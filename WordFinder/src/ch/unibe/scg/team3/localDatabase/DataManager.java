package ch.unibe.scg.team3.localDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ch.unibe.scg.team3.wordfinder.R;
import ch.unibe.scg.team3.wordlist.Wordlist;
import ch.unibe.scg.team3.wordlist.WordlistBuilder;

/**
 * This class provides the connection between the SQLite database and the
 * classes which need the data. This class provides the methods for adding a
 * wordlist to the database or getting an wordlist object by name from the
 * database. Additional the methods for reset and initialisation and update of the db are
 * in this class.
 * 
 * @author nils
 * 
 */
public class DataManager {

	private Context context;
	private MySQLiteHelper helper;

	// private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	public DataManager(Context context) {
		this.context = context;
		this.helper = new MySQLiteHelper(context);
	}

	public void addWordlist(Wordlist wordlist) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("INSERT INTO Dictionary VALUES(NULL,'" + wordlist.getName()
				+ "')");

		String content = wordlist.getContent();
		String[] words = content.split("" + Wordlist.WORD_SEPARATOR);
		db.beginTransaction();
		try {
			for (int i = 0; i < words.length; i++) {
				if (words[i].length() < 5 && words[i].length() > 0) {
					// System.out.println(words[i]);
					db.execSQL("INSERT INTO "
							+ words[i].substring(0, 1).toLowerCase()
							+ "short VALUES(NULL, '" + wordlist.getName()
							+ "', '" + words[i] + "')");

				} else if (words[i].length() > 5) {
					// System.out.println(words[i]);
					db.execSQL("INSERT INTO "
							+ words[i].substring(0, 1).toLowerCase()
							+ "long VALUES(NULL, '" + wordlist.getName()
							+ "', '" + words[i] + "')");

				}
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		db.close();
	}

	public void setWordToWordlist(String word, String wordlist) {
		SQLiteDatabase db = helper.getWritableDatabase();

		if (word.length() < 5 && word.length() > 0) {
			// System.out.println(words[i]);
			db.execSQL("INSERT INTO " + word.substring(0, 1).toLowerCase()
					+ "short VALUES(NULL, '" + wordlist + "', '" + word + "')");

		} else if (word.length() > 5) {
			// System.out.println(words[i]);
			db.execSQL("INSERT INTO " + word.substring(0, 1).toLowerCase()
					+ "long VALUES(NULL, '" + wordlist + "', '" + word + "')");

		}
		db.close();
	}

	public Wordlist getwordlist(String name) {
		StringBuilder wordlist = new StringBuilder();
		SQLiteDatabase db = helper.getReadableDatabase();
		for (int i = 0; i < MySQLiteHelper.ALPHABET.length(); i++) {

			Cursor c = db.rawQuery("SELECT content FROM "
					+ MySQLiteHelper.ALPHABET.charAt(i)
					+ "WHERE Dictionary = ?", new String[] { name });
			if (c != null)
				c.moveToFirst();
			while (!c.isAfterLast()) {
				wordlist.append(c.getString(c.getColumnIndex("content")));
				wordlist.append("" + Wordlist.WORD_SEPARATOR);
				c.moveToNext();
			}
			c.close();
		}
		db.close();
		WordlistBuilder w = new WordlistBuilder(name);
		w.addWords(wordlist.toString());
		return w.getWordlist();
	}

	public void removeWordlist(String name) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE IF EXISTS FROM Dictionary WHERE Name = '" + name
				+ "'");
		db.close();
	}

	public void removeWordFromWordlist(String word, String wordlist) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE IF EXISTS FROM" + word.substring(0, 1).toLowerCase()
				+ "WHERE Name = '" + wordlist + "' AND content = '" + word
				+ "'");
		db.close();
	}

	public boolean isWordInWordlist(String word, String wordlist) {
		
		if(word.length() == 0) return false;
		String table = word.substring(0, 1).toLowerCase();
		if(word.length() < 5){
			table += "short";
		}else{
			table += "long";
		}
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT content FROM "
				+ table
				+ " WHERE Dictionary = ? AND content = ?", new String[] {
				wordlist,  word.toLowerCase() });
		if (c.getCount() != 0) {
			c.close();
			db.close();
			return true;
		}
		db.close();
		return false;

	}

	private void initDB(Context context) {
		WordlistBuilder builer = new WordlistBuilder("English");
		
		InputStream inputStream = context.getResources().openRawResource(R.raw.english);
		InputStreamReader inputreader = new InputStreamReader(inputStream);
	    BufferedReader buffreader = new BufferedReader(inputreader);
	    
		builer.addWords(buffreader);
		Wordlist english = builer.getWordlist();
		addWordlist(english);
	}

	public void reset(Context context) {
		SQLiteDatabase db = helper.getWritableDatabase();
		for (int i = 0; i < MySQLiteHelper.ALPHABET.length(); i++) {
			db.execSQL("DROP TABLE IF EXISTS "
					+ MySQLiteHelper.ALPHABET.substring(i, i + 1) + "short");
			db.execSQL("DROP TABLE IF EXISTS "
					+ MySQLiteHelper.ALPHABET.substring(i, i + 1) + "long");

		}
		db.execSQL("DROP TABLE IF EXISTS Dictionary");
		helper.onCreate(db);
		initDB(context);
		db.close();
		try {
			helper.importDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
