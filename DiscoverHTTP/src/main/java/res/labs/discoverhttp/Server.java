package res.labs.discoverhttp;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import res.labs.discoverhttp.data.Clock;

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
            
            //Handle each connexion 
            Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Send data to client " + server.getLocalSocketAddress());
                
                String httpResponse = "HTTP/1.1 200 OK\r\n" + clock.getTime() + "\r\n";
                try {                 
                    socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                    socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            serverThread.start();
            

        }
        
        
    }catch(Exception e){
          e.printStackTrace();
      } 
    }
}
