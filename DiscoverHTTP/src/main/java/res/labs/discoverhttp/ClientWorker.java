/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import res.labs.discoverhttp.data.Clock;
import res.labs.discoverhttp.handler.RequestHandler;
import res.labs.discoverhttp.handler.ResponseHandler;

/**
 *
 * @author fidimala
 */
public class ClientWorker implements Runnable{
    private final Socket socketClient;
    RequestHandler requestHandler = null;
    ResponseHandler responseHandler = null;
    Clock clock;
    
    
    public ClientWorker(Socket socket, Clock clock) throws IOException{
        socketClient = socket;
        requestHandler = new RequestHandler(socket.getInputStream());
        responseHandler = new ResponseHandler(socket.getOutputStream());
        this.clock = clock;
    }
    
    public int parseRequest() throws IOException{
      return requestHandler.processing();
   }
    
public void sendResponse() throws IOException{
    int status = parseRequest();
    String contentType = requestHandler.getRequestContentType()[0];
    String requestedMethod = requestHandler.getMethod();
    System.out.println("CONTENT TYPE: " + contentType);
    System.out.println("METHOD: " + requestedMethod);
    
    //Update the clock
    if("POST".equals(requestedMethod)){
        clock.set(requestHandler.getData());
    }
    
    responseHandler.send(contentType, requestedMethod, status, clock);
    System.out.println("data sending for client");
    System.out.println("End connexion");
    
    } 

    @Override
    public void run(){
        try {
            sendResponse();
            socketClient.close();
        }catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    
}
