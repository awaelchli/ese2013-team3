package ch.unibe.scg.team3.wordfinder.test;

import android.test.AndroidTestCase;

import ch.unibe.scg.team3.board.*;
import ch.unibe.scg.team3.token.*;

/**
 * @author adrian
 */
public class RandomBoardGeneratorTest extends AndroidTestCase {

	public void testBoardWithSize1() {
		RandomBoardGenerator r = new RandomBoardGenerator(1);

		Board board = r.getBoard();
		IToken tok = board.getToken(0, 0);

		assertFalse(tok.isEmpty());
	}

	public void testBoardNoNullToken() {
		RandomBoardGenerator r = new RandomBoardGenerator(100);

		Board board = r.getBoard();

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();

		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {

				IToken tok = board.getToken(i, j);
				assertFalse(tok.isEmpty());
				assertTrue(alphabet.contains(tok.toString()));
			}
		}
	}

}
