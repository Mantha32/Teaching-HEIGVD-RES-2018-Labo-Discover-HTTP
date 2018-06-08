package res.labs.discoverhttp.entity;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import res.labs.discoverhttp.DiscoverHttp;
import res.labs.discoverhttp.data.Clock;
import res.labs.discoverhttp.data.Hours;
import res.labs.discoverhttp.data.JsonObjectMapper;
import res.labs.discoverhttp.data.XmlObjectMapper;
import res.labs.discoverhttp.handler.RequestHandler;
import res.labs.discoverhttp.handler.ResponseHandler;
import res.labs.discoverhttp.handler.SupportedFormat;

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
   
    //Update the clock
    if("POST".equals(requestedMethod)){
        
        //Deal with requested JSON format on demand
        if(SupportedFormat.JSON.equals(contentType)){
            Hours hours;
            hours = JsonObjectMapper.parseJson(requestHandler.getData(), Hours.class);
            
            clock.set(hours.getHour(), hours.getMinute());
            
        }else if(SupportedFormat.XML.equals(contentType)){
            Hours hours;
            hours = XmlObjectMapper.parseXml(requestHandler.getData(), Hours.class);
            
            clock.set(hours.getHour(), hours.getMinute());            
        } else{
            /*
            String[] tmp = requestHandler.getData().split("&");
            String[] tmpHour = tmp[0].split("=");
            String[] tmpMinute = tmp[1].split("=");
            */
            String[] tmpHour = requestHandler.getData().split(":");
            clock.set(Integer.parseInt(tmpHour[1]), Integer.parseInt(tmpHour[1]));
        }
    }
    
    System.out.println("data sending for client");
    
    responseHandler.send(contentType, requestedMethod, status, clock);
    

    } 

    @Override
    public void run(){
        try {
            sendResponse();
            socketClient.close();
            System.out.println("Connexion is closed!!!");
        }catch (IOException ex) {
            Logger.getLogger(DiscoverHttp.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    
}
