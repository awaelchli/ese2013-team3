package ch.unibe.scg.team3.wordfinder;

import ch.unibe.scg.team3.game.Game;
import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer extends CountDownTimer{
    private TextView timerDisplayer = null;
    private long remainingTime;
    
	public Timer(long millisInFuture, long countDownInterval, TextView timerTextField) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
		timerDisplayer = timerTextField;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		int seconds = (int) (millisUntilFinished / 1000) % 60 ;
		int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);	
	    String minText = String.format("%02d", minutes);	
	    String secText = String.format("%02d", seconds);
	    timerDisplayer.setText( minText + ":" + secText );	
	    remainingTime = millisUntilFinished;
	}
	
	public long getRemainingTime(){
		return remainingTime;
		
	}
	
	
	@Override
	public void onFinish() {
		timerDisplayer.setText("Your time is over!!!");
		Game.setIsOver();
	}
}
