package ch.unibe.scg.team3.wordfinder.test;

import android.test.AndroidTestCase;
import ch.unibe.scg.team3.wordlist.Wordlist;


/**
 * @author adrian
 */

public class WordlistTest extends AndroidTestCase{

	private static final char SEP = Wordlist.WORD_SEPARATOR;

	public Wordlist testInitWordlist() {
		Wordlist list = new Wordlist("test");

		assertEquals(list.getSize(), 0);
		assertEquals(list.getContent().size(), 0);
		assertEquals(list.getName(), "test");

		return list;
	}

	public Wordlist testAddWords(Wordlist list) {
		list.addWord(" ");

		assertEquals(list.getSize(), 1);
		assertEquals(list.getContent(), " " + SEP);

		list.addWord("b");
		list.addWord("a");

		assertEquals(list.getSize(), 3);
		assertEquals(list.getContent(), " " + SEP + "a" + SEP + "b" + SEP);

		return list;
	}

	public void testRemoveWords(Wordlist list) {
		list.removeWord(" ");

		assertEquals(list.getContent(), "a" + SEP + "b" + SEP);

		list.removeWord("x");

		assertEquals(list.getSize(), 2);

		list.removeWord("a");
		list.removeWord("b");
		list.removeWord("b");

		assertEquals(list.getSize(), 0);
		assertEquals(list.getContent(), "");
	}
}
