package ch.unibe.scg.team3.board;

/**
 * 
 * @author adrian
 * 
 */
public class NullToken implements IToken {

	private static final NullToken INSTANCE = new NullToken();

	private NullToken() {

	}

	public static IToken getInstance() {
		return INSTANCE;
	}

	@Override
	public int getValue() {
		return 0;
	}

	@Override
	public char getLetter() {
		return ' ';
	}

	@Override
	public String toString() {
		return "" + getLetter();
	}

}
