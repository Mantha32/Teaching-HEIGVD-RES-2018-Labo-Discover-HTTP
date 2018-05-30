/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.handler;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

/**
 *
 * @author fidimala
 */
public class ResponseHandler {
    public final BufferedOutputStream writer;
    
    public ResponseHandler(OutputStream out){
     writer = new BufferedOutputStream(out);
    }
}
