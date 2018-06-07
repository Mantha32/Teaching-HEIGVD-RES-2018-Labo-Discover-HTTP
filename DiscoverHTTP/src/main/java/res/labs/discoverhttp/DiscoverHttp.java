package res.labs.discoverhttp;
import java.io.IOException;
import res.labs.discoverhttp.data.Hours;
import res.labs.discoverhttp.entity.ClientHTTP;
import res.labs.discoverhttp.entity.ServerHTTP;

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
Content-Length: 18
Content-Type: application/x-www-form-urlencoded
 
hour=15&minute=45


POST / HTTP/1.1
Host: localhost
User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
Accept: application/json
Accept-Language: en-us,en;q=0.5
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
Content-Length: 23
Content-Type: application/x-www-form-urlencoded
 
{"hour":15,"minute":46}
*/


public class DiscoverHttp {
  public static void main(String[] args) throws IOException{ 
    int port = 8080; 
    
    
    try{
        
        System.out.println("The argument , the app have got: " + args.length + " argument(s)!");
        
        
        for(String tmp: args){
            System.out.println(tmp);
        }
        
        System.out.println("-------------------------------------");
        
        
        //this application setup as HTTP client
        if(args.length > 1){  
            String hostname = args[0];
            port = Integer.parseInt(args[1]);
            String method = args[2];
            String ressource = args[3];
            String contentType = args[4];
            
            //handle the GET request
            ClientHTTP client = new ClientHTTP(hostname, port, method, ressource, contentType);
            client.sendRequest();
            client.readResponse();
            
            
            //handler the POST request
            if(args.length > 5){
                
                String[] hoursTokens = args[5].split(":");
                
                Hours hours = new Hours(Integer.parseInt(hoursTokens[0]), Integer.parseInt(hoursTokens[1]));
                client.sendRequest(method, hours);
                client.readResponse();
                client.close();
            } 
            
        }else{
        //this application setup as HTTP server
            if(args.length > 0)
              port = Integer.parseInt(args[0]);
            
            
            ServerHTTP server = new ServerHTTP(port);
            server.run();
        
        }   
        
    }catch(Exception e){
          e.printStackTrace();
      } 
    }
}
