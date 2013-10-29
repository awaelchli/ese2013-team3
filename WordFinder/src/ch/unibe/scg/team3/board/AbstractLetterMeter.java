package ch.unibe.scg.team3.board;

import java.util.HashMap;

public abstract class AbstractLetterMeter {
	
	HashMap<Character, Integer> table;
	
	protected AbstractLetterMeter(){
		table = new HashMap<Character, Integer>(); 
		init();
	}
	
	protected abstract void init();
	
	public int getValue(char c) {
		return table.get(c);
	}

}
