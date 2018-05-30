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
       int statusCode = 0;
        requestLine = reader.readline();
        System.out.println("First line contents: ");
        System.out.println(requestLine);
        
        if ("GET".equals(getMethod())){
            statusCode = GETHandler();
        }
        
        return statusCode;
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
      
    private int GETHandler() throws IOException{
       
        //retrieve the content-type section 10.5
        boolean isContentType = false;
        String line;
        
        do{
            line = reader.readline();
            isContentType = line.contains("Accept:");
        }while(!line.isEmpty() && !isContentType);
        
        if(!isContentType){
            return StatusCode.BAD_REQUEST;
        }else{
            
            requestContentType = line.substring("Accept: ".length());
            System.out.println("Negociation type: " + requestContentType);
        }
        
        return StatusCode.OK; //Well define request    
    }
    
}
