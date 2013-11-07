package ch.unibe.scg.team3.localDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
/**
 * This class gives the possibility to manage wordlists in the database.
 * @author nils
 *
 */
public class WordlistHandler extends DataHandler {

	/**
	 * Words with length smaller than SMALL_WORD are small words
	 */
	public static final int SMALL_WORD = 5;
	public static final String SHORT_WORD_TABLE_SUFFIX = "short";
	public static final String LONG_WORD_TABLE_SUFFIX = "long";
	private String selectedWordlist;
	
	
	// TODO: check for injections
	public WordlistHandler(Context context) {
		super(context);
		
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		selectedWordlist = preferences
				.getString("choose_wordlist", null);

	}
	/**
//	 * Adds a new Wordlistentry in Database
	 * @param name
	 * @throws WordlistAlreadyInDataBaseException
	 */
	public void addEmptyWordlist(String name)
			throws WordlistAlreadyInDataBaseException {

		if (!isWordlistInDatabase(name)) {
			SQLiteDatabase db = helper.getWritableDatabase();
			// TODO: use ? to prevent injection
			db.execSQL("INSERT INTO Dictionary VALUES(NULL,'" + name + "')");
			db.close();
		} else {
			throw new WordlistAlreadyInDataBaseException();
		}
	}

	// TODO: check if needed after v1 release
	public void addWordlistByFileInRaw(String name, String filename)
			throws WordlistAlreadyInDataBaseException {

		addEmptyWordlist(name);

		int resID = context.getResources().getIdentifier(filename, "raw",
				context.getPackageName());

		int wordlistId = getWordlistId(name);

		InputStream inputStream = context.getResources().openRawResource(resID);
		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);

		String word;
		SQLiteDatabase db = helper.getWritableDatabase();
		db.beginTransaction();

		try {
			while ((word = buffreader.readLine()) != null) {
				addWordToOpenDb(word, wordlistId, db);
			}
			db.setTransactionSuccessful();
		}

		catch (IOException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}
	/**
	 * Adds a word to the given wordlist in main database. Pay attention that database
	 * is closed before invoke and it will be closed after execution.
	 * @param word
	 * 			should not be empty or null
	 * @param wordlistname
	 * 			should not be empty or null
	 * @return
	 * 			returns boolean value whether adding entry in database was successful
	 */
	public boolean addWordToWordlist(String word, String wordlistname) {

		int wordlistId = getWordlistId(wordlistname);
		SQLiteDatabase db = helper.getWritableDatabase();

		try {
			addWordToOpenDb(word.toLowerCase(), wordlistId, db);
		} catch (SQLException e) {
			db.close();
			return false;
		} 
		db.close();
		return true;
	}
	/**
	 * 			Adds a word to an OPEN! database. Its important to use this method carefully!
	 * 			Database will NOT! be closed after execution!
	 * @param word
	 * 			should not be empty or null
	 * @param wordlistId
	 * 			should not be empty or null
	 * @param db
	 * 			should be a valid dataBase
	 * @throws SQLException
	 */

	private void addWordToOpenDb(String word, int wordlistId, SQLiteDatabase db)
			throws SQLException {
		if (word.length() < SMALL_WORD && word.length() > 0) {

			db.execSQL("INSERT INTO " + getFirstLetterFromInputToLowerCase(word)
					+ SHORT_WORD_TABLE_SUFFIX + " VALUES(NULL, " + wordlistId
					+ ", '" + word.toLowerCase() + "')");

		} else if (word.length() >= SMALL_WORD) {
			db.execSQL("INSERT INTO " + getFirstLetterFromInputToLowerCase(word)
					+ LONG_WORD_TABLE_SUFFIX + " VALUES(NULL, " + wordlistId
					+ ", '" + word.toLowerCase() + "')");

		} else {
			throw new SQLException();
		}
	}

	public String getFirstLetterFromInputToLowerCase(String word) {
		return word.substring(0, 1).toLowerCase();
	}
	/**
	 * Removes a wordlist given by name in main database. Pay attention that database
	 * is closed before invoke and it will be closed after execution.
	 * @param name
	 * 			name of wordlist to be removed from main database
	 */
	public void removeWordlist(String name) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE IF EXISTS FROM Dictionary WHERE Name = '" + name
				+ "'");
		db.close();
	}
	/**
	 * Removes a word from the given wordlist in main database. Pay attention that database
	 * is closed before invoke and it will be closed after execution.
	 * @param word
	 * 			word to be removed from a wordlist given by name
	 * @param wordlist
	 * 			name of wordlist which contains the word to remove
	 */
	public void removeWordFromWordlist(String word, String wordlist) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE IF EXISTS FROM" + getFirstLetterFromInputToLowerCase(word)
				+ "WHERE Name = '" + wordlist + "' AND content = '" + word
				+ "'");
		db.close();
	}
	/**
	 * 
	 * @param word
	 * @param wordlistId
	 * @return
	 */
	public boolean isWordInWordlist(String word, int wordlistId) {

		if (word.length() == 0)
			return false;

		String table = getFirstLetterFromInputToLowerCase(word);

		if (word.length() < SMALL_WORD) {
			table += SHORT_WORD_TABLE_SUFFIX;
		} else {
			table += LONG_WORD_TABLE_SUFFIX;
		}

		SQLiteDatabase db = helper.getReadableDatabase();

		String[] contents = {word.toLowerCase() };

		Cursor cursor = db.rawQuery("SELECT Dictionary, Content FROM " + table
				+ " WHERE Dictionary = " + wordlistId + " AND Content = ? ", contents);

		if (cursor.getCount() != 0) {
			cursor.close();
			db.close();
			return true;
		}
		else{
		db.close();
		return false;
		}

	}

	// TODO: test, can use in addWordToWordlist
	public boolean isWordlistInDatabase(String wordlistname) {

		SQLiteDatabase db = helper.getReadableDatabase();

		String[] content = { wordlistname };

		Cursor cursor = db.rawQuery(
				"SELECT _id FROM Dictionary WHERE Name = ?", content);
		
		if (cursor.getCount() != 0) {
			cursor.close();
			db.close();
			return true;
		}
		db.close();
		return false;

	}

	//TODO: look at code downwards here
	public int getWordlistId(String wordlistname) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT _id FROM Dictionary WHERE Name = ?",
				new String[] { wordlistname });
		if (c.getCount() != 0) {
			c.moveToFirst();
			int id = c.getInt(0);
			c.close();
			db.close();
			return id;
		}
		db.close();
		return 0;

	}

	public CharSequence[] getWordlists() {
		CharSequence[] lists = null;
		ArrayList<String> tmp = new ArrayList<String>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT _id , Name FROM Dictionary", null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				tmp.add(c.getString(1));
				c.moveToNext();
			}

			c.close();
			db.close();
			return (CharSequence[]) tmp.toArray(new CharSequence[tmp.size()]);
		}
		db.close();
		return lists;

	}

	public CharSequence[] getWordlistids() {
		CharSequence[] lists = null;
		ArrayList<String> tmp = new ArrayList<String>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT _id , Name FROM Dictionary", null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				tmp.add(Integer.toString(c.getInt(0)));
				c.moveToNext();
			}
			c.close();
			db.close();
			return (CharSequence[]) tmp.toArray(new CharSequence[tmp.size()]);
		}
		db.close();
		return lists;

	}

	public void copyDB() throws IOException {
		SQLiteDatabase db = helper.getWritableDatabase();
		helper.importDatabase();
		db.close();
	}

	
	public String getRandomWordFromWordlist(){
		String word;
		Random r = new Random();
		int letter = r.nextInt(26);
		String table = MySQLiteHelper.ALPHABET.substring(letter,letter + 1);
		switch(r.nextInt(2)){
			case 0: table = table + SHORT_WORD_TABLE_SUFFIX;
					break;
			case 1: table = table + LONG_WORD_TABLE_SUFFIX;
					break;		
		}
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT Content FROM " + table +" WHERE Dictionary = '" +
				selectedWordlist + "' ORDER BY RANDOM() LIMIT 1", null);
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			word = c.getString(0);
			c.close();
			db.close();
			return word;
		}
		else {
			c.close();
			db.close();
			return "";
		}
		
	}

}
