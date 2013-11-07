package ch.unibe.scg.team3.wordfinder.test;

import ch.unibe.scg.team3.board.WordSelection;
import ch.unibe.scg.team3.token.NullToken;
import ch.unibe.scg.team3.token.Token;
import android.test.AndroidTestCase;

public class WordSelectionTest extends AndroidTestCase {

	public void testInit(){
		WordSelection select = new WordSelection();
		assertEquals(select.length(), 0);
		assertEquals(select.getScore(), 0);
		assertEquals(select.toString(), "");
	}
	
	public void testAddGoodTokens(){
		WordSelection select = new WordSelection();
		NullToken tok1 = new NullToken(0, 0);
		Token tok2 = new Token('H', 2, 1, 0);
		Token tok3 = new Token('E', 3, 3, 0);
		Token tok4 = new Token('L', 4, 3, 0);
		Token tok5 = new Token('L', 5, 1, 0);
		Token tok6 = new Token('O', 6, 1, 0);
		
		select.addToken(tok1);
		
		assertEquals(select.length(), 1);
		assertEquals(select.toString(), " ");
		
		select.addToken(tok2);
		select.addToken(tok3);
		select.addToken(tok4);
		select.addToken(tok5);
		select.addToken(tok6);
		
		assertEquals(select.length(), 6);
	}

}
