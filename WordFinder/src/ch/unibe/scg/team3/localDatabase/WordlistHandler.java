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
	 * database is closed before you invoke this method and that it will be closed after
	 * execution.
	 * 
	 * @param word
	 *            should not be empty or null
	 * @param wordlistname
	 *            should not be empty or null
	 * @return True, if the word was added successfully and false otherwise.
	 */
	public boolean addWord(String word, String wordlistname) {
		int wordlistId = getWordlistId(wordlistname);
		String table = new String();
		
		if (word.length() < SMALL_WORD && word.length() > 0) {
			table = firstLetterOf(word) + SHORT_WORD_TABLE_SUFFIX;

		} else if (word.length() >= SMALL_WORD) {
			table = firstLetterOf(word) + LONG_WORD_TABLE_SUFFIX;
		}
		
		ContentValues values = new ContentValues();
		values.put(COL_WORDLIST, String.valueOf(wordlistId));
		values.put(COL_CONTENT, word.toLowerCase());
		long id = helper.insert(table, null, values);
		
		return id == -1 ? false : true;
	}

	/**
	 * @param word
	 *            The word should have at least one character.
	 * @return The first letter of this word in lower case.
	 */
	public String firstLetterOf(String word) {
		return word.substring(0, 1).toLowerCase();
	}

	/**
	 * Removes a wordlist given by a name in database. Pay attention that the
	 * database is closed before you invoke and that it will be closed after execution.
	 * 
	 * @param name
	 *            The name of wordlist to be removed from main database
	 */
	public void removeWordlist(String name) {
		String whereClause = COL_NAME + " = ?";
		String[] whereArgs = { name };
		helper.delete(TABLE_WORDLIST, whereClause, whereArgs);
//		helper.execSQL("DELETE FROM Dictionary WHERE Name = '" + name + "'");
	}

	/**
	 * Removes a word from the given wordlist in main database. Pay attention
	 * that database is closed before invoke and it will be closed after
	 * execution.
	 * 
	 * @param word
	 *            word to be removed from a wordlist given by name
	 * @param wordlist
	 *            name of wordlist which contains the word to remove
	 */
	public void removeWordFromWordlist(String word, String wordlist) {
		int wordlistId = getWordlistId(wordlist);
		String table;

		if (word.length() < SMALL_WORD) {
			table = firstLetterOf(word) + SHORT_WORD_TABLE_SUFFIX;
		} else {
			table = firstLetterOf(word) + LONG_WORD_TABLE_SUFFIX;
		}
		helper.execSQL("DELETE FROM " + table + " WHERE Dictionary = '" + wordlistId
				+ "' AND content = '" + word + "'");
	}

	/**
	 * 
	 * @param word
	 * @param wordlistId
	 * @return
	 */
	public boolean isWordInWordlist(String word, int wordlistId) {

		if (word.length() < MINIMUM_WORD_LENGTH)
			return false;

		String table = firstLetterOf(word);

		if (word.length() < SMALL_WORD) {
			table += SHORT_WORD_TABLE_SUFFIX;
		} else {
			table += LONG_WORD_TABLE_SUFFIX;
		}

		String[] contents = { word.toLowerCase() };

		Cursor cursor = helper.rawQuery("SELECT Dictionary, Content FROM '" + table
				+ "' WHERE Dictionary = '" + wordlistId + "' AND Content = ? ", contents);

		if (cursor.getCount() != 0) {
			cursor.close();
			return true;
		} else {
			cursor.close();
			return false;
		}

	}

	// TODO: test, can use in addWordToWordlist
	public boolean isWordlistInDatabase(String wordlistname) {

		String[] content = { wordlistname };

		Cursor cursor = helper.rawQuery("SELECT _id FROM Dictionary WHERE Name = ?", content);

		if (cursor.getCount() != 0) {
			cursor.close();
			return true;
		} else {
			cursor.close();
			return false;
		}

	}

	// TODO: look at code downwards here
	public int getWordlistId(String wordlistname) {
		Cursor c = helper.rawQuery("SELECT _id FROM Dictionary WHERE Name = ?",
				new String[] { wordlistname });
		if (c.getCount() != 0) {
			c.moveToFirst();
			int id = c.getInt(0);
			c.close();
			return id;
		} else {
			c.close();
			return 0;
		}

	}

	public CharSequence[] getWordlists() {
		CharSequence[] lists = null;
		ArrayList<String> tmp = new ArrayList<String>();
		Cursor c = helper.rawQuery("SELECT _id , Name FROM Dictionary", null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				tmp.add(c.getString(1));
				c.moveToNext();
			}

			c.close();
			return (CharSequence[]) tmp.toArray(new CharSequence[tmp.size()]);
		} else {
			c.close();
			return lists;
		}

	}

	public CharSequence[] getWordlistIds() {
		CharSequence[] lists = null;
		ArrayList<String> tmp = new ArrayList<String>();
		Cursor c = helper.rawQuery("SELECT _id , Name FROM Dictionary", null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				tmp.add(Integer.toString(c.getInt(0)));
				c.moveToNext();
			}
			c.close();
			return (CharSequence[]) tmp.toArray(new CharSequence[tmp.size()]);
		} else {
			c.close();
			return lists;
		}

	}

	public String getRandomWordFromWordlist() {
		Random r = new Random();
		int randomint = r.nextInt(26);
		String table = MySQLiteHelper.ALPHABET.substring(randomint, randomint + 1);
		return getRandomWordFromWordlistByLetter(table);

	}

	public String getRandomWordFromWordlistByLetter(String letter) {
		Random r = new Random();
		int random = r.nextInt(2);
		boolean rboolean;
		switch (random) {
		case 0:
			rboolean = true;
			break;
		default:
			rboolean = false;
			break;
		}
		return getRandomWordFromDatabaseByLetterAndLength(letter, rboolean);
	}

	public String getRandomWordFromDatabaseByLetterAndLength(String letter, boolean isShort) {
		String word = "";
		String table = "";
		if (isShort) {
			table = letter + SHORT_WORD_TABLE_SUFFIX;
		} else {
			table = letter + LONG_WORD_TABLE_SUFFIX;
		}
		Cursor c = helper.rawQuery("SELECT Content FROM " + table + " WHERE Dictionary = '"
				+ selectedWordlist + "' ORDER BY RANDOM() LIMIT 1", null);
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			word = c.getString(0);
			c.close();
		} else {
			c.close();
		}
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
		Cursor c = helper.rawQuery("SELECT Content FROM " + table + " WHERE Dictionary = '"
				+ selectedWordlist + "' AND Content LIKE '" + suffix + "%'", null);

		while (c != null && c.moveToNext()) {
			list.add(c.getString(0));
		}
		c.close();
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

}
