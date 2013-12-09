package ch.unibe.scg.team3.token;

import ch.unibe.scg.team3.board.Point;
import ch.unibe.scg.team3.game.IElement;

/**
 * @author adrian
 */
public class Token extends AbstractToken implements IElement {

	private final char letter;
	private final int value;

	public Token(char letter, int value, Point coordinates) {
		this.letter = letter;
		this.value = value;
		setCoordinates(coordinates);
	}

	public Token(char letter, int value, int x, int y) {
		this(letter, value, new Point(x, y));
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public char getLetter() {
		return letter;
	}

	@Override
	public IToken clone() {
		return new Token(letter, value, getCoordinates());
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
