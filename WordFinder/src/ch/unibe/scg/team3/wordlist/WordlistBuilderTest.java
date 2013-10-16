package ch.unibe.scg.team3.wordlist;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;

/**
 * 
 * @author adrian
 *
 */
@RunWith(JExample.class)
public class WordlistBuilderTest {
	
	private static final char SEP = Wordlist.WORD_SEPARATOR;

	@Test
	public WordlistBuilder testInitBuilder(){
		WordlistBuilder builder = new WordlistBuilder("testbuild");
		
		Wordlist empty = builder.getWordlist();
		
		assertEquals(empty.getSize(), 0);
		assertEquals(empty.getName(), "testbuild");
		
		return builder;
	}
	
	@Given("#testInitBuilder")
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
	
	@Test
	public void testBuildEnglishWordlist() throws FileNotFoundException{
		File file = new File("res/raw/english.txt");
		
		WordlistBuilder builder = new WordlistBuilder("English");
		
		builder.addWords(file);
	}
	
}
