package res.labs.discoverhttp.data;

/**
 *
 * @author fidimala
 */
public class Hours {
    private final int hour;
    private final int minute;

    public Hours (int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }
    
    
    public int getHour(){
        return hour;
    }
    
    public int getMinute(){
        return minute;
    }
    
    @Override
    public String toString(){
        return Integer.toString(hour) + ":" + Integer.toString(minute);
    }
    
    public String toJson(){
     StringBuilder sb = new StringBuilder("{ \"hour\": \"");
     sb.append(hour).append("\",");
     sb.append("\"minute\": \"");
     sb.append(minute).append("\"}");
     
     return sb.toString();
     
    }
    
    public String toXML(){
       StringBuilder tmp = new StringBuilder();

       tmp.append("<?xml version = \"1.0\"?>");
       tmp.append("<hour-info>");
       tmp.append("<hour>");
       tmp.append(hour);
       tmp.append("</hour>");
       tmp.append("<minute>");
       tmp.append(minute);
       tmp.append("</minute>");
       tmp.append("</hour-info>");
       
       return tmp.toString();
    }
    
}
