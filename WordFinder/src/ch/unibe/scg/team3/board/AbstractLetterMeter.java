package ch.unibe.scg.team3.board;

import java.util.HashMap;

/**
 * This class provides the functionality to measure letters. Letters are mapped
 * in a hash table. Subclasses have to implement the method {@link #init()}.
 * 
 * @author adrian
 * 
 */
public abstract class AbstractLetterMeter {

	HashMap<Character, Integer> table;

	protected AbstractLetterMeter() {
		table = new HashMap<Character, Integer>();
		init();
	}

	protected abstract void init();

	public int getValue(char c) {
		return table.get(c);
	}

}
