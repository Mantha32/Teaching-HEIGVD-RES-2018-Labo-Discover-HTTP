/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import res.labs.discoverhttp.data.Hours;
import res.labs.discoverhttp.data.JsonObjectMapper;
import res.labs.discoverhttp.data.XmlObjectMapper;
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
     * @throws com.fasterxml.jackson.core.JsonProcessingException 
     */
    
    public void sendRequest(String method, Hours hours)throws IOException, JsonProcessingException{
        OutputStream output = socket.getOutputStream();
        BufferedOutputStream writer = new BufferedOutputStream(output);
        
        String body = setBody(contentType, hours);
        StringBuilder request = new StringBuilder(setHeader(method,ressource, contentType, body.length()));
        
        if("POST".equals(method)){
            request.append(body);
            request.append(CRLF);
        }
        
        writer.write(request.toString().getBytes("UTF-8"));
        writer.flush();
        
    }
    
    public void sendRequest(String method, String body)throws IOException{
       OutputStream output = socket.getOutputStream();
       BufferedOutputStream writer = new BufferedOutputStream(output);
       
        StringBuilder request = new StringBuilder(setHeader(method,ressource, contentType, body.length())); 
        
        request.append(body);
        request.append(CRLF);
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
        
        String request = setHeader(method, ressource, contentType, 0);
                
        writer.write(request.getBytes("UTF-8"));
        writer.flush();
        
    }    
    
  
   public void readResponse()throws IOException{
       InputStream input = socket.getInputStream();
       LineByLineInputStream reader = new LineByLineInputStream(input);

       String line;
       
       // read header
        do {    
                line = reader.readline();
                System.out.println(line);
        }while (!line.isEmpty());
        
        //consume the séparator define in the http ptrotocol
        //line = reader.readline();
        do {    
                line = reader.readline();
                System.out.println(line);
        }while (!line.isEmpty());
        
        //read the body
        
        input.close();
        reader.close();
   }
   
   private String setHeader(String requestedMethod, String requestedRessource, String supportedContentType, int contentLength){
       StringBuilder header = new StringBuilder();
       
       header.append(requestedMethod).append(" ").append(requestedRessource).append(" HTTP/1.1").append(CRLF);
       header.append("Host: ").append(hostName).append(CRLF);
       header.append("User-Agent: Simple Http Client").append(CRLF);
       header.append("Accept: " ).append(supportedContentType).append(CRLF);
       header.append("Accept-Language: en-US").append(CRLF);
       
       if("POST".equals(requestedMethod)){
           header.append("Content-Length: ").append(contentLength).append(CRLF);
       }
       
       header.append("Connection: close").append(CRLF);
       header.append(CRLF);   
       
       return header.toString();
   }
   
   
   private String setBody(String format, Hours hours) throws JsonProcessingException{     
       
       if(SupportedFormat.XML.equals(format)){
           return XmlObjectMapper.toXml(hours);
       }
       
       if(SupportedFormat.JSON.equals(format)){
           return JsonObjectMapper.toJson(hours);
       }
       
       return hours.toString();
   }
   
   public void close() throws IOException{
       socket.close();
   }
       
}
