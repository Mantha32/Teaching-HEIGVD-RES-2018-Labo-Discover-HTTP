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
    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    
    private static final Map<String,String> statusMessage = createMap();
    
     
    private static Map<String,String> createMap(){
        Map<String,String> tmpMap;
        tmpMap = new HashMap<>();
        tmpMap.put(Integer.toString(OK),"OK");
        tmpMap.put(Integer.toString(BAD_REQUEST), "Bad Request");
        tmpMap.put(Integer.toString(FORBIDDEN), "Forbidden");
        tmpMap.put(Integer.toString(NOT_FOUND), "not Found");
        
        return tmpMap;
    }
                                                               
    public  static String getStatusMessage(int statusCode){
       return statusMessage.get(Integer.toString(statusCode));
    }
       
}
