
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
        
        header.append("Server: Dilifera clock service 1.0 \r\n");
        header.append("Date: ").append(clock.getGMTFormat()).append(CRLF);
        header.append("Content-Type: ").append(negociatedFormat).append(CRLF);
        header.append("Content-Length: ").append(contentSize).append(CRLF);
        header.append("Last-Modified: ").append(clock.getGMTFormat()).append(CRLF);
        header.append("Connection: Closed");
        header.append("Accept-Ranges: bytes");
        header.append(CRLF).append(CRLF);
        
    }
    
    private void setBody(){
        if(SupportedFormat.JSON.equals(negociatedFormat)){
            body.append(clock.toJson());
        }else if (SupportedFormat.XML.equals(negociatedFormat)){
            body.append(clock.toXML());
        }else{ // the default body is html
            body.append("<!DOCTYPE html>\n");
            body.append("<html>");
            body.append("<body>");
            body.append("<title> hours </title>");
            body.append("<h1> TIME : ");
            body.append(clock.getTime());
            body.append("</h1>");
            body.append("</body>");
            body.append("</html>");                  
        }
             
    }
    
    @Override
    public String toString(){
        setStatusLine();
        setBody();
        int contentLength = body.toString().length();
        setHeader(contentLength);
        
        header.append(body);
        
        return header.toString();
        
    }
       
}
