/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.entity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import res.labs.discoverhttp.data.Clock;



/**
 *
 * @author fidimala
 */
public class ServerHTTP {
    int listenPort = 8080; 
   /*
   * The server socket, used to accept client connection requests
   */
    ServerSocket server;
    //Instance the new clock for the server!
    Clock clock;
    
    
    public ServerHTTP() throws IOException{
        server = new ServerSocket(listenPort);
        clock = new Clock();
    }
    
    public ServerHTTP(int port) throws IOException{
        server = new ServerSocket(port);
        listenPort = port;  
        clock = new Clock();
    }
    
    public void run() throws IOException{
        System.out.println("Listening for connection on port " + listenPort + " ...." );
        
        while(true){
            Socket socket = server.accept();
            System.out.println("--------------------------------------" );   
            System.out.println("New client is detected!" );                     
            //handle each requested client

            new Thread(new ClientWorker(socket,clock)).start();
        }
    }
    
    
}
