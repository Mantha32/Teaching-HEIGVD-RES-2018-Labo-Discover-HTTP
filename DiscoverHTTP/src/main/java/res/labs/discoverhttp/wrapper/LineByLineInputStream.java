/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.wrapper;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * Readline mimics the readline method in Reader object
 * This method detect the \r\n sequence and return the stream.
 * @author fidimala
 */
public class LineByLineInputStream extends FilterInputStream{
    //private final InputStream reader;
    public LineByLineInputStream(InputStream is){
        super(is);
    }
       
   
    public String readline() throws IOException{
        StringBuilder sb = new StringBuilder();
        int readValue = super.read();
        boolean wasR_EOL = false;
        boolean wasN_EOL = false;
        
        while((readValue != -1) && (!wasR_EOL || !wasN_EOL)){            
            
            switch(readValue){
                case '\r':
                    wasR_EOL = !wasR_EOL; // keep track the MAC EOL 
                    break;
                case '\n':
                    wasN_EOL = !wasN_EOL; //End up the streaming reading
                    break;
                default:
                    sb.append((char)readValue);
            }
            
            readValue = super.read();
        }
        
        return sb.toString();
    }
    
}
