package ch.unibe.scg.team3.game;

import android.os.CountDownTimer;

/**
 * A simple timer that counts down the time from a given initial time.
 * 
 * @author lukas
 * @author adrian
 */
public abstract class Timer extends CountDownTimer {

	private final long initialTime;
	protected long remainingTime;

	/**
	 * @param millisInFuture
	 *            The time in milliseconds from which the counter should count
	 *            down. This must be a positive value.
	 */
	public Timer(long millisInFuture) {
		super(millisInFuture, 1000);
		initialTime = millisInFuture;
		remainingTime = initialTime;
		assert invariant();
	}

	private boolean invariant() {
		return initialTime >= 0 && remainingTime >= 0;
	}

	/**
	 * This method will be called every second after the timer has been started
	 * by {@link #start()} until the countdown reaches zero.
	 */
	@Override
	public void onTick(long millisUntilFinished) {
		remainingTime = millisUntilFinished;
		assert invariant();
	}

	/**
	 * @return The remaining time in milliseconds.
	 */
	public long getRemainingTime() {
		return remainingTime;
	}

	/**
	 * @return The elapsed time in milliseconds since the start of the
	 *         countdown.
	 */
	public long getElapsedTime() {
		return initialTime - remainingTime;
	}

	/**
	 * Represents the timer as the remaining time.
	 * 
	 * @see #format(long)
	 */
	@Override
	public String toString() {
		return format(remainingTime);
	}

	/**
	 * Converts milliseconds into a human readable time format.
	 * 
	 * @param millis
	 *            The milliseconds to be formated
	 * @return The time in the format mm:ss.
	 */
	public static String format(long millis) {
		long seconds = (millis / 1000) % 60;
		long minutes = ((millis / (1000 * 60)) % 60);

		String minText = String.format("%02d", minutes);
		String secText = String.format("%02d", seconds);

		return minText + ":" + secText;
	}
}
