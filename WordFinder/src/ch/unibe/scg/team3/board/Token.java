package ch.unibe.scg.team3.board;

/**
 * 
 * @author adrian
 * 
 */
public class Token implements IToken {

	private final char letter;
	private final int value;

	public Token(char letter, int value) {
		this.letter = letter;
		this.value = value;
	}

	@Override
	public int getValue() {
		return this.value;
	}

	@Override
	public char getLetter() {
		return this.letter;
	}

	@Override
	public String toString() {
		return "" + getLetter();
	}
}
