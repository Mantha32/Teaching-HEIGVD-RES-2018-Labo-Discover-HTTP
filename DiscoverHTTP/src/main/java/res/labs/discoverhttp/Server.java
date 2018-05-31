package res.labs.discoverhttp;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import res.labs.discoverhttp.data.Clock;

/**
 *
 * @author Rafidimalala
 */

/*
Exeample header

GET / HTTP/1.1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9;q=0.8
Accept: application/xml
Accept: application/json


POST / HTTP/1.1
Host: localhost
User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
Accept: text/html,application/xhtml+xml,application/xml;q=0.9;q=0.8
Accept-Language: en-us,en;q=0.5
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
Content-Type: application/x-www-form-urlencoded
Content-Length: 18
 
hour=15&minute=45

POST / HTTP/1.1
Host: localhost
User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
Accept: application/json
Accept-Language: en-us,en;q=0.5
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
Content-Type: application/x-www-form-urlencoded
Content-Length: 28
 
{"hour"="15","minute"="45"}
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
            System.out.println("--------------------------------------" );   
            System.out.println("New client is detected!" );                     
            //handle each requested client
       
            new Thread(new ClientWorker(socket,clock)).start();
            
            System.out.println("End connexion");
        }
        
        
    }catch(Exception e){
          e.printStackTrace();
      } 
    }
}
