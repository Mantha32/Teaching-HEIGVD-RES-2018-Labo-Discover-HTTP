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
    private  String[] requestLine;
    private  String requestContentType = "";
    private final LineByLineInputStream reader;
    private String data= "";
    

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
       
        String tmpLine = reader.readline();
        System.out.println("First line contents: " + tmpLine);
        requestLine = tmpLine.split(" ");
        
        //Retrieve the negociated format 
        
        
        if ("GET".equals(getMethod())){
            statusCode = GETHandler();
        }
        
        if ("POST".equals(getMethod())){
            statusCode = POSTHandler();
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
        return requestLine[0];
    }
    
    public String getRequestedRessource(){
        return requestLine[1];
    }
    
    public String getHttpVersion(){
        return requestLine[2];
    }
    
    public String[] getRequestContentType(){
        return requestContentType.split(",");
    }
    
    public String getData(){
        return data;
    }
    private int GETHandler() throws IOException{
        String line;
        
        System.out.println("METHOD: " + getMethod());
        System.out.println("Requested ressource: --" + getRequestedRessource() + "--");
        
        
        //retrieve the content-type section 10.5
        line = retrieveLineBy("Accept:");
        requestContentType = line.substring("Accept: ".length());
        System.out.println("CONTENT TYPE: " + requestContentType);
        
        if("/".equals(getRequestedRessource())){

           /*
            We assume that the first foramt is used to precess the response and if
            the requested format in not valid , we send an error: bad request
            */ 
           String tmpContentType = getRequestContentType()[0];
            System.out.println("Negociated content-type format: --" + tmpContentType + "--");
            
            if(line.isEmpty() || (!SupportedFormat.isSuppportedFormat(tmpContentType))){
                return StatusCode.BAD_REQUEST;
            }else{
                System.out.println("Use this content type to process the response: --" + tmpContentType + "--");
              
            }   
        }else{
            //Ressource not found
            return StatusCode.NOT_FOUND;
        }

        
        return StatusCode.OK; //Well define request    
    }
    
    private int POSTHandler() throws IOException{
        String contentLengthLine;
        //retrieve the content-type section 10.5
        int status = GETHandler();
        
        contentLengthLine = retrieveLineBy("Content-Length: ");
        //escape the space
        contentLengthLine = contentLengthLine.substring("Content-Length: ".length());
        if(contentLengthLine.isEmpty())
            return StatusCode.BAD_REQUEST;
        
        int contentLength = Integer.parseInt(contentLengthLine);
        
        //Consume the CRLF that separates the header and the body
        reader.readline();
        
        data = reader.readline();
        
        if(data.isEmpty())
            return StatusCode.BAD_REQUEST;
        
        return StatusCode.OK; //Well define request   
        
    }
    
    private String retrieveLineBy(String token) throws IOException{
        //retrieve the content-type section 10.5
        boolean isContentType;
        String line = "";
        
        do{
            line = reader.readline();
            isContentType = line.contains(token);
        }while(!line.isEmpty() && !isContentType); 
        
        return line;
    }
    
}
