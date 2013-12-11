package ch.unibe.scg.team3.localDatabase;

import java.util.ArrayList;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.preference.PreferenceManager;

/**
 * This class enables the access to the wordlists stored in the database. Its
 * responsibility is to mutate and manage them correctly.
 * 
 * @author nils
 * @author adrian
 */
public class WordlistHandler extends DataHandler {

	/*
	 * Words with length smaller than SMALL_WORD are small words
	 */
	public static final int SMALL_WORD = 5;
	/*
	 * Words in the database are categorized into tables for short and long
	 * words.
	 */
	public static final String SHORT_WORD_TABLE_SUFFIX = "short";
	public static final String LONG_WORD_TABLE_SUFFIX = "long";
	private static final int MINIMUM_WORD_LENGTH = 3;

	private static final String TABLE_WORDLIST = "Dictionary";
	private static final String COL_NAME = "Name";
	private static final String COL_CONTENT = "Content";
	private static final String COL_WORDLIST = "Dictionary";

	private final String selectedWordlist;

	public WordlistHandler(Context context) {
		super(context);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		selectedWordlist = preferences.getString("choose_wordlist", null);

	}

	/**
	 * Adds a new wordlist entry in the database
	 * 
	 * @param name
	 *            The name that the wordlist should be called.
	 * @throws WordlistAlreadyInDataBaseException
	 *             if the name is already used by some othe wordlist.
	 */
	public void addEmptyWordlist(String name) throws WordlistAlreadyInDataBaseException {

		if (!isWordlistInDatabase(name)) {

			ContentValues values = new ContentValues();
			values.put(COL_NAME, name);
			helper.insert(TABLE_WORDLIST, null, values);

		} else {
			throw new WordlistAlreadyInDataBaseException();
		}
	}

	/**
	 * Adds a word to the given wordlist in the database. Pay attention that the
	 * database is closed before you invoke this method and that it will be
	 * closed after execution.
	 * 
	 * @param word
	 *            should not be empty or null
	 * @param wordlistname
	 *            should not be empty or null
	 * @return True, if the word was added successfully and false otherwise.
	 */
	public boolean addWord(String word, String wordlistname) {
		int wordlistId = getWordlistId(wordlistname);
		String table = tableOf(word);

		ContentValues values = new ContentValues();
		values.put(COL_WORDLIST, String.valueOf(wordlistId));
		values.put(COL_CONTENT, word.toLowerCase());
		long id = helper.insert(table, null, values);

		return id == -1 ? false : true;
	}

	/**
	 * Removes a wordlist given by a name in database. Pay attention that the
	 * database is closed before you invoke and that it will be closed after
	 * execution.
	 * 
	 * @param name
	 *            The name of wordlist to be removed from main database
	 */
	public void removeWordlist(String name) {
		String whereClause = COL_NAME + " = ?";
		String[] whereArgs = { name };
		helper.delete(TABLE_WORDLIST, whereClause, whereArgs);
	}

	/**
	 * Removes a word from the given wordlist in the database. Pay attention
	 * that database is closed before you invoke it and that it will be closed
	 * after execution.
	 * 
	 * @param word
	 *            The word to be removed from a wordlist given by the name. This
	 *            method ignores upper case words and converts the word to lower
	 *            case.
	 * @param wordlist
	 *            The name of the wordlist which contains the word to remove.
	 */
	public void removeWord(String word, String wordlist) {
		int wordlistId = getWordlistId(wordlist);

		String table = tableOf(word);
		String whereClause = COL_WORDLIST + " = ? AND " + COL_CONTENT + " = ?";
		String[] whereArgs = { String.valueOf(wordlistId), word.toLowerCase() };

		helper.delete(table, whereClause, whereArgs);
	}

	/**
	 * @param word
	 *            The word to search in the given wordlist. Upper case
	 *            characters in this string will get ignored since the database
	 *            stores only lower case words.
	 * @param wordlistId
	 *            The id of the wordlist to search through.
	 * @return True, if the word is in the wordlist with the given id. The word
	 *         will be checked in lower case since the database stores only
	 *         lower case words.
	 */
	public boolean isWordInWordlist(String word, int wordlistId) {

		if (word.length() < MINIMUM_WORD_LENGTH)
			return false;

		String table = tableOf(word);

		String[] columns = { COL_WORDLIST, COL_CONTENT };
		String selection = COL_WORDLIST + " = ? AND " + COL_CONTENT + " = ?";
		String[] selectionArgs = { String.valueOf(wordlistId), word.toLowerCase() };
		Cursor cursor = helper.query(table, columns, selection, selectionArgs, null, null, null);

		if (cursor.getCount() != 0) {
			cursor.close();
			return true;
		} else {
			cursor.close();
			return false;
		}

	}

	/**
	 * @param wordlistname
	 *            The name of the wordlist to search for.
	 * @return True if the wordlist with the specified name is in the database
	 *         and false otherwise.
	 */
	public boolean isWordlistInDatabase(String wordlistname) {

		if (getWordlistId(wordlistname) == -1) {
			return false;
		}

		return true;
	}

	/**
	 * @param wordlistname
	 *            The name of the wordlist to search for.
	 * @return The id of the wordlist if it was found and -1 if the wordlist
	 *         with the given name is not in the database.
	 */
	public int getWordlistId(String wordlistname) {

		String[] columns = { COL_ID };
		String selection = COL_NAME + " = ?";
		String[] selectionArgs = { wordlistname };
		Cursor cursor = helper.query(TABLE_WORDLIST, columns, selection, selectionArgs, null, null,
				null);

		int id = -1;
		if (cursor.moveToFirst()) {
			id = cursor.getInt(0);
		}
		cursor.close();
		return id;
	}

	/**
	 * @return All wordlist names that are stored in the database.
	 */
	public ArrayList<String> getWordlists() {

		ArrayList<String> list = new ArrayList<String>();

		String[] columns = { COL_NAME };
		Cursor cursor = helper.query(TABLE_WORDLIST, columns, null, null, null, null, null);

		while (cursor != null && cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}

		cursor.close();
		return list;
	}

	/**
	 * @return All wordlist ids that are in the database. The ArrayList holds
	 *         Strings for easier conversion to CharSequence[] in the
	 *         PreferencesActivity.
	 * @see PreferencesActivity
	 */
	public ArrayList<String> getWordlistIds() {

		ArrayList<String> list = new ArrayList<String>();

		String[] columns = { COL_ID };
		Cursor cursor = helper.query(TABLE_WORDLIST, columns, null, null, null, null, null);

		while (cursor != null && cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}

		cursor.close();
		return list;
	}

	public String getRandomWordStartingWith(String letter) {
		Random r = new Random();
		int random = r.nextInt(2);
		
		boolean shortWords = random == 0 ? true : false;
			
		return getRandomWordFromDatabaseByLetterAndLength(letter, shortWords);
	}

	public String getRandomWordFromDatabaseByLetterAndLength(String letter, boolean isShort) {
		String word = "";
		String table = "";
		if (isShort) {
			table = letter + SHORT_WORD_TABLE_SUFFIX;
		} else {
			table = letter + LONG_WORD_TABLE_SUFFIX;
		}
		helper.setLocked(true);
		Cursor c = helper.rawQuery("SELECT Content FROM " + table + " WHERE Dictionary = '"
				+ selectedWordlist + "' ORDER BY RANDOM() LIMIT 1", null);
		if (c != null && c.moveToFirst()) {
			word = c.getString(0);
			c.close();
		} else {
			c.close();
		}
		helper.setLocked(false);
		return word;
	}

	public ArrayList<String> getWordsStartingWith(String suffix) {
		ArrayList<String> list = new ArrayList<String>();

		if (suffix.length() == 0) {
			return list;
		}

		String letter = String.valueOf(suffix.charAt(0));
		String table = "";

		if (suffix.length() < SMALL_WORD) {
			table = letter + SHORT_WORD_TABLE_SUFFIX;
		} else {
			table = letter + LONG_WORD_TABLE_SUFFIX;
		}
		helper.setLocked(true);
		Cursor c = helper.rawQuery("SELECT Content FROM " + table + " WHERE Dictionary = '"
				+ selectedWordlist + "' AND Content LIKE '" + suffix + "%'", null);

		while (c != null && c.moveToNext()) {
			list.add(c.getString(0));
		}
		c.close();
		helper.setLocked(false);
		return list;
	}

	public String getWordlistNameById(int wordlistId) {
		String name = "";
		Cursor c = helper.rawQuery("SELECT Name FROM Dictionary WHERE _id ='" + wordlistId + "'",
				null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			name = c.getString(0);
			c.close();
			return name;
		} else {
			c.close();
			return name;
		}
	}

	/**
	 * Returns the name of the table according to the first letter and the
	 * length of the word.
	 */
	private String tableOf(String word) {
		String table = new String();

		if (word.length() < SMALL_WORD && word.length() > 0) {
			table = firstLetterOf(word) + SHORT_WORD_TABLE_SUFFIX;

		} else if (word.length() >= SMALL_WORD) {
			table = firstLetterOf(word) + LONG_WORD_TABLE_SUFFIX;
		}
		return table;
	}

	/**
	 * @param word
	 *            The word should have at least one character.
	 * @return The first letter of this word in lower case.
	 */
	public String firstLetterOf(String word) {
		return word.substring(0, 1).toLowerCase();
	}
	
	public String getRandomWordFromWordlist() {
		Random r = new Random();
		int randomint = r.nextInt(26);
		String table = MySQLiteHelper.ALPHABET.substring(randomint, randomint + 1);
		return getRandomWordStartingWith(table);

	}

}
