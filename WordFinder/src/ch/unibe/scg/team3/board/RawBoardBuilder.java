package ch.unibe.scg.team3.board;

import ch.unibe.scg.team3.token.AbstractToken;
import ch.unibe.scg.team3.token.Token;

/**
 * This class is responsible for building a board from a string input.
 * 
 * @author adrian
 */
public class RawBoardBuilder extends AbstractBoardGenerator {

	private String raw;

	public RawBoardBuilder(String board) {
		super(board.length());
		raw = board;
	}

	@Override
	protected void generate() {
		double side = Math.sqrt(raw.length());

		if (side % 0 == 0) {
			fillBoard((int) side);
		}
	}

	private void fillBoard(int size) {
		
		int index = 0;
		
		for (int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++){
				
				char letter = raw.charAt(index);
				int value = meter.getValue(letter);
				
				AbstractToken tok = new Token(letter, value, i, j);
				board.setToken(tok);
				
				index++;
			}
		}
	}

}
