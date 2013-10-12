package ch.unibe.scg.team3.board;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class TokenTest {

	@Test
	public void testInitToken() {
		Token tok1 = new Token(' ', 0);
		assertEquals(tok1.toString(), " ");
		assertEquals(tok1.getValue(), 0);

		Token tok2 = new Token('x', -2);
		assertEquals(tok2.toString(), "x");
		assertEquals(tok2.getValue(), -2);
	}

	@Test
	public void testNullToken() {
		IToken ntok1 = NullToken.getInstance();
		IToken ntok2 = NullToken.getInstance();

		assertFalse(ntok1 == null);
		assertEquals(ntok1, ntok2);
	}
}
