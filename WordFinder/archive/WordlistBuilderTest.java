package ch.unibe.scg.team3.wordfinder.test;

import android.test.AndroidTestCase;


/**
 * @author adrian
 */
public class WordlistBuilderTest extends AndroidTestCase{
	
	private static final char SEP = Wordlist.WORD_SEPARATOR;

	public WordlistBuilder testInitBuilder(){
		WordlistBuilder builder = new WordlistBuilder("testbuild");
		
		Wordlist empty = builder.getWordlist();
		
		assertEquals(empty.getSize(), 0);
		assertEquals(empty.getName(), "testbuild");
		
		return builder;
	}
	
	public void testAddWords(WordlistBuilder builder){
		builder.addWords("");
		
		Wordlist list = builder.getWordlist();
		assertEquals(list.getSize(), 0);
		
		String words = "car" + SEP + "dog" + SEP + "house";
		builder.addWords(words);
		list = builder.getWordlist();
		
		assertEquals(list.getSize(), 3);
		assertEquals(list.getContent(), words + SEP);
	}
}
