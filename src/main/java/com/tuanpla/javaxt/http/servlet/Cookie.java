package com.tuanpla.javaxt.http.servlet;

//******************************************************************************
//**  Cookie Class
//******************************************************************************
/**
 *   Creates a cookie, a small amount of information sent by a servlet to a
 *   Web browser, saved by the browser, and later sent back to the server. A
 *   cookie's value can uniquely identify a client, so cookies are commonly
 *   used for session management.
 *
 ******************************************************************************/

public class Cookie {
    
    private String name;
    private String value;
    private String path;

    public Cookie(String name){
        this(name, null);
    }
    
    public Cookie(String name, String value){
        this.name = name;
        this.value = value;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    public String toString(){
        StringBuffer str;
        if (value==null){
            str = new StringBuffer(name.length() + 1);
            str.append(name);
            str.append(";");
        }
        else{
            str = new StringBuffer(name.length() + value.length() + 2);
            str.append(name);
            str.append("=");
            str.append(value);
            str.append(";");

            if (path!=null){
                str.append(" Path=");
                str.append(path);
            }
        }
        //JSESSIONID=1f3de3ba66697674ff4a07bc561e; Path=/JavaXT
        return str.toString();
    }

    public int hashCode(){
        return toString().hashCode();
    }

    public boolean equals(Object obj){
        if (obj!=null){
            return obj.toString().equals(this.toString());
        }
        return false;
    }
}