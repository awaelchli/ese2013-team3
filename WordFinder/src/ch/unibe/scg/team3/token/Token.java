package ch.unibe.scg.team3.token;


/**
 * 
 * @author adrian
 * 
 */
public class Token extends AbstractToken {

	final char letter;
	final int value;

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
}
