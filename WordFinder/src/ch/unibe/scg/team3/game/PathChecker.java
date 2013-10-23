package ch.unibe.scg.team3.game;

import java.util.List;

import ch.unibe.scg.team3.board.Point;

//TODO: work on class description here:

/**
 * 
 * @author faerber
 * @author adrian
 * 
 *         This class validates a user input. It does not check if a marked word
 *         is correct but if it is a valid path. A valid path is a number of
 *         connected fields.
 * 
 *         This function expect a list of coordinates corresponding to the
 *         hovered fields. The order of the elements in the list must correspond
 *         to the order in which the fields were hovered.
 * 
 *         return value: a boolean indicating if the path is valid (true) or not
 *         (false).
 * 
 */
public class PathChecker {

	public static boolean check(List<Point> coordinates) {
		for (int i = 1; i < coordinates.size(); i++) {
			if (!isValidSuccessor(coordinates.get(i - 1), coordinates.get(i))) {
				return false;
			}
		}
		return true;
	}

	private static boolean isValidSuccessor(Point predecessor, Point successor) {

		int pre_row = predecessor.getY();
		int pre_col = predecessor.getX();
		int suc_row = successor.getY();
		int suc_col = successor.getX();
		if ((Math.abs(pre_row - suc_row) > 1)
				|| Math.abs(pre_col - suc_col) > 1) {
			return false;
		}
		return true;
	}
}
