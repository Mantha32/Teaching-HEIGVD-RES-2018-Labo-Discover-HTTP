
package res.labs.discoverhttp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import res.labs.discoverhttp.handler.StatusCode;
import res.labs.discoverhttp.handler.SupportedFormat;

/**
 *
 * @author fidimala
 */
public final class RawHTML {
    private String contentType;
    private final StringBuilder header;
    private final StringBuilder body;
    private final String CRLF = "\r\n";
    private final String negociatedFormat;
    private final Clock clock;
    private final int statusCode;
        
    public RawHTML(int statusCode, String negociatedFormat, Clock clock){
        header = new StringBuilder();
        body = new StringBuilder();
        this.negociatedFormat = negociatedFormat;
        this.statusCode = statusCode;
        this.clock = clock;
    }
    
    private void setStatusLine(){
        header.append("HTTP/1.1 ");
        header.append(statusCode);
        header.append(" ");
        header.append(StatusCode.getStatusMessage(statusCode)).append(CRLF);          
    }
    
    
    private void setHeader(int contentSize){
        
        setStatusLine();
        header.append("Server: Dilifera clock service 1.0 \r\n");
        header.append("Date: ").append(clock.getGMTFormat()).append(CRLF);
        header.append("Content-Type: ").append(negociatedFormat).append(CRLF);
        header.append("Content-Length: ").append(contentSize).append(CRLF);
        header.append("Last-Modified: ").append(clock.getGMTFormat()).append(CRLF);
        header.append("Connection: Closed").append(CRLF);
        header.append("Accept-Ranges: bytes");
        header.append(CRLF).append(CRLF);
        
    }
    
    private void setBody(int status, String contentType) throws JsonProcessingException{
        if(SupportedFormat.JSON.equals(contentType)){
            if(status == StatusCode.OK){
                body.append(clock.toJson());
            }else{
                setBodyHTML(status);
            }               
        }else if (SupportedFormat.XML.equals(contentType)){
            if(status == StatusCode.OK){
                body.append(clock.toXML());
            }else{
                setBodyHTML(status);
            }      
        }else{ // the default body is text/html
            setBodyHTML(status);
            
        }
                     
    }
    
    //handle the error status et text/html message, the text/html is used to perform the error message
    private void setBodyHTML(int status){
    // the default body is text/html
        body.append("<!DOCTYPE html>\n");
        body.append("<html>");
        body.append("<body>");
        body.append("<title> Labo Hours Server </title>");
        switch(status){
            case StatusCode.OK:
                body.append("<h1> Time : ");
                body.append(clock.getTime());
                body.append("</h1>");
                break; 
            case StatusCode.CREATED:
                body.append("<h1> INFO 201: The clock is updated! </h1>");  
                break;                
            case StatusCode.BAD_REQUEST:
                body.append("<h1> ERROR 400: BAD REQUEST </h1>");  
                break;
            case StatusCode.FORBIDDEN:
                body.append("<h1> ERROR 403: FORBIDDEN </h1>");
                body.append("<p> You don't have the permission to access this ressource on this server.</p>" );
                break;
            case StatusCode.NOT_FOUND:
                body.append("<h1> ERROR 404: RESSOURCE NOT FOUND </h1>");
                body.append("<p> The requested URL  was not found on this server.</p>" );

                break;
            case StatusCode.METHOD_NOT_ALLOWED:
                body.append("<h1> ERROR 405: METHOD NOT YET SUPPORTED</h1>");
                break;
            
        }

        body.append("</body>");
        body.append("</html>");                
    }
    
    @Override
    public String toString() {
        
        try {
            setBody(statusCode, negociatedFormat);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RawHTML.class.getName()).log(Level.SEVERE, null, ex);
        }
        int contentLength = body.toString().length();
        setHeader(contentLength);
        
        header.append(body);
        
        return header.toString();
        
    }
       
}
