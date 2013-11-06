package ch.unibe.scg.team3.board;

import java.util.Random;

import ch.unibe.scg.team3.token.Token;

/**
 * This board generator creates a board containing tokens with random letters and values based on 
 * DefaultLetterMeter
 * 
 * @author viviane
 * @author adrian
 */
public class RandomBoardGenerator extends AbstractBoardGenerator {

	public RandomBoardGenerator(int size) {
		super(size);
	}

	@Override
	protected void generate() {

		Random r = new Random();

		for (int i = 0; i < this.board.getSize(); i++) {
			for (int j = 0; j < this.board.getSize(); j++) {

				char letter = (char) (r.nextInt(26) + 'a');
				int value = valueOf(letter);
				Token tok = new Token(letter, value);
				this.board.setToken(tok, i, j);
			}

		}
	}

}
