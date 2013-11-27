package ch.unibe.scg.team3.wordfinder.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import com.parse.Parse;

import android.test.AndroidTestCase;

import ch.unibe.scg.team3.localDatabase.MySQLiteHelper;
import ch.unibe.scg.team3.localDatabase.SavedGamesHandler;
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

public class WordlistHandlerTest extends AndroidTestCase implements IDataHandlerTest {
	
	protected WordlistHandler wordlistHandler;
	
	public void testGetRandomWordFromWordlist() {

		String string = wordlistHandler.getRandomWordFromWordlist();
		assertNotNull(string);
		assertFalse(string.equals(""));

	}

	public void testAddEmptyWordlist() throws WordlistAlreadyInDataBaseException {
		wordlistHandler.addEmptyWordlist("TestAddEmptyWordlist");
		assertTrue(wordlistHandler.isWordlistInDatabase("TestAddEmptyWordlist"));
		// wordlistHandler.addEmptyWordlist("TestAddEmptyWordlist");

	}

	public void testAddWordToWordlist() throws WordlistAlreadyInDataBaseException {
		wordlistHandler.addEmptyWordlist("TestAddWordToWordlist");
		wordlistHandler.addWordToWordlist("teste", "TestAddWordToWordlist");
		assertTrue(wordlistHandler.isWordInWordlist("teste",wordlistHandler.getWordlistId("TestAddWordToWordlist")));
	}

	public void testGetFirstLetterFromInputToLowerCase() {
		assertTrue(wordlistHandler.getFirstLetterFromInputToLowerCase("Test").equals("t"));

	}

	public void testRemoveWordlist() throws WordlistAlreadyInDataBaseException {
		wordlistHandler.addEmptyWordlist("TestRemoveWordlist");
		wordlistHandler.removeWordlist("TestRemoveWordlist");
		assertFalse(wordlistHandler.isWordlistInDatabase("TestRemoveWordlist"));
		assertFalse(wordlistHandler.isWordlistInDatabase(""));
	}

	public void testRemoveWordFromWordlist()
			throws WordlistAlreadyInDataBaseException {
		wordlistHandler.addEmptyWordlist("TestRemoveWordFromWordlist");
		wordlistHandler
				.addWordToWordlist("Teste", "TestRemoveWordFromWordlist");
		wordlistHandler.removeWordFromWordlist("teste",
				"TestRemoveWordFromWordlist");
		assertFalse((wordlistHandler.isWordInWordlist("Teste",
				wordlistHandler.getWordlistId("TestRemoveWordFromWordlist"))));
	}

	public void testIsWordInWordlist()
			throws WordlistAlreadyInDataBaseException {
		wordlistHandler.addEmptyWordlist("TestIsWordInWordlist");
		wordlistHandler.addWordToWordlist("Teste", "TestIsWordInWordlist");
		assertTrue(wordlistHandler.isWordInWordlist("Teste",
				wordlistHandler.getWordlistId("TestIsWordInWordlist")));

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
		assertNotNull(wordlistHandler.getWordlistIds());
		assertTrue(wordlistHandler.getWordlistIds().length >= 2);
	}

	public void testGetRandomWordFromWordlistByLetter() {
		String a = MySQLiteHelper.ALPHABET;
		wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		for (int i = 0; i < a.length(); i++) {
			String temp = wordlistHandler.getRandomWordFromWordlistByLetter(a
					.substring(i, i + 1));
			String temp2 = wordlistHandler
					.getFirstLetterFromInputToLowerCase(temp);
			assertTrue(a.substring(i, i + 1).equals(temp2));
		}
	}
	public void setUp() {
		 wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		android.preference.PreferenceManager.setDefaultValues(
				mContext.getApplicationContext(), R.xml.preferences, false);
		Parse.initialize(mContext, "ORYli0X0QqbH3Oefe0wvI2TsYa4d4Kw7sYKZFYuK",
				"FYUWqwq1E0VlFkVUXs6Fus1OZUN6CfqJo81EPbTJ");
		try {
			wordlistHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
