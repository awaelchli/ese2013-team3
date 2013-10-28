package ch.unibe.scg.team3.game;

import java.util.Iterator;
import java.util.LinkedList;

import ch.unibe.scg.team3.gameui.BoardButton;

/**
 * The Path contains a list of buttons which were selected.
 * The responsibility of the path is to know if he is valid or not.
 * 
 * @author adrian
 */

//TODO: make it Path<T> 
public class Path implements Iterable<BoardButton>{

	private LinkedList<BoardButton> path;
	private int color;

	public Path() {
		path = new LinkedList<BoardButton>();
	}

	/**
	 * @param button The button to be added, not null
	 * @return True, if the button is not already in the path and adjacent to the last added button, false otherwise
	 */
	public boolean add(BoardButton button) {
		
		if (path.contains(button))
			return false;

		if (0 < path.size() && !isAdjacent(button, path.getLast()))
			return false;

		path.add(button);
		return true;
	}
	
	public void clear(){
		path.clear();
	}
	
	/**
	 * @return The length of the path is greater or equal to zero.
	 */
	public int length(){
		return path.size();
	}

	@Override
	public Iterator<BoardButton> iterator() {
		return path.iterator();
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getColor() {
		return color;
	}
	
	private static boolean isAdjacent(BoardButton b1, BoardButton b2) {

		int b1_x = b1.getCoordinates().getX();
		int b1_y = b1.getCoordinates().getY();
		int b2_x = b2.getCoordinates().getX();
		int b2_y = b2.getCoordinates().getY();
		
		if ((Math.abs(b1_x - b2_x) > 1) || Math.abs(b1_y - b2_y) > 1) {
			return false;
		}
		return true;
	}

	public boolean isEmpty() {
		return path.isEmpty();
	}

}
