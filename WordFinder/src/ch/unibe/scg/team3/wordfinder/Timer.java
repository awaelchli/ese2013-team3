package ch.unibe.scg.team3.wordfinder;

import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer extends CountDownTimer {

	public Timer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	@Override
	public void onFinish() {	
	}

	@Override
	public void onTick(long arg0) {
	}
	
	public void onPause(long arg0) {
	}

}
