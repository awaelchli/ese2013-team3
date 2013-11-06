package ch.unibe.scg.team3.board;

import java.util.Comparator;

/**
 * @author viviane
 */

public class StringLengthComparator implements Comparator<String> {


	@Override
	public int compare(String lhs, String rhs) {
		 if (lhs.length() > rhs.length()) {
	         return -1;
	      } else if (lhs.length() < rhs.length()) {
	         return 1;
	      }
	      return lhs.compareTo(rhs);
	}

}
