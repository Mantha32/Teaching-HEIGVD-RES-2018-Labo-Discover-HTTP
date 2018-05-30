package res.labs.discoverhttp;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import res.labs.discoverhttp.data.Clock;
import res.labs.discoverhttp.handler.RequestHandler;

/**
 *
 * @author Rafidimalala
 */
public class Server {
  public static void main(String[] args) throws IOException{ 
    int listenPort = 8080; 
   /*
   * The server socket, used to accept client connection requests
   */
    ServerSocket server;
      
    
    try{
        if(args.length > 0){        
            listenPort = Integer.parseInt(args[0]);
        }
        
        //Instance the new clock for the server!
        Clock clock = new Clock();
        
        System.out.println("Listening for connection on port " + listenPort + " ...." );
        
        //bind the serverSocket with a port
        server = new ServerSocket(listenPort);
        
        while(true){
            Socket socket = server.accept();
            System.out.println("New client is detected!" );                     
            //handle each requested client
       
            new Thread(new ClientWorker(socket)).start();
        }
        
        
    }catch(Exception e){
          e.printStackTrace();
      } 
    }
}
