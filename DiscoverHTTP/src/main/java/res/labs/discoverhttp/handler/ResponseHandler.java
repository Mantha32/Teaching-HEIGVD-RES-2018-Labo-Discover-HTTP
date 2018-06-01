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
import res.labs.discoverhttp.data.Clock;
import res.labs.discoverhttp.data.RawHTML;

/**
 *
 * @author fidimala
 */
public class ResponseHandler {
    public final BufferedOutputStream writer;
    
    public ResponseHandler(OutputStream out){
     writer = new BufferedOutputStream(out);
    }
    
    public void send(String supportedFormat, String method, int statusCode, Clock clock) throws UnsupportedEncodingException, IOException{
        System.out.println("STATUS: " + statusCode);
        
        RawHTML rawHTML = new RawHTML(statusCode, supportedFormat, clock);
        
        writer.write(rawHTML.toString().getBytes("UTF-8"));
        writer.flush();       
    }
}
