package res.labs.discoverhttp.handler;

import java.io.IOException;
import java.io.InputStream;
import res.labs.discoverhttp.wrapper.LineByLineInputStream;

/**
 * This class parse the request header.
 * We select the field that we need to do the right job
 * Request-line section 5.1
 * Content type section 10.5
 * 
 * This minimalist implementation handle the object we need
 * @author fidimala
 */
public class RequestHandler {
    private  String requestLine = "";
    private  String requestContentType = "";
    private final LineByLineInputStream reader;

    public RequestHandler(InputStream is) throws IOException {
        reader = new LineByLineInputStream(is);   
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public int processing() throws IOException{
        requestLine = reader.readline();
        
        //retrieve the content-type section 10.5
        boolean isContentType = false;
        String line = reader.readline();
        while(!line.isEmpty() && !isContentType){
            isContentType = line.contains("Accept:");
        }
        
        if(line.isEmpty()){
            return 400; //Bad request status
        }else{
            
            requestContentType = line.substring("Accept: ".length());
        }
        
        return 200; //Well define request
    }
    
    /**
     * Retrieve the HTTP method in the request-line section 5.1
     * We design this function according the specific implementation we have to do
     * amid GET and POST method
     * 
     * @return the HTTP method in the request-line
     */
    public String getMethod(){
        return requestLine.split(" ")[0];
    }
    
    public String getHttpVersion(){
        return requestLine.split(" ")[2];
    }
    
    public String[] getRequestContentType(){
        return requestContentType.split(",");
    }
      
}
