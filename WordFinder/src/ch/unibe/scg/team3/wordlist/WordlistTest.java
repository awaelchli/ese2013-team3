//package ch.unibe.scg.team3.wordlist;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import ch.unibe.jexample.Given;
//import ch.unibe.jexample.JExample;
//
///**
// * 
// * @author adrian
// *
// */
//@RunWith(JExample.class)
//public class WordlistTest {
//
//	private static final char SEP = Wordlist.WORD_SEPARATOR;
//
//	@Test
//	public Wordlist testInitWordlist() {
//		Wordlist list = new Wordlist("test");
//
//		assertEquals(list.getSize(), 0);
//		assertEquals(list.getContent().size(), 0);
//		assertEquals(list.getName(), "test");
//
//		return list;
//	}
//
//	@Given("#testInitWordlist")
//	public Wordlist testAddWords(Wordlist list) {
//		list.addWord(" ");
//
//		assertEquals(list.getSize(), 1);
//		assertEquals(list.getContent(), " " + SEP);
//
//		list.addWord("b");
//		list.addWord("a");
//
//		assertEquals(list.getSize(), 3);
//		assertEquals(list.getContent(), " " + SEP + "a" + SEP + "b" + SEP);
//
//		return list;
//	}
//
//	@Given("#testAddWords")
//	public void testRemoveWords(Wordlist list) {
//		list.removeWord(" ");
//
//		assertEquals(list.getContent(), "a" + SEP + "b" + SEP);
//
//		list.removeWord("x");
//
//		assertEquals(list.getSize(), 2);
//
//		list.removeWord("a");
//		list.removeWord("b");
//		list.removeWord("b");
//
//		assertEquals(list.getSize(), 0);
//		assertEquals(list.getContent(), "");
//	}
//}
