package ch.unibe.scg.team3.wordfinder.test;

import ch.unibe.scg.team3.game.Path;
import ch.unibe.scg.team3.token.Token;
import android.test.AndroidTestCase;

public class PathTest extends AndroidTestCase {

	public void testAddRemoveElement(){
		Path<Token> path = new Path<Token>();
		assertTrue(path.isEmpty());
		
		assertTrue(path.add(new Token('A', 1, 0, 0)));
		assertEquals(path.length(), 1);
		
		path.add(new Token('A', 1, 0, 0));
		assertEquals(path.length(), 1);
		
		assertEquals(path.removeLast(), new Token('A', 1, 0, 0));
		assertTrue(path.isEmpty());
	}

}
