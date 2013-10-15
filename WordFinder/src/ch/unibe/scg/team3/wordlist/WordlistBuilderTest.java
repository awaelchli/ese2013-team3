package ch.unibe.scg.team3.wordlist;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class WordlistBuilderTest {

	@Test
	public WordlistBuilder testInitBuilder(){
		WordlistBuilder builder = new WordlistBuilder("testbuild");
		
		Wordlist empty = builder.getWordlist();
		
		assertEquals(empty.getSize(), 0);
		assertEquals(empty.getName(), "testbuild");
		
		return builder;
	}
	
	@Given("#testInitBuilder")
	public void testAddWords(){
		
	}
	
}
