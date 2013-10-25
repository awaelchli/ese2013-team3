package ch.unibe.scg.team3.board;

import java.util.Random;

import ch.unibe.scg.team3.token.Token;

/**
 * This board generator creates a board containing tokens with random letters and values 1
 * 
 * @author viviane
 * @author adrian
 */
public class RandomBoardGenerator extends AbstractBoardGenerator {

	public RandomBoardGenerator(int size) {
		super(size);
	}

	@Override
	public void generate() {

		Random r = new Random();

		for (int i = 0; i < this.board.getSize(); i++) {
			for (int j = 0; j < this.board.getSize(); j++) {

				char letter = (char) (r.nextInt(26) + 'a');
				Token tok = new Token(letter, 1);
				this.board.setToken(tok, i, j);
			}

		}
	}

}
