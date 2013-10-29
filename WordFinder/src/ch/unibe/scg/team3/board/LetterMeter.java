package ch.unibe.scg.team3.board;

import java.util.HashMap;

public abstract class LetterMeter {
	
	HashMap<Character, Integer> table;
	
	protected LetterMeter(){
		table = new HashMap<Character, Integer>(); 
		init();
	}
	
	protected abstract void init();
	
	public int getValue(char c) {
		return table.get(c);
	}

}
