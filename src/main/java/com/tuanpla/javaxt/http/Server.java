package com.tuanpla.javaxt.http;
import com.tuanpla.javaxt.http.servlet.HttpServletRequest;
import com.tuanpla.javaxt.http.servlet.HttpServletResponse;
import com.tuanpla.javaxt.http.servlet.ServletException;
import com.tuanpla.javaxt.http.servlet.HttpServlet;
import java.util.List;
import java.util.LinkedList;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ConcurrentHashMap;

//******************************************************************************
//**  JavaXT Http Server
//******************************************************************************
/**
 *   A lightweight, multi-threaded web server used to process HTTP requests
 *   and send responses back to the client. 
 *
 *   Adapted from Java Network Programming, 2nd Edition:
 *   http://www.oreilly.com/catalog/javanp2/chapter/ch11.html#71137
 *
 *   Updated to use non-blocking sockets and IO streams, SSL support,
 *   persistent connections (Keep-Alive), GZIP compression, chunked transfer
 *   encoding, multiple socket listeners, http session management, etc.
 *
 *   Note that the server requires an implementation of the HttpServlet class.
 *   As new requests come in, they are passed to the HttpServlet.processRequest()
 *   method which is used to generate a response.
 *
 ******************************************************************************/

public class Server extends Thread {
    
    private static int numThreads;
    private java.util.ArrayList<InetSocketAddress> addresses =
            new java.util.ArrayList<InetSocketAddress>();

    private HttpServlet servlet;

    private static ConcurrentHashMap<Integer, SocketConnection> connections =
            new ConcurrentHashMap<Integer, SocketConnection>();

    /** Maximum time that socket connections can remain idle. */
    private static int maxIdleTime = 2*60000; //2 minutes

    private static boolean debug = false;
   
  //**************************************************************************
  //** Constructor
  //**************************************************************************
  /** Used to instantiate the Server on a given port.
   */
    public Server(int port, int numThreads, HttpServlet servlet) {
        this(new InetSocketAddress(port), numThreads, servlet);
    }


  //**************************************************************************
  //** Constructor
  //**************************************************************************
  /** Used to instantiate the Server on a given port and IP address.
   */
    public Server(InetSocketAddress address, int numThreads, HttpServlet servlet){
        this(new InetSocketAddress[]{address}, numThreads, servlet);
    }


  //**************************************************************************
  //** Constructor
  //**************************************************************************
  /** Used to instantiate the Server on multiple ports and/or IP addresses.
   */
    public Server(InetSocketAddress[] addresses, int numThreads, HttpServlet servlet){
        this.addresses.clear();
        for (InetSocketAddress address : addresses){
            this.addresses.add(address);
        }
        this.numThreads = numThreads;
        this.servlet = servlet;
    }

  //**************************************************************************
  //** Constructor
  //**************************************************************************
  /** Used to instantiate the Server on multiple ports and/or IP addresses.
   */
    public Server(java.util.List<InetSocketAddress> addresses, int numThreads, HttpServlet servlet){
        this.addresses.clear();
        for (InetSocketAddress address : addresses){
            this.addresses.add(address);
        }
        this.numThreads = numThreads;
        this.servlet = servlet;
    }

    
  //**************************************************************************
  //** Main
  //**************************************************************************
  /** Entry point for the application. Accepts command line arguments to 
   *  specify which port to use and the maximum number of concurrent threads.
   *
   *  @param args the command line arguments
   */
    public static void main(String[] args) {


      //Set the port to listen on
        InetSocketAddress[] addresses;
        if (args.length>0){
            try {
                int port = Integer.parseInt(args[0]);
                if (port < 0 || port > 65535) throw new Exception();
                addresses = new InetSocketAddress[]{
                    new InetSocketAddress(port)
                };
            }
            catch (Exception e) {
                System.out.println("Invalid Port: " + args[0]);
                return;
            }
        }
        else{
            addresses = new InetSocketAddress[]{
                new InetSocketAddress(80),
                new InetSocketAddress(443)
            };
        }


      //Instantiate the HttpServer with a sample HttpServlet
        try {
            Server webserver = new Server(addresses, 250, new ServletTest());
            webserver.start();
        }
        catch (Exception e) {
            System.out.println("Server could not start because of an " 
             + e.getClass());
            System.out.println(e);
        }
    }
    
    
  //**************************************************************************
  //** Run
  //**************************************************************************
  /** Used to start the web server. Creates a thread pool and instantiates a
   *  socket listener for each specified port/address.
   */
    public void run() {

      //Create Thread Pool
        for (int i=0; i<numThreads; i++) {
            new Thread(new RequestProcessor(servlet, i)).start();
        }


      //Set up timer task to shutdown idle connections
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new SocketMonitor(), maxIdleTime, maxIdleTime);

        
      //Create a new SocketListener for each port/address
        for (InetSocketAddress address : addresses){
            new Thread(new SocketListener(address)).start();
        }

        
      //Call init
        if (servlet!=null) try{ servlet.init(new Object());} catch(Exception e){}

    }


  //**************************************************************************
  //** SocketListener
  //**************************************************************************
  /** Thread used to open a socket and accept client connections. Inbound
   *  requests (client connections) are added to a queue and processed by the
   *  next available RequestProcessor thread. Idle connections are
   *  automatically closed after 5 minutes.
   */
    private static class SocketListener implements Runnable {

        private InetSocketAddress address;
        public SocketListener(InetSocketAddress address){
            this.address = address;
        }

        @Override
        public void run() {

            String hostName = address.getHostName();
            if (hostName.equals("0.0.0.0") || hostName.equals("127.0.0.1")) hostName = "localhost";
            hostName += ":" + address.getPort();
            System.out.print("Accepting connections on " + hostName + "\r\n");


            java.nio.channels.Selector selector = null;
            ServerSocketChannel server = null;
            try {

              //Create the selector
                selector = java.nio.channels.Selector.open();

              //Create a non-Blocking Server Socket Channel
                server = ServerSocketChannel.open();
                server.configureBlocking(false);
                server.socket().bind(address);
                server.register(selector, SelectionKey.OP_ACCEPT); //server.validOps()

            }
            catch (java.io.IOException e) {
                System.out.println("Failed to create listener for " + hostName);
                System.out.println("...because of an " + e.getClass());
                System.out.println(e);
                //e.printStackTrace();
                return;
            }


          //Pass Inbound Request to the RequestProcessor
            SelectionKey key = null;
            while (true) {

                try {
                    if(selector.select() == 0) continue; //selector.select();

                    java.util.Set readyKeys = selector.selectedKeys();
                    java.util.Iterator it = readyKeys.iterator();


                  //Process one key at a time
                    while (it.hasNext()) {

                      //Get the selection key
                        key = (SelectionKey)it.next();

                      //Remove it from the list to indicate that it is being processed
                        it.remove();

                      //Check whether the key is valid
                        if (!key.isValid()) {
                            continue;
                        }

                      //Process the key
                        if (key.isAcceptable()) {

                          //Accept the connection
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);


                          //Add the new connection to the list of active connections
                            synchronized(connections){
                                int connID = socketChannel.hashCode();
                                if (!connections.contains(connID)){
                                    connections.put(connID, new SocketConnection(socketChannel));
                                    connections.notifyAll();
                                }
                            }


                          //Register for read events
                            socketChannel.register(selector, SelectionKey.OP_READ);

                        }
                        else if (key.isReadable()) {

                          //Process Read Key
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            if (socketChannel.isOpen()){
                                socketChannel.configureBlocking(false);



                              //Check the last time anything was written to the
                              //socket. If it was more than 60 seconds ago, the
                              //browser is in a keep-alive state and is asking the
                              //the server if there's anything else to read. If
                              //nothing has been sent to the client in over 60
                              //seconds, we will drop the connection by throwing
                              //an exception.
                                SocketConnection connection = null;
                                synchronized(connections){
                                    connection = connections.get(socketChannel.hashCode());
                                    if (connection!=null){
                                        Long lastWrite = connection.lastEvent;
                                        if (lastWrite!=null){
                                            if (new java.util.Date().getTime()-lastWrite>120000){ //<--120 seconds...
                                                throw new Exception("Connection idle for over 2 minutes.");
                                            }
                                        }
                                    }
                                }


                              //Process the request. Most request end up here!
                                if (connection!=null)
                                    RequestProcessor.processRequest(connection);
                            }
                        }
                    }
                }
                catch (Exception e) {

                    //System.out.println();
                    //e.printStackTrace();


                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    synchronized(connections){
                        connections.remove(socketChannel.hashCode());
                        connections.notifyAll();
                    }

                    try{
                        socketChannel.close();
                    }
                    catch(Exception ex){}

                    key.cancel();

                }
            }

        }
    }


  //**************************************************************************
  //** RequestProcessor
  //**************************************************************************
  /**  Thread used to process HTTP Requests and send responses back to the
   *   client. As new http requests come in (via the processRequest method),
   *   they are added to a queue (pool). Requests in the queue are processed 
   *   by instances of this class via the run method.
   */
    private static class RequestProcessor implements Runnable {

        private static List<SocketConnection> pool = new LinkedList<SocketConnection>();
        private static Integer[] active;
        private HttpServlet servlet;
        private int threadID;

        public RequestProcessor(HttpServlet servlet, int threadID){
            this.servlet = servlet;
            this.threadID = threadID;
            if (active==null) active = new Integer[numThreads];
        }

        public static void processRequest(SocketConnection request) {

            int connID = request.hashCode();
            synchronized (pool) {
                if (!pool.contains(request)){
                    synchronized (active) {

                        boolean isActive = false;
                        for (Integer id : active){
                            if (id!=null && connID==id){
                                isActive = true;
                                break;
                            }
                        }

                        if (!isActive){
                            pool.add(request);
                            pool.notifyAll();
                        }
                    }
                }
            }
        }


        public void run() {
            while (true) {

              //Wait for new requests to be added to the pool
                SocketConnection connection = null;
                int connID = -1;
                synchronized (pool) {
                    while (pool.isEmpty()) {
                      try {
                        pool.wait();
                      }
                      catch (InterruptedException e) {
                          return;
                      }
                    }

                  //Remove the connection from the pool and mark it as "active"
                    connection = pool.remove(0);
                    connID = connection.hashCode();
                    synchronized (active) {
                        active[threadID] = connID;
                        active.notifyAll();
                    }
                    pool.notifyAll();
                }


              //Process request and send a response back to the client
                HttpServletRequest request = null;
                HttpServletResponse response = null;
                try {
                    if (servlet!=null){
                        request = new HttpServletRequest(connection, servlet);
                        response = new HttpServletResponse(request, connection);
                        servlet.processRequest(request, response);
                    }
                }
                catch (ServletException e){
                    if (debug) System.out.println(e.getMessage());
                    if (request!=null){
                        response = new HttpServletResponse(request, connection);
                        response.setStatus(e.getStatusCode(), e.getMessage());
                    }
                    else{
                      //TODO: Need to propgate error to the client!
                        
                    }
                }
                catch (java.lang.OutOfMemoryError e){
                    System.out.println("OutOfMemoryError!!!");
                    return;
                }
                catch (Exception e) {
                    if (debug){
                        if (e.getMessage()==null) e.printStackTrace();
                        else System.out.println(e.getMessage());
                        //e.printStackTrace();
                    }
                }


              //Flush the response
                if (response!=null) response.flushBuffer();


              //Close the socket connection (as needed)
                boolean isKeepAlive = (request!=null ? request.isKeepAlive() : false);                
                if (!isKeepAlive){
                    try {
                        connection.close();
                    }
                    catch (java.io.IOException e) {}
                }


              //Destroy the request and response objects
                if (request!=null){
                    request.clear();
                    request = null;
                }
                if (response!=null){
                    response.reset();
                    response = null;
                }


              //Update the list of active connections
                synchronized (active) {
                    active[threadID] = null;
                    active.notifyAll();
                }

            }
        }
    }


  //**************************************************************************
  //** SocketMonitor
  //**************************************************************************
  /**  TimerTask used to find and close idle connections.
   */
    private static class SocketMonitor extends java.util.TimerTask {
        public void run(){

            long currTime = new java.util.Date().getTime();
            java.util.ArrayList<SocketConnection> idleConnections =
                    new java.util.ArrayList<SocketConnection>();

          //Find idle connections
            synchronized(connections){
                java.util.Iterator<Integer> it = connections.keySet().iterator();
                while (it.hasNext()){
                    int connID = it.next();
                    SocketConnection connection = connections.get(connID);
                    if (connection!=null){
                        if (currTime-connection.lastEvent>maxIdleTime){
                            idleConnections.add(connection);
                        }
                    }
                }
            }

          //Close idle connections
            for (SocketConnection connection : idleConnections){
                try{ connection.close(); }
                catch(Exception e){}
            }
            
          //Clean up
            idleConnections.clear();
            idleConnections = null;
        }
    }



  //**************************************************************************
  //** SocketConnection
  //**************************************************************************
  /** Simple wrapper for a SocketChannel. Logs reads/writes for the
   *  SocketMonitor and is used to associate an SSLEngine with the given
   *  SocketChannel.
   */
    public static class SocketConnection {
        private int id;
        private long startTime;
        private Long lastEvent;
        private SocketChannel socketChannel;
        private javax.net.ssl.SSLEngine sslEngine;
        private String localhost;
        private String localaddress;
        private int localport;
        private java.net.InetSocketAddress remoteSocketAddress;
        
        protected SocketConnection(SocketChannel socketChannel){
            this.id = socketChannel.hashCode();
            this.socketChannel = socketChannel;
            startTime = new java.util.Date().getTime();
            lastEvent = startTime;
            
            java.net.Socket socket = socketChannel.socket();
            localhost = socket.getLocalAddress().getCanonicalHostName();
            localport = socket.getLocalPort();
            localaddress = socket.getLocalAddress().getHostAddress();
            
            remoteSocketAddress = (java.net.InetSocketAddress) socket.getRemoteSocketAddress();
        }


      /** Returns the client IP address. */
        public java.net.InetSocketAddress getRemoteSocketAddress(){
            return remoteSocketAddress;
        }


      /** Returns the host name of the Internet Protocol (IP) interface on
       *  which the request was received.
       */
        public String getLocalHost(){
            return localhost;
        }

      /** Returns the Internet Protocol (IP) address of the interface on which
       *  the request was received.
       */
        public String getLocalAddress(){
            return localaddress;
        }


      /** Returns the Internet Protocol (IP) port number of the interface on
       *  which the request was received.
       */
        public int getLocalPort(){
            return localport;
        }


      /** Used to read data from the SocketChannel. Note that a SocketChannel
       *  in non-blocking mode cannot read any more bytes than are immediately
       *  available from the socket's input buffer. In this case, multiple
       *  attempts are made to read from the SocketChannel. There is a 1 ms
       *  delay between read attempts and a timeout of 15 seconds. The method
       *  will return immediately after receiving bytes from the socket.
       *  Unfortunately, this is no way for this method to know whether the
       *  client is done sending data so no checksum is performed to ensure
       *  that all the bytes came across cleanly.
       */
        public int read(java.nio.ByteBuffer buffer) throws java.io.IOException {
            Integer numBytesRead = 0;
            int numRetries = 0;

            if (socketChannel==null) throw new java.io.IOException("SocketChannel is null!");
            numBytesRead = socketChannel.read(buffer);
            if (numBytesRead==0){

                synchronized (numBytesRead) {
                    while ((numBytesRead = socketChannel.read(buffer))<1) {

                        if (numBytesRead==-1) throw new java.io.IOException("Received -1 bytes. Socket is closed.");

                        numRetries++;
                        if (numRetries>15000){
                            throw new java.io.IOException("Timeout waiting for bytes from the client.");
                        }

                        try {
                            numBytesRead.wait(1);
                        }
                        catch (InterruptedException e) {
                            throw new java.io.IOException("Interrupt!");
                        }
                    }
                }
            }

            if (numBytesRead==-1) throw new java.io.IOException("Received -1 bytes. Socket is closed.");


            //if (numRetries>9) System.out.println("Wakeup! Read " + numBytesRead + " bytes after " + numRetries + " tries.");


            lastEvent = new java.util.Date().getTime();
            return numBytesRead;
        }


      /** Used to write data to the SocketChannel. Note that a SocketChannel
       *  in non-blocking mode cannot write any more bytes than are free in the
       *  socket's output buffer. In this case, multiple attempts are made to
       *  write to the SocketChannel. There is a 50 ms delay between write
       *  attempts and a timeout of 15 seconds. Returns only after all of the
       *  bytes in the buffer have been sent.
       *
       *  @param length Number of bytes in the buffer. This is used to ensure
       *  that all the bytes were sent to the client. Note that buffer length
       *  does not always equal buffer capacity.
       */
        public int write(java.nio.ByteBuffer buffer, int length) throws java.io.IOException {

            Integer numBytesWrite = 0;
            int numRetries = 0;

            numBytesWrite = socketChannel.write(buffer);
            if (numBytesWrite!=length){

                synchronized (numBytesWrite) {
                    while (numBytesWrite!=length) {

                        int x = socketChannel.write(buffer);
                        if (x>=0) numBytesWrite += x;
                        if (numBytesWrite == length) break;

                        numRetries++;
                        if (numRetries>300 || x==-1){
                            throw new java.io.IOException();
                        }
                        
                        try {
                            numBytesWrite.wait(50);
                        }
                        catch (InterruptedException e) {
                            throw new java.io.IOException("Interrupt!");
                        }
                    }
                }
                
            }

            if (numBytesWrite==-1) throw new java.io.IOException();
            
            //if (numRetries>1) System.out.println("Wrote " + numBytesWrite + " bytes after " + numRetries + " tries.");


            lastEvent = new java.util.Date().getTime();
            return numBytesWrite;
        }

      /** Used to close the socketChannel and update the list of connections.*/
        public void close() throws java.io.IOException{
            synchronized(connections){
                connections.remove(id);
                connections.notifyAll();
            }
            try{
                socketChannel.close();
            }
            catch(Exception e){}
            socketChannel = null;
            sslEngine = null;
        }

        public javax.net.ssl.SSLEngine getSSLEngine(){
            return sslEngine;
        }

        public void setSSLEngine(javax.net.ssl.SSLEngine sslEngine){
            this.sslEngine = sslEngine;
        }

        public SocketChannel getChannel(){
            return socketChannel;
        }

        public int hashCode(){
            return id;
        }
    }


  //**************************************************************************
  //** ServletTest
  //**************************************************************************
  /** Simple implementation of an JavaXT HttpServlet. Simply returns the
   *  request headers and body back to the client in plain text.
   */
    private static class ServletTest extends com.tuanpla.javaxt.http.servlet.HttpServlet {

        private boolean debug = false;

        public ServletTest() throws Exception {
            //setKeyStore(new java.io.File("/temp/keystore.jks"), "password");
            //setTrustStore(new java.io.File("/temp/keystore.jks"), "password");
        }

        public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws java.io.IOException {

            if (debug){
                System.out.println();
                System.out.println("New Request From: " + request.getRemoteAddr());
                System.out.println("TimeStamp: " + new java.util.Date());

              //Print the requested URL
                System.out.println(request.getMethod() + ": " + request.getURL().toString());
                System.out.println();
            }


          //Send response to the client
            try{

                byte[] header = request.toString().getBytes("UTF-8");
                byte[] body = request.getBody();
                byte[] msg = new byte[header.length + body.length];

                System.arraycopy(header,0,msg,0,header.length);
                System.arraycopy(body,0,msg,header.length,body.length);

                header = null;
                body = null;

                response.setContentType("text/plain");
                response.write(msg);

                msg = null;

            }
            catch(Exception e){
            }

            if (debug){
                System.out.println(request.toString());
                System.out.println(response.toString());
            }

        }
    }


}// End server class