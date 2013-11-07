package ch.unibe.scg.team3.token;

import ch.unibe.scg.team3.board.Point;

/**
 * This token is a special type of {@link IToken}. It is used to represent empty
 * tokens which have no letter
 * 
 * @author adrian
 */
public class NullToken extends AbstractToken {

	public static final char NULL_CHAR = ' ';

	public NullToken(Point coordinates){
		setCoordinates(coordinates);
	}
	
	public NullToken(int x, int y){
		this(new Point(x, y));
	}

	@Override
	public int getValue() {
		return 0;
	}

	@Override
	public char getLetter() {
		return NULL_CHAR;
	}

	@Override
	public IToken clone() {
		return new NullToken(getCoordinates());
	}

	@Override
	public boolean isEmpty() {
		return true;
	}
}
