package ch.unibe.scg.team3.wordfinder.test;

import java.io.File;
import java.io.IOException;

import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.wordfinder.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;

public class WordlistHandlerTest extends AndroidTestCase {
	protected  WordlistHandler wordlistHandler;
	protected void setUp(){
		wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		File database = new File("/data/data/ch.unibe.scg.team3.wordfinder/databases/localDatabase.db");
		try {
			wordlistHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testGetRandomWordFromWordlist(){
		
			android.preference.PreferenceManager.setDefaultValues(mContext.getApplicationContext(), R.xml.preferences, false);
			String string = wordlistHandler.getRandomWordFromWordlist();
			assertNotNull(string);
			assertFalse(string.equals(""));
		
		
	        
	        

}}
