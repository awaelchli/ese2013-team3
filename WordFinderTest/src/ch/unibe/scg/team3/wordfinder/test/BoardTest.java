package ch.unibe.scg.team3.wordfinder.test;

import android.test.AndroidTestCase;

import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.path.Path;
import ch.unibe.scg.team3.token.AbstractToken;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.Token;

/**
 * @author adrian
 */
public class BoardTest extends AndroidTestCase {

	public void testBoardWithSize1() {
		Board board = new Board(1);
		assertEquals(board.toString(), " ");
		assertEquals(board.getSize(), 1);
	}

	public void testSetTokenGetTokenAtCorners() {
		Board board = new Board(7);

		assertEquals(board.toString().length(), 49);

		assertEquals(board.getToken(0, 0).toString(), " ");
		assertEquals(board.getToken(0, 6).toString(), " ");
		assertEquals(board.getToken(6, 0).toString(), " ");
		assertEquals(board.getToken(6, 6).toString(), " ");

		AbstractToken tok1 = new Token('1', 1, 0, 0);
		AbstractToken tok2 = new Token('2', 2, 0, 6);
		AbstractToken tok3 = new Token('3', 3, 6, 0);
		AbstractToken tok4 = new Token('4', 4, 6, 6);
		
		board.setToken(tok1);
		board.setToken(tok2);
		board.setToken(tok3);
		board.setToken(tok4);

		assertEquals(board.getToken(0, 0).toString(), "1");
		assertEquals(board.getToken(0, 6).toString(), "2");
		assertEquals(board.getToken(6, 0).toString(), "3");
		assertEquals(board.getToken(6, 6).toString(), "4");
	}
	
	public void testSetPath(){
		
		Board board = new Board(4);
		
		AbstractToken tok1 = new Token('1', 1, 0, 0);
		AbstractToken tok2 = new Token('2', 1, 1, 0);
		AbstractToken tok3 = new Token('3', 1, 0, 1);
		AbstractToken tok4 = new Token('4', 1, 2, 0);
		AbstractToken tok5 = new Token('5', 1, 3, 0);
		AbstractToken badTok = new Token('b', 1, 4, 0);
		
		Path<IToken> ch.unibe.scg.team3.path = new Path<IToken>();
		
		
		ch.unibe.scg.team3.path.add(tok1);
		ch.unibe.scg.team3.path.add(tok3);
		ch.unibe.scg.team3.path.add(tok2);
		ch.unibe.scg.team3.path.add(tok4);
		
		board.setPath(ch.unibe.scg.team3.path);
		
		assertEquals(board.getToken(0, 0), tok1);
		assertEquals(board.getToken(1, 0), tok2);
		assertEquals(board.getToken(0, 1), tok3);
		assertEquals(board.getToken(2, 0), tok4);
		
		Path<IToken> badPath = new Path<IToken>();
		
		badPath.add(tok5);
		badPath.add(badTok);
		
		assertFalse(board.setPath(badPath));
		assertTrue(board.getToken(3, 0).isEmpty());
	}
}
