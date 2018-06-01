package res.labs.discoverhttp.handler;

/**
 *
 * @author fidimala
 */
public class SupportedFormat {
    public static String HTML = "text/html";
    public static String XML = "application/xml";
    public static String JSON = "application/json";
    public static String ALL = "*/*"; //compatibility with the client curl
    
    public static boolean isSuppportedFormat(String format){
       
        return HTML.equals(format) || JSON.equals(format) || XML.equals(format) || ALL.equals(format);
    }
}
