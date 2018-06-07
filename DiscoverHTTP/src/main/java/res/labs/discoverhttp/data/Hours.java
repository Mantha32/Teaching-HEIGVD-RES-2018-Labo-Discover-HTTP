package res.labs.discoverhttp.data;

/**
 * This class is used to serialize/deserialize
 * 
 * JsonObjectMapper utility class can use this class.
 * 
 * @author Olivier Liechti
 */
public class Hours {
    private int hour;
    private int minute;

    public Hours (int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }
    
    //Introducing the dummy constructor to avoid error in processing string to java class
    public Hours(){}
    
    public void setHours(int hours){
        this.hour = hours;
    }
    
    public void setMinute(int minute){
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
