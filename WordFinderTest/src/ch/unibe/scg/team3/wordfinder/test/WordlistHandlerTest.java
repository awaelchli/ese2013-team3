package ch.unibe.scg.team3.wordfinder.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ch.unibe.scg.team3.localDatabase.WordlistAlreadyInDataBaseException;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.wordfinder.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;

public class WordlistHandlerTest extends AndroidTestCase {
	protected WordlistHandler wordlistHandler;

	protected void setUp() {
		android.preference.PreferenceManager.setDefaultValues(
				mContext.getApplicationContext(), R.xml.preferences, false);
		wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		File database = new File(
				"/data/data/ch.unibe.scg.team3.wordfinder/databases/localDatabase.db");
		try {
			wordlistHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testGetRandomWordFromWordlist() {

		String string = wordlistHandler.getRandomWordFromWordlist();
		assertNotNull(string);
		assertFalse(string.equals(""));

	}
	public void testAddEmptyWordlist()
			throws WordlistAlreadyInDataBaseException {
		wordlistHandler.addEmptyWordlist("TestAddEmptyWordlist");
		assertTrue(wordlistHandler.isWordlistInDatabase("TestAddEmptyWordlist"));
		
	}

	public void testAddWordToWordlist() throws WordlistAlreadyInDataBaseException {
		wordlistHandler.addEmptyWordlist("TestAddWordToWordlist");
		wordlistHandler.addWordToWordlist("Teste", "TestAddWordToWordlist");
		assertTrue(wordlistHandler.isWordInWordlist("Teste", wordlistHandler.getWordlistId("TestAddWordToWordlist")));
	}
	
	public void testAddWordToOpenDb()
			throws SQLException {
		
	}

	public void testGetFirstLetterFromInputToLowerCase() {
		
	}

	public void testRemoveWordlist() {
		
	}
	
	public void testRemoveWordFromWordlist() {
		
	}
	
	public void testIsWordInWordlist() throws WordlistAlreadyInDataBaseException {
		wordlistHandler.addEmptyWordlist("TestIsWordInWordlist");
		wordlistHandler.addWordToWordlist("Teste", "TestIsWordInWordlist");
		assertTrue(wordlistHandler.isWordInWordlist("Teste", wordlistHandler.getWordlistId("TestIsWordInWordlist")));
		
	}

	public void testIsWordlistInDatabase() {
		assertTrue(wordlistHandler.isWordlistInDatabase("English"));
		assertTrue(wordlistHandler.isWordlistInDatabase("Deutsch"));
		assertFalse(wordlistHandler.isWordlistInDatabase(""));
		
	}

	public void testGetWordlistId() {
		assertTrue(wordlistHandler.getWordlistId("English") == 1);
		assertTrue(wordlistHandler.getWordlistId("Deutsch") == 2);
		assertTrue(wordlistHandler.getWordlistId("") == 0);

	}

	public void testGetWordlists() {
		assertNotNull(wordlistHandler.getWordlists());
		assertTrue(wordlistHandler.getWordlists().length >= 2);
		
	}

	public void testGetWordlistids() {
		assertNotNull(wordlistHandler.getWordlistids());
		assertTrue(wordlistHandler.getWordlistids().length >= 2);
	}

}
