package com.tuanpla.javaxt.http.servlet;
import com.tuanpla.javaxt.http.servlet.HttpServletResponse.ConnectionOutputStream;
import java.io.IOException;

//******************************************************************************
//**  ServletOutputStream
//******************************************************************************
/**
 *   Provides an output stream for writing to the body of an http response. 
 *   Writes raw bytes to a socket connection. Automatically encrypts the data 
 *   if the socket is SSL/TLS encrypted.
 *
 ******************************************************************************/

public class ServletOutputStream extends java.io.OutputStream {
    
    private ConnectionOutputStream out;
    
    public ServletOutputStream(ConnectionOutputStream out){
        this.out = out;
    }
    
    public void write(int b) throws IOException {
        out.write(b);
    }
    
    public void flush() throws IOException{
        out.flush();
    }
    
    public void close() throws IOException{
        out.close();
    }    
}