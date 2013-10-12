package ch.unibe.scg.team3.board;

/**
 * 
 * @author adrian
 *
 */
public class Token implements IToken {
	
	private final char letter;
	private final int value;
	
	public Token(char letter, int value){
		this.letter = letter;
		this.value = value;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char getLetter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "" + getLetter();
	}
}
