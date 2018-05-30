/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.handler;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import res.labs.discoverhttp.data.Clock;

/**
 *
 * @author fidimala
 */
public class ResponseHandler {
    public final BufferedOutputStream writer;
    
    public ResponseHandler(OutputStream out){
     writer = new BufferedOutputStream(out);
    }
    
    public void send(String supportedFormat, String method, int statusCode) throws UnsupportedEncodingException, IOException{
        String CRLF = " \r\n";
        int index = 0;
        if(supportedFormat.equals(SupportedFormat.JSON))
            index = 1;
        else if (supportedFormat.equals(SupportedFormat.XML))
            index = 2;
        
        StringBuilder responseHeader = new StringBuilder(method);
        responseHeader.append(" ").append(statusCode).append(" ").append(StatusCode.getStatusMessage(statusCode)).append(CRLF);
        responseHeader.append("Date: ").append(new Date().toString()).append(CRLF);
        responseHeader.append("Server: Dilifera clock service 1.0 \r\n");
        StringBuilder responseBody = new StringBuilder();
        Clock clock = new Clock();
        switch(index){
            case 0: //HTML response
                responseBody.append("<html>");
                responseBody.append("<body>");
                responseBody.append("<h1> TIME : ");
                
                responseBody.append(clock.getTime());
                responseBody.append("</h1>");
                responseBody.append("/<body>");
                responseBody.append("/<html>");             
                break;
            case 1:
                responseBody.append(clock.toJson());
                break;
            case 2:
                //responseBody.append('<?xml version="1.0" ?>');
            break;
        }
        
        String tmp = responseBody.toString();
        responseHeader.append("Content-Length: ").append(tmp.length()).append(CRLF);
        responseHeader.append("Content-Type: ").append(supportedFormat).append(CRLF);
        responseHeader.append("Connection: Closed");
        responseHeader.append(CRLF).append(CRLF);
        
        responseHeader.append(responseBody);
        
        writer.write(responseHeader.toString().getBytes("UTF-8"));
        writer.flush();
        
    }
}
