package ch.unibe.scg.team3.token;

import ch.unibe.scg.team3.board.Point;

/**
 * The abstract token models the basic properties of an element on the board.
 * The abstract token is represented by a letter, a value, and a coordinate.
 * 
 * @author adrian
 * @see IToken
 */
public abstract class AbstractToken implements IToken {

	private Point coordinates;

	@Override
	public abstract int getValue();

	@Override
	public abstract char getLetter();

	@Override
	public abstract IToken clone();

	@Override
	public Point getCoordinates() {
		return coordinates;
	}

	@Override
	public void setCoordinates(Point point) {
		coordinates = point;
	}
	
	public abstract boolean isEmpty();

	@Override
	public boolean letterEquals(IToken tok) {
		return getLetter() == tok.getLetter();
	}

	@Override
	public String toString() {
		return "" + getLetter();
	}

	@Override
	public boolean equals(Object o) {

		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		IToken tok = (IToken) o;

		boolean letters = tok.getLetter() == this.getLetter();
		boolean values = tok.getValue() == this.getValue();
		boolean coordinates = tok.getCoordinates().equals(this.getCoordinates());

		return letters && values && coordinates;
	}
}