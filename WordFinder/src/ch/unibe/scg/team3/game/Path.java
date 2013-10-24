package ch.unibe.scg.team3.game;

import java.util.LinkedList;

import ch.unibe.scg.team3.board.Point;
import ch.unibe.scg.team3.wordfinder.R;

public class Path {

	private LinkedList<Point> path;
	private int state;

	public Path() {
		path = new LinkedList<Point>();
	}

	public boolean add(Point p) {

		if (path.contains(p))
			return false;

		if (!isAdjacent(p, path.getLast()))
			return false;

		path.add(p);
		return true;
	}
	
	public void clear(){
		path.clear();
	}

	private static boolean isAdjacent(Point p1, Point p2) {

		int p1_x = p1.getX();
		int p1_y = p1.getY();
		int p2_x = p2.getX();
		int p2_y = p2.getY();
		
		if ((Math.abs(p1_x - p2_x) > 1) || Math.abs(p1_y - p2_y) > 1) {
			return false;
		}
		return true;
	}

}
