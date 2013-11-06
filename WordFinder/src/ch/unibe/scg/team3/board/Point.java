package ch.unibe.scg.team3.board;

/**
 * This class models just the rudiental properties of a point with integer
 * coordinates. 
 * 
 * @author adrian
 */
public class Point {

	private final int x;
	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	@Override
	public boolean equals(Object o) {

		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;

		Point p = (Point) o;
		return p.getX() == x && p.getY() == y;
	}
}
