package ch.unibe.scg.team3.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.token.IToken;

/**
 * The instance of this class represents a selection of a word on a board. The
 * selection contains tokens from the board. The responsibility of this class is
 * to add tokens correctly to form a word and to compute the score of the word.
 * 
 * @see Board
 * @author adrian
 */
public class WordSelection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8992770960252410029L;
	private List<IToken> selection;

	public WordSelection() {
		this.selection = new ArrayList<IToken>();
	}

	/**
	 * @param tok A token not equal to null
	 */
	public void addToken(IToken tok) {
		assert tok != null;
		this.selection.add(tok);
	}

	/**
	 * @return The length of the selection is a positive integer
	 */
	public int length() {
		assert selection.size() >= 0;
		return selection.size();
	}

	/**
	 * @return The sum of the values from all tokens in this selection, which is
	 *         positive.
	 */
	public int getScore() {

		int sum = 0;

		for (IToken tok : this.selection) {
			sum += tok.getValue();
		}

		assert sum >= 0;
		return sum;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (IToken tok : this.selection) {
			sb.append(tok.toString());
		}

		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {

		if (o == null || o.getClass() != this.getClass())
			return false;

		WordSelection other = (WordSelection) o;

		if (!other.toString().equals(this.toString()))
			return false;

		return true;
	}

}
