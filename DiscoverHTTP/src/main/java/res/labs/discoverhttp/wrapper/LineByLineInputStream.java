/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.wrapper;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;

/**
 *
 * Readline mimics the readline method in Reader object
 * This method detect the \r\n sequence and return the stream.
 * @author fidimala
 */
public class LineByLineInputStream extends FilterInputStream{
    //private final InputStream reader;
    public LineByLineInputStream(InputStream is){
        super(new BufferedInputStream(is));
    }
       
   
    public String readline() throws IOException{
       
        StringBuilder sb = new StringBuilder("");
        
        int readValue = super.read(); 
        
        boolean wasR_EOL = false;
        boolean wasN_EOL = false;
        
        while((readValue != -1)){            
            
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
            
            if(wasR_EOL && wasN_EOL){
                break;
            }else{
                readValue = super.read();
            }    
        }
        
        String line = sb.toString();
                
        return line;
    }
    
}
