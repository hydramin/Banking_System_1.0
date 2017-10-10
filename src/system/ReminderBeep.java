package system;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple demo that uses java.util.Timer to schedule a task to execute once 5
 * seconds have passed.
 */

public class ReminderBeep extends TimerTask {
  Toolkit toolkit;

  Timer timer;

  public ReminderBeep(int seconds) {
//    toolkit = Toolkit.getDefaultToolkit();
    timer = new Timer();
    timer.schedule(this, seconds * 1000, seconds * 1000);
  }
  
  
    public void run() {
      System.out.println("Time's up!");
//      toolkit.beep();
      timer.cancel(); //Not necessary because we call System.exit
      //System.exit(0); //Stops the AWT thread (and everything else)
//      new Timer().schedule(new RemindTask(), 5 * 1000, 3 * 1000);
    }
  

  public static void main(String args[]) {
    System.out.println("About to schedule task.");
    new ReminderBeep(5);
    System.out.println("Task scheduled.");
    
  }
}
