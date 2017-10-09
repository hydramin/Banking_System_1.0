package system;

import java.util.TimerTask;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class MyTimeTask extends TimerTask {

	public void run() {
		System.out.println("Hello World!");
	}

	public static void main(String[] args) {

		// the Date and time at which you want to execute
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = dateFormatter.parse("2017-10-09 00:23:00");
//			System.out.println(date.getTime());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Now create the time and schedule it
		Timer timer = new Timer();

		// Use this if you want to execute it once
//		timer.schedule(new MyTimeTask(), date);

		// Use this if you want to execute it repeatedly
		 int period = 10000;//10secs
		 timer.schedule(new MyTimeTask(), date, period );
	}
}