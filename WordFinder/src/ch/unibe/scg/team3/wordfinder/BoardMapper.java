package ch.unibe.scg.team3.wordfinder;

import java.util.HashMap;

import ch.unibe.scg.team3.game.Point;

/**
 * 
 * @author faerber
 *
 * Maps fields to corresponding grid coordinates.
 * 
 * @return A mapping from fields to its grid coordinates.
 */
public class BoardMapper {
	
	private HashMap<String, Point> map;
	private int boardSize;
	
	public BoardMapper(int boardSize) {
		
		this.boardSize = boardSize;
		this.createMap();
	}

	private void createMap() {
		this.map = new HashMap<String, Point>();
		for (int i = 1;i < this.boardSize + 1; i++) {
			for (int j = 1; j < this.boardSize + 1; j++) {
				this.map.put("button".concat(Integer.toString((i-1)*6+j)), new Point(j-1,i-1));
			}
		}
	}
	 /**
	  * 
	  * @param tag The name of the field
	  * @return The coordinate point a field
	  */
	public Point getPoint(String tag) {
		
		return this.map.get(tag);
	}
}
