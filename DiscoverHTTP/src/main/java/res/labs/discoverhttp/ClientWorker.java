/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import res.labs.discoverhttp.data.Clock;
import res.labs.discoverhttp.wrapper.LineByLineInputStream;

/**
 *
 * @author fidimala
 */
public class ClientWorker implements Runnable{
    private final Socket socketClient;
    BufferedInputStream reader = null;
    
    
    public ClientWorker(Socket socket) throws IOException{
        socketClient = socket;
        reader = new BufferedInputStream(new LineByLineInputStream(socket.getInputStream()));    
    }
    
    public void parseRequest(){
        
    }
    
public void sendResponse() throws IOException{
    Clock clock = new Clock();
    String httpResponse = "HTTP/1.1 200 OK\r\n" + clock.getTime() + "\r\n";                
        socketClient.getOutputStream().write(httpResponse.getBytes("UTF-8"));
        socketClient.close();
    } 

    @Override
    public void run(){
        try {
            sendResponse();
            socketClient.close();
            reader.close();
        }catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    
}
