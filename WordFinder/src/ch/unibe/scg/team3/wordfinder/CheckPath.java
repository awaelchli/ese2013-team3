package ch.unibe.scg.team3.wordfinder;

import java.util.List;

/**
 * 
 * @author faerber
 * 
 * This class validates a user input. It does not check if a marked word is correct
 * but if it is a valid path. A valid path is a number of connected fields.
 * 
 * This function expect a list of coordinates corresponding to the hovered fields.
 * The order of the elements in the list must correspond to the order in which the fields
 * were hovered.
 * 
 * return value: a boolean indicating if the path is valid (true) or not (false).
 *
 */

public class CheckPath {

	public boolean validateSelection(List<int[]> c) {
		boolean isValid = true;
		for (int i=1;i<c.size();i++) {
			if (!isValidSuccessor(c.get(i-1), c.get(i))) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}
	private boolean isValidSuccessor(int[] predecessor, int[] successor) {
		boolean isValidSuccessor = true;
		int pre_row = predecessor[0];
		int pre_col = predecessor[1];
		int suc_row = successor[0];
		int suc_col = successor[1];
		if ((Math.abs(pre_row-suc_row) > 1) || Math.abs(pre_col-suc_col) > 1) {
			isValidSuccessor = false;
		}
		return isValidSuccessor;
	}
}
