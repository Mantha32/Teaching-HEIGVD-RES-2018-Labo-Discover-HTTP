/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    LineByLineInputStream reader = null;
    
    
    public ClientWorker(Socket socket) throws IOException{
        socketClient = socket;
        reader = new LineByLineInputStream(socket.getInputStream());    
    }
    
    public void parseRequest() throws IOException{
        System.out.println("parse the client request");
        
        StringBuilder sb = new StringBuilder();
        String line;
        do{
            line = reader.readline();
            System.out.println(line);
        }while(!line.isEmpty());
        
        System.out.println("End header request!");

   }
    
public void sendResponse() throws IOException{
    parseRequest();
    Clock clock = new Clock();
    String httpResponse = "HTTP/1.1 200 OK\r\n" + clock.getTime() + "\r\n";                
    socketClient.getOutputStream().write(httpResponse.getBytes("UTF-8"));
    System.out.println("data sending for client");
    System.out.println("End connexion");
    socketClient.close();
    reader.close();
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
