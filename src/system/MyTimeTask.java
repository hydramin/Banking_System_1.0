package system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyTimeTask implements Runnable {

	static ScheduledExecutorService pay;
	static int time;
	public MyTimeTask() {
		pay = Executors.newSingleThreadScheduledExecutor();
		pay.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);		
	}
	public void run() {
		System.out.println("Hello World!");
	}

	
	public static void main(String[] args) {
		MyTimeTask t1 = new MyTimeTask();
		MyTimeTask t2 = new MyTimeTask();
		System.out.println(t1.pay);
		System.out.println(t2.pay);		
	}
	
}