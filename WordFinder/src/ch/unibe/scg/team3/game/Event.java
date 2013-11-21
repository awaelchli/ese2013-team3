package ch.unibe.scg.team3.game;

public class Event {
	
	public static final int BOARD_CREATED = 0;
	public static final int BOARD_UPDATED = 1;
	public static final int GAME_OVER = 2;
	public static final int WORD_FOUND = 3;
	public static final int TIME_TICK = 4;
	

	private int action;
	
	public Event(int action){
		this.action = action;
	}
	
	public int getAction(){
		return action;
	}
}
