/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.data;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Rafidimalala
 */

/**
 * This class provide clock using the current date using the following format:
 * "hh:mm"
 * The deltaTime is the difference with the current clock in the current time
 * when the host set the clock, we use deltaTime to record the difference the current clock 
 * and the host provide by the host
 * 
 */
public class Clock {
    private int deltaHour = 0;
    private int deltaMinute = 0;
    private final DateFormat dateFormat;

    
    public Clock(){
        dateFormat = new SimpleDateFormat("HH:mm");
        deltaHour = 0;
        deltaMinute = 0;

    }
   
   /**
    * Process the ellasped time between the current clock and the clock that the client would like
    * @param hostTime is the posetd data in the body request following this format
    * hour=15&minute=45
    */   
   public void set(int hour, int minute){
        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        deltaHour = hour - cal.get(Calendar.HOUR_OF_DAY);
        deltaMinute = minute - cal.get(Calendar.MINUTE);        
   }
   
   /**
    * provide the clock when the client fetch it
    * the clock is altered if the client set up for this own timer
    * @return the current clock or the alter clock when the host set up the clock
    */
   public String getTime(){   
        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        
       if(deltaHour != 0){
          cal.add(Calendar.HOUR, deltaHour); 
       }
       
       if(deltaMinute != 0){
           cal.add(Calendar.MINUTE, deltaMinute);
       }
       
       return dateFormat.format(cal.getTime());
       
   }
   
   public String toJson(){
       String tmp[] = getTime().split(":");
       
       Hours hours = new Hours(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
               
        //return JsonObjectMapper.toJson(hours);
        
        return hours.toJson();    
   }
   
   public String getGMTFormat(){
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        return df.format(now);
   }
   
   public String toXML(){
       StringBuilder tmp = new StringBuilder();
       String currentHour[] = getTime().split(":");
        
       tmp.append("<?xml version = \"1.0\"?>");
       tmp.append("<hour-info>");
       tmp.append("<hour>");
       tmp.append(currentHour[0]);
       tmp.append("</hour>");
       tmp.append("<minute>");
       tmp.append(currentHour[1]);
       tmp.append("</minute>");
       tmp.append("</hour-info>");
       
       return tmp.toString();
   }
}
