package token;


/**
 * 
 * @author adrian
 * 
 */
public class NullToken extends AbstractToken {

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
}
