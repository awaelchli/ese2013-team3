package ch.unibe.scg.team3.gameui;

import android.os.CountDownTimer;
import android.widget.TextView;

public abstract class Timer extends CountDownTimer{
    private TextView timerTextView;
    private long remainingTime;
    
	public Timer(long millisInFuture, long countDownInterval, TextView timerTextView) {
		super(millisInFuture, countDownInterval);
		this.timerTextView = timerTextView;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		remainingTime = millisUntilFinished;
		
		long seconds = (millisUntilFinished / 1000) % 60 ;
		long minutes = ((millisUntilFinished / (1000*60)) % 60);
		
	    String minText = String.format("%02d", minutes);	
	    String secText = String.format("%02d", seconds);
	    
		timerTextView.setText(minText + ":" + secText );	
	    
	}
	
	public long getRemainingTime(){
		return remainingTime;
		
	}
}
