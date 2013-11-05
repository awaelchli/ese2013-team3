package ch.unibe.scg.team3.token;

import ch.unibe.scg.team3.board.Point;
import ch.unibe.scg.team3.game.IElement;


/**
 * @author adrian
 */
public class Token extends AbstractToken implements IElement {

	private final char letter;
	private final int value;
	private Point coordinates;

	public Token(char letter, int value) {
		this.letter = letter;
		this.value = value;
	}
	
	public Token(char letter, int value, Point coordinates){
		this(letter, value);
		this.coordinates = coordinates;
	}
	
	public Token(char letter, int value, int x, int y){
		this(letter, value, new Point(x, y));
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
	public IToken clone() {
		return new Token(letter, value);
	}

	@Override
	public Point getCoordinates() {
		return coordinates;
	}

	@Override
	public void setCoordinates(Point point) {
		coordinates = point;
	}
}
