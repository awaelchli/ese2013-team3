package ch.unibe.scg.team3.path;

import ch.unibe.scg.team3.game.IElement;

public class ColoredPath<E extends IElement> extends Path<E> {

	private int color;

	/**
	 * Set the color of the ch.unibe.scg.team3.path to mark its state.
	 * 
	 * @param color
	 *            The color
	 */
	public void setColor(int color) {
		this.color = color;
	}

	public int getColor() {
		return color;
	}

}
