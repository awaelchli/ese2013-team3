package ch.unibe.scg.team3.token;

import ch.unibe.scg.team3.game.IElement;

/**
 * @author adrian
 */
public interface IToken extends IElement{

	public int getValue();

	public char getLetter();

	public IToken clone();
}
