package ch.unibe.scg.team3.path;

import ch.unibe.scg.team3.game.IElement;

/**
 * The colored path has the additional functionality to represent its state with
 * a color encoded in a integer.
 * 
 * @author adrian
 */
public class ColoredPath<E extends IElement> extends Path<E> {

	private int color;

	/**
	 * Set the color of the path to mark its state.
	 * 
	 * @param color
	 *            An integer representing the color of this path.
	 */
	public void setColor(int color) {
		this.color = color;
	}

	public int getColor() {
		return color;
	}

}
