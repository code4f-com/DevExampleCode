package com.tuanpla.javaxt.http.servlet;
import java.security.KeyStore;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLContext;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

//******************************************************************************
//**  HttpServlet Class
//******************************************************************************
/**
 *   The HttpServer requires an implementation of an HttpServlet in order to
 *   process HTTP requests.
 *
 ******************************************************************************/

public abstract class HttpServlet {

    private Authenticator authenticator;
    private KeyStore keystore;
    private KeyStore truststore;
    private KeyManagerFactory kmf;
    private TrustManagerFactory tmf;
    private String sslProvider;
    protected static final ServletContext context = new ServletContext();
    
  //This variable are used in the HttpServletRequest class.
    protected String servletPath = "";


  //**************************************************************************
  //** init
  //**************************************************************************
  /** Called by the servlet container to indicate to a servlet that the servlet
   *  is being placed into service.
   */
    public void init(Object ServletConfig) throws ServletException {
    }


  //**************************************************************************
  //** processRequest
  //**************************************************************************
  /** This method is called each time the server receives an http request (GET,
   *  POST, HEAD, etc.). Use this method to formulate a response to the client.
   */
    public abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, java.io.IOException;


  //**************************************************************************
  //** getServletContext
  //**************************************************************************
  /** Returns the ServletContext.
   */
    public ServletContext getServletContext(){
        return context;
    }



    public void log(String str){
        //TODO: Implement logger
    }
    
  //**************************************************************************
  //** setPaths
  //**************************************************************************
  /** Used to set the context and servlet paths used in the 
   *  HttpServletRequest.getContextPath() and the 
   *  HttpServletRequest.getServletPath() methods. 
   */    
    public void setPaths(String contextPath, String servletPath){
      //TODO: Update logic used to assign context path
        this.getServletContext().setContextPath(contextPath);
        this.servletPath = servletPath;
    }


  //**************************************************************************
  //** setAuthenticator
  //**************************************************************************
  /** Used to define an Authenticator used to authenticate requests.
   */
    public void setAuthenticator(Authenticator authenticator){
        this.authenticator = authenticator;
    }


  //**************************************************************************
  //** getAuthenticator
  //**************************************************************************
  /** Returns a new instance of an Authenticator used to authenticate users.
   */
    protected Authenticator getAuthenticator(HttpServletRequest request){
        if (authenticator!=null) return authenticator.newInstance(request);
        else return null;
    }


  //**************************************************************************
  //** setKeyStore
  //**************************************************************************
  /** Used to set the KeyStore and initialize the KeyManagerFactory. The
   *  KeyStore is used to store keys and certificates for SSL.
   */
    public void setKeyStore(KeyStore keystore, String passphrase) throws Exception {

      //Update class variable
        this.keystore = keystore;

      //Initialize the KeyManagerFactory
        kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keystore, passphrase.toCharArray());
    }


  //**************************************************************************
  //** setKeyStore
  //**************************************************************************
  /** Used to set the KeyStore and initialize the KeyManagerFactory. The
   *  KeyStore is used to store keys and certificates for SSL.
   */
    public void setKeyStore(java.io.File keyStoreFile, String passphrase) throws Exception {
        char[] pw = passphrase.toCharArray();
        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(new java.io.FileInputStream(keyStoreFile), pw);
        setKeyStore(keystore, passphrase);
    }


  //**************************************************************************
  //** getKeyStore
  //**************************************************************************
  /** Returns the the KeyStore associated with this Servlet.
   */
    public KeyStore getKeyStore(){
        return keystore;
    }


  //**************************************************************************
  //** setTrustStore
  //**************************************************************************
  /** Used to set the TrustStore and initialize the TrustManagerFactory. The
   *  TrustStore is used to store public keys and certificates for SSL.
   */
    public void setTrustStore(KeyStore truststore) throws Exception {

      //Update class variable
        this.truststore = truststore;

      //Initialize the TrustManagerFactory
        tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(truststore);
    }


  //**************************************************************************
  //** setTrustStore
  //**************************************************************************
  /** Used to set the TrustStore and initialize the TrustManagerFactory. The
   *  TrustStore is used to store public keys and certificates for SSL.
   */
    public void setTrustStore(java.io.File trustStoreFile, String passphrase) throws Exception {
        char[] pw = passphrase.toCharArray();
        KeyStore truststore = KeyStore.getInstance("JKS");
        truststore.load(new java.io.FileInputStream(trustStoreFile), pw);
        setTrustStore(truststore);
    }


  //**************************************************************************
  //** getTrustStore
  //**************************************************************************
  /** Returns the the TrustStore associated with this Servlet.
   */
    public KeyStore getTrustStore(){
        return truststore;
    }


  //**************************************************************************
  //** setSSLProvider
  //**************************************************************************
  /** Used to specify an Security Provider used to decrypt SSL/TLS messages.
   */
    public void setSSLProvider(java.security.Provider provider){
        if (provider!=null){
            sslProvider = provider.getName();
            //java.security.Security.addProvider(provider);
        }
        else sslProvider = null;
    }


  //**************************************************************************
  //** setSSLProvider
  //**************************************************************************
  /** Used to specify an Security Provider used to decrypt SSL/TLS messages.
   */
    public void setSSLProvider(String provider){
        setSSLProvider(java.security.Security.getProvider(provider));
    }


  //**************************************************************************
  //** getSSLEngine
  //**************************************************************************
  /** Used to instantiate an SSLEngine used to decrypt SSL/TLS messages.
   */
    protected SSLEngine getSSLEngine() throws ServletException {

        /*//Debug use only!
        java.security.Provider provider = new SSLProvider();
        java.security.Security.addProvider(provider);
        setSSLProvider(provider);
        */


        javax.net.ssl.KeyManager[] km = null;
        javax.net.ssl.TrustManager[] tm = null;

        if (kmf!=null) km = kmf.getKeyManagers();
        if (tmf!=null) tm = tmf.getTrustManagers();


        SSLContext sslc;
        try{
            if (sslProvider==null) sslc = SSLContext.getInstance("TLS");
            else sslc = SSLContext.getInstance("TLS", sslProvider);
            sslc.init(km, tm, null);
        }
        catch(Exception e){
            ServletException se = new ServletException("Failed to instantiate SSLEngine.");
            se.initCause(e);
            throw se;
        }


        SSLEngine sslEngine = sslc.createSSLEngine();
        sslEngine.setUseClientMode(false);
        sslEngine.setEnableSessionCreation(true);
        sslEngine.setNeedClientAuth(false);
        return sslEngine;
    }
}