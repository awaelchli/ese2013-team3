package ch.unibe.scg.team3.game;

import java.util.Iterator;
import java.util.LinkedList;

import ch.unibe.scg.team3.gameui.BoardButton;

/**
 * 
 * @author adrian
 *
 */
public class Path implements Iterable<BoardButton>{

	private LinkedList<BoardButton> path;
	private int color;

	public Path() {
		path = new LinkedList<BoardButton>();
	}

	public boolean add(BoardButton b) {
		
		if (path.contains(b))
			return false;

		if (0 < path.size() && !isAdjacent(b, path.getLast()))
			return false;

		path.add(b);
		return true;
	}
	
	public void clear(){
		path.clear();
	}
	
	public int size(){
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

}
