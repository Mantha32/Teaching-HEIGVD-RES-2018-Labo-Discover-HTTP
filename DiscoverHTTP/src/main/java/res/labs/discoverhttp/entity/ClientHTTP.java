/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.entity;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import res.labs.discoverhttp.data.Hours;
import res.labs.discoverhttp.handler.SupportedFormat;
import res.labs.discoverhttp.wrapper.LineByLineInputStream;

/**
 *
 * @author fidimala
 */

public class ClientHTTP {
    private final String hostName;
    private final int port;
    private final String method;
    private final String ressource;
    private final String contentType;
    private final Socket socket;
    private final String CRLF = "\r\n";
    
    public ClientHTTP(String hostname, int port , String method, String ressource, String contentType)throws IOException{
        this.hostName = hostname;
        this.port = port;
        this.method = method;
        this.ressource = ressource;
        this.contentType = contentType;
        socket = new Socket(hostname, port);
    }
    /**
     * send method exclude GET from client to server
     * @param method
     * @param hours
     * @throws IOException 
     */
    
    public void sendRequest(String method, Hours hours)throws IOException{
        OutputStream output = socket.getOutputStream();
        BufferedOutputStream writer = new BufferedOutputStream(output);
        
        StringBuilder request = new StringBuilder(setHeader(method,ressource, contentType));
        
        if("POST".equals(method)){
            request.append(setBody(contentType, hours));
            request.append(CRLF);
        }
        
        writer.write(request.toString().getBytes("UTF-8"));
        writer.flush();
        
    }
/**
 * Send GET request from client to server
 * @throws IOException 
 */    
    public void sendRequest()throws IOException{
        OutputStream output = socket.getOutputStream();
        BufferedOutputStream writer = new BufferedOutputStream(output);
        
        String request = setHeader(method, ressource, contentType);
                
        writer.write(request.getBytes("UTF-8"));
        writer.flush();
        
    }    
    
  
   public void readResponse()throws IOException{
       InputStream input = socket.getInputStream();
       LineByLineInputStream reader = new LineByLineInputStream(input);

       String line;
       
        while ((line = reader.readline()) != null) {
                System.out.println(line);
        }
   }
   
   private String setHeader(String requestedMethod, String requestedRessource, String supportedContentType){
       StringBuilder header = new StringBuilder();
       
       header.append(requestedMethod).append(" ").append(requestedRessource).append(" HTTP/1.1").append(CRLF);
       header.append("Host: ").append(hostName).append(CRLF);
       header.append("User-Agent: Simple Http Client").append(CRLF);
       header.append("Accept: " ).append(supportedContentType).append(CRLF);
       header.append("Accept-Language: en-US").append(CRLF);
       header.append("Connection: close").append(CRLF);
       header.append(CRLF);   
       
       return header.toString();
   }
   
   
   private String setBody(String format, Hours hours){     
       
       if(SupportedFormat.XML.equals(format)){
           return hours.toXML();
       }
       
       if(SupportedFormat.JSON.equals(format)){
           return hours.toJson();
       }
       
       return hours.toString();
   }
       
}
