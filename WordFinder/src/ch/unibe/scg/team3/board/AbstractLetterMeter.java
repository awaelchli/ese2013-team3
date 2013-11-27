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

	protected HashMap<Character, Integer> table;

	protected AbstractLetterMeter() {
		table = new HashMap<Character, Integer>();
		init();
	}

	protected abstract void init();

	/**
	 * @param c
	 *            A character
	 * @return The value of the measured character. If there is no entry in the
	 *         hash-table, the value is 0.
	 */
	public int getValue(char c) {
		int value = table.get(c);
		return table.get(c) == null ? 0 : value;
	}

}
