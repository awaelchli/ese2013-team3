package ch.unibe.scg.team3.wordfinder.test;

import android.test.AndroidTestCase;
import ch.unibe.scg.team3.board.Point;
import ch.unibe.scg.team3.token.*;;


/**
 * @author adrian
 */
public class TokenTest extends AndroidTestCase{
	

	public void testInitToken() {
		
		IToken tok1 = new Token(' ', 0);
		assertEquals(tok1.toString(), " ");
		assertEquals(tok1.getValue(), 0);

		IToken tok2 = new Token('x', -2);
		assertEquals(tok2.toString(), "x");
		assertEquals(tok2.getValue(), -2);
	}

	public void testNullToken() {
		IToken ntok1 = NullToken.getInstance();
		IToken ntok2 = NullToken.getInstance();

		assertFalse(ntok1 == null);
		assertEquals(ntok1, ntok2);
		assertTrue(ntok1.getLetter() == ' ');
		assertTrue(ntok2.getValue() == 0);
	}
	
	public void testTokenEquals(){
		IToken tok1 = new Token('A', 2);
		IToken tok2 = new Token('A', 1);
		IToken tok3 = new Token('B', 2);
		IToken tok4 = NullToken.getInstance();
		IToken tok5 = new Token(' ', 0);
		
		assertTrue(tok1.equals(tok1));
		assertFalse(tok1.equals(tok2));
		assertFalse(tok1.equals(tok3));
		assertFalse(tok1.equals(tok4));
		assertFalse(tok4.equals(tok5));
	}
	
	public void testTokenWithCoordinates(){
		
		IToken tok = new Token('X', 3, new Point(0, 0));
		assertEquals(tok.getCoordinates(), new Point(0, 0));

		tok.setCoordinates(new Point(1, 0));
		assertEquals(tok.getCoordinates(), new Point(1, 0));
		
		IToken ntok = NullToken.getInstance();
		assertNull(ntok.getCoordinates());
		
		ntok.setCoordinates(new Point(1, 0));
		assertNull(ntok.getCoordinates());
	}
}
