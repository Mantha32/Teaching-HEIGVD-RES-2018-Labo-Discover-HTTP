/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.handler;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fidimala
 */
public class StatusCode {
    public final int OK = 200;
    public final int BAD_REQUEST = 400;
    public final int FORBIDDEN = 403;
    public final int NOT_FOUND = 404;
    
    private final static Map<String,String> statusMessage = createMap();
    
     private static final Map<String, String> myMap = createMap();
     
    private static Map<String,String> createMap(){
        Map<String,String> tmpMap;
        tmpMap = new HashMap<>();
        tmpMap.put(Integer.toString(OK), "OK");
        tmpMap.put(Integer.toString(BAD_REQUEST), "Bad Request");
        tmpMap.put(Integer.toString(FORBIDDEN), "Forbidden");
        tmpMap.put(Integer.toString(NOT_FOUND), "not Found");
        
        return tmpMap;
    }
                                                               
    
    
    
}
