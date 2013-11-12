package ch.unibe.scg.team3.gameui;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author lukas
 * @author adrian
 */
public abstract class Timer extends CountDownTimer {
	
	private long remainingTime;
	private TextView view;
	private final Activity context;

	public Timer(long millisInFuture, TextView countdownView, Activity context) {
		super(millisInFuture, 1000);
		this.context = context;
		view = countdownView;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		remainingTime = millisUntilFinished;
		
		 context.runOnUiThread(new Runnable(){

		      @Override
		      public void run(){
		    	  view.setText(format(remainingTime));
		      }
		  });     
		
	}

	public long getRemainingTime() {
		return remainingTime;
	}
	
	@Override
	public String toString() {
		return format(remainingTime);
	}

	public static String format(long millis) {
		long seconds = (millis / 1000) % 60;
		long minutes = ((millis / (1000 * 60)) % 60);

		String minText = String.format("%02d", minutes);
		String secText = String.format("%02d", seconds);

		return minText + ":" + secText;
	}
}
