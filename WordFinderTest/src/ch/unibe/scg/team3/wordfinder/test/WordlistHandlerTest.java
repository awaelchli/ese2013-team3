package ch.unibe.scg.team3.wordfinder.test;

import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import android.test.AndroidTestCase;

public class WordlistHandlerTest extends AndroidTestCase {

	public void testGetRandomWordFromWordlist(){
		WordlistHandler handler = new WordlistHandler(mContext);
		String string = handler.getRandomWordFromWordlist();
			assertNotNull(string);
			assertFalse(string.equals(""));
		
		
	}

}
