package ch.unibe.scg.team3.wordfinder.test;

import ch.unibe.scg.team3.game.Path;
import ch.unibe.scg.team3.token.IToken;
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
	
	public void testRemoveLast(){
		Path<IToken> path = new Path<IToken>();
		
		Token first = new Token('B', 1, 0, 1);
		Token second = new Token('C', 2, 0, 2);
		
		path.add(first);
		path.add(second);
		
		IToken last = path.removeLast();
		assertEquals(last, second);
		
		assertFalse(path.isEmpty());
		
		last = path.removeLast();
		assertEquals(last, first);
		
		assertTrue(path.isEmpty());
	}

}
