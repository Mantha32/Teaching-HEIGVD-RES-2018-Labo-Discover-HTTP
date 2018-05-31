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
}
