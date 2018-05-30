/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.labs.discoverhttp.data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    * Process the elasped time between the current clock and the clock that the client would like
    * @param hostTime 
    */ 
   public void set(String hostTime){
       String[] tmp = hostTime.split(":");
       
        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        
       deltaHour = Integer.getInteger(tmp[0]) - cal.get(Calendar.HOUR_OF_DAY);
       deltaMinute = Integer.getInteger(tmp[1]) - cal.get(Calendar.MINUTE);
          
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
       return "{ hour: " + tmp[0] + ", minute: " + tmp[1] + "}";
   }
}
