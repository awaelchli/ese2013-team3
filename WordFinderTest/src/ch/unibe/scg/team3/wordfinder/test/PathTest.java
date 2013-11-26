package ch.unibe.scg.team3.wordfinder.test;

import ch.unibe.scg.team3.path.Path;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.Token;
import android.test.AndroidTestCase;

public class PathTest extends AndroidTestCase {

	public void testAddRemoveElement(){
		Path<Token> ch.unibe.scg.team3.path = new Path<Token>();
		assertTrue(ch.unibe.scg.team3.path.isEmpty());
		
		assertTrue(ch.unibe.scg.team3.path.add(new Token('A', 1, 0, 0)));
		assertEquals(ch.unibe.scg.team3.path.length(), 1);
		
		ch.unibe.scg.team3.path.add(new Token('A', 1, 0, 0));
		assertEquals(ch.unibe.scg.team3.path.length(), 1);
		
		assertEquals(ch.unibe.scg.team3.path.removeLast(), new Token('A', 1, 0, 0));
		assertTrue(ch.unibe.scg.team3.path.isEmpty());
	}
	
	public void testRemoveLast(){
		Path<IToken> ch.unibe.scg.team3.path = new Path<IToken>();
		
		Token first = new Token('B', 1, 0, 1);
		Token second = new Token('C', 2, 0, 2);
		
		ch.unibe.scg.team3.path.add(first);
		ch.unibe.scg.team3.path.add(second);
		
		IToken last = ch.unibe.scg.team3.path.removeLast();
		assertEquals(last, second);
		
		assertFalse(ch.unibe.scg.team3.path.isEmpty());
		
		last = ch.unibe.scg.team3.path.removeLast();
		assertEquals(last, first);
		
		assertTrue(ch.unibe.scg.team3.path.isEmpty());
	}
	
	public void testContains(){
		Path<IToken> ch.unibe.scg.team3.path = new Path<IToken>();
		
		Token tok1 = new Token('A', 1, 0, 0);
		Token tok2 = new Token('B', 1, 0, 1);
		Token tok3 = new Token('B', 2, 0, 3);
		
		ch.unibe.scg.team3.path.add(tok1);
		ch.unibe.scg.team3.path.add(tok2);
		ch.unibe.scg.team3.path.add(tok3);
		
		assertTrue(ch.unibe.scg.team3.path.contains(tok1));
		assertTrue(ch.unibe.scg.team3.path.contains(tok2));
		assertFalse(ch.unibe.scg.team3.path.contains(tok3));
		
		ch.unibe.scg.team3.path.removeLast();
		assertFalse(ch.unibe.scg.team3.path.contains(tok2));
		ch.unibe.scg.team3.path.removeLast();
		assertFalse(ch.unibe.scg.team3.path.contains(tok1));
		
		assertTrue(ch.unibe.scg.team3.path.isEmpty());
	}

	
}
