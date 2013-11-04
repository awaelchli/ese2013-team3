package ch.unibe.scg.team3.game;

import ch.unibe.scg.team3.board.Point;

/**
 * An IElement represents an element in the board. This interface is 
 * mainly used for the {@see Path} class to validate coordinates.
 * 
 * @author adrian
 *
 */
public interface IElement {

	public Point getCoordinates();
	
	public void setCoordinates(final Point point);
}
