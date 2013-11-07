package ch.unibe.scg.team3.wordfinder.test;

import android.test.AndroidTestCase;
import ch.unibe.scg.team3.board.Point;
import ch.unibe.scg.team3.token.*;;


/**
 * @author adrian
 */
public class TokenTest extends AndroidTestCase{
	

	public void testInitToken() {
		
		IToken tok1 = new Token(' ', 0, 0, 0);
		assertEquals(tok1.toString(), " ");
		assertEquals(tok1.getValue(), 0);

		IToken tok2 = new Token('x', -2, 0, 0);
		assertEquals(tok2.toString(), "x");
		assertEquals(tok2.getValue(), -2);
	}

	public void testNullToken() {
		IToken ntok1 = new NullToken(0, 1);
		IToken ntok2 = new NullToken(1, 0);

		assertFalse(ntok1.equals(ntok2));
		assertTrue(ntok1.getLetter() == NullToken.NULL_CHAR);
		assertTrue(ntok2.getValue() == 0);
	}
	
	public void testTokenEquals(){
		IToken tok1 = new Token('A', 2, 0, 0);
		IToken tok2 = new Token('A', 1, 0, 0);
		IToken tok3 = new Token('B', 2, 0, 0);
		IToken tok4 = new NullToken(0, 0);
		IToken tok5 = new Token(' ', 0, 0, 0);
		
		assertEquals(tok1, tok1);
		assertFalse(tok1.equals(tok2));
		assertFalse(tok1.equals(tok3));
		assertFalse(tok1.equals(tok4));
		assertFalse(tok4.equals(tok5));
	}
	
	public void testTokenWithCoordinates(){
		
		IToken tok = new Token('X', 3, 0, 0);
		assertEquals(tok.getCoordinates(), new Point(0, 0));

		tok.setCoordinates(new Point(1, 0));
		assertEquals(tok.getCoordinates(), new Point(1, 0));
		
		IToken ntok = new NullToken(1, 0);
		assertNotNull(ntok.getCoordinates());
		assertEquals(ntok.getCoordinates(), new Point(1, 0));
		
		ntok.setCoordinates(new Point(0, 1));
		assertNotNull(ntok.getCoordinates());
	}
}
