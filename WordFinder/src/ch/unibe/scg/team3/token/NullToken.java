package ch.unibe.scg.team3.token;

import ch.unibe.scg.team3.board.Point;

/**
 * This token is a special type of {@link IToken}. It implements the singleton
 * design pattern.
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

	@Override
	public IToken clone() {
		return new NullToken();
	}

	@Override
	public Point getCoordinates() {
		return new Point(-1, -1);
	}

	@Override
	public void setCoordinates(Point point) {
	}
}
