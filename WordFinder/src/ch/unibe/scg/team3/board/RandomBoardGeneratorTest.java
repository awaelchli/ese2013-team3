package ch.unibe.scg.team3.board;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;

import token.IToken;
import token.NullToken;
import ch.unibe.jexample.JExample;

/**
 * 
 * @author adrian
 * 
 */
@RunWith(JExample.class)
public class RandomBoardGeneratorTest {

	@Test
	public void testBoardWithSize1(){
		RandomBoardGenerator r = new RandomBoardGenerator(1);
		r.generate();
		
		Board board = r.getBoard();
		
		IToken tok = board.getToken(0, 0);
		IToken nul = NullToken.getInstance();
		
		assertFalse(tok.getLetter() == nul.getLetter());
	}
	
}
