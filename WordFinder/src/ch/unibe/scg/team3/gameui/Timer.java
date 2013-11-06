package ch.unibe.scg.team3.gameui;

import android.os.CountDownTimer;

/**
 * 
 * @author lukas
 * @author adrian
 */
public abstract class Timer extends CountDownTimer {
	
	private long remainingTime;

	public Timer(long millisInFuture) {
		super(millisInFuture, 1000);
	}

	@Override
	public void onTick(long millisUntilFinished) {
		remainingTime = millisUntilFinished;
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
