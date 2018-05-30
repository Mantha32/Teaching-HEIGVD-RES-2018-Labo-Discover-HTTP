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
import res.labs.discoverhttp.handler.RequestHandler;
import res.labs.discoverhttp.handler.ResponseHandler;
import res.labs.discoverhttp.wrapper.LineByLineInputStream;

/**
 *
 * @author fidimala
 */
public class ClientWorker implements Runnable{
    private final Socket socketClient;
    RequestHandler requestHandler = null;
    ResponseHandler responseHandler = null;
    
    
    public ClientWorker(Socket socket) throws IOException{
        socketClient = socket;
        requestHandler = new RequestHandler(socket.getInputStream());
        responseHandler = new ResponseHandler(socket.getOutputStream());
    }
    
    public int parseRequest() throws IOException{
      return requestHandler.processing();
   }
    
public void sendResponse() throws IOException{
    int status = parseRequest();
    
    responseHandler.send(requestHandler.getRequestContentType()[0], requestHandler.getMethod(), status);
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
