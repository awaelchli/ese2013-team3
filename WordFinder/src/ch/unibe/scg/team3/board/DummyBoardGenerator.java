package ch.unibe.scg.team3.board;

import java.util.Random;

import ch.unibe.scg.team3.token.NullToken;
import ch.unibe.scg.team3.token.Token;

/**
 * 
 * @author viviane
 * 
 *         creates a board with the words 'apple', 'gold', and 'and' in it.
 * 
 * 
 * 
 */

public class DummyBoardGenerator extends AbstractBoardGenerator {

	private char[] apple = { 'A', 'P', 'P', 'P', 'E' };
	private char[] gold = { 'G', 'O', 'L', 'D' };
	private char[] and = { 'A', 'N', 'D' };

	public DummyBoardGenerator(int size) {
		super(size);
	}

	@Override
	public void generate() {

		// put the words apple, gold, and and into the board

		for (int i = 0; i < apple.length; i++) {
			this.board.setToken(new Token(apple[i], 1), 3, i + 1);
		}

		for (int i = 0; i < gold.length; i++) {
			this.board.setToken(new Token(gold[i], 1), i + 1, 4);
		}

		for (int i = 0; i < and.length; i++) {
			this.board.setToken(new Token(and[i], 1), i + 3, i + 1);
		}

		// fill the rest with random letters

		Random r = new Random();

		for (int i = 0; i < this.board.getSize(); i++) {
			for (int j = 0; j < this.board.getSize(); j++) {

				if (board.getToken(i, j).getLetter() == NullToken.getInstance()
						.getLetter()) {
					char letter = (char) (r.nextInt(26) + 'A');
					Token tok = new Token(letter, 1);
					this.board.setToken(tok, i, j);
				}

			}

		}

	}

}
