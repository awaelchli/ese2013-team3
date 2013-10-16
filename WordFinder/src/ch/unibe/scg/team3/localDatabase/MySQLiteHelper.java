package ch.unibe.scg.team3.localDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ch.unibe.scg.team3.wordlist.Wordlist;
import ch.unibe.scg.team3.wordlist.WordlistBuilder;

/**
 * 
 * @author nils
 * @author adrian
 * 
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

	// private static final MySQLiteHelper instance = new MySQLiteHelper();

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// public static MySQLiteHelper getInstance(){
	// return instance;
	// }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create Wordlists Table
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for(int i = 0;i<25;i++){
			String shorttable = "CREATE TABLE " + alphabet.charAt(i) + 
									"short(_id INTEGER PRIMARY KEY, Dictionary NUMERIC, " +
									"Content TEXT";
			db.execSQL(shorttable);
			String longtable = "CREATE TABLE " + alphabet.charAt(i) + 
									"long(_id INTEGER PRIMARY KEY, Dictionary NUMERIC, " +
									"Content TEXT";
			db.execSQL(longtable);
			String dictable ="CREATE TABLE Dictionary (_id INTEGER PRIMARY KEY, Name TEXT)";
			db.execSQL(dictable);
			initDB();
		}
	}

	private void initDB() {
//		File file1 = new File("res/raw/english.txt");
//		WordlistBuilder builder1 = new WordlistBuilder("English");
//		builder1.addWords(file1);
//		Wordlist english = builder1.getWordlist();
//		addWordlist(english);
//		
//		File file2 = new File("res/raw/german.txt");
//		WordlistBuilder builder2 = new WordlistBuilder("German");
//		builder2.addWords(file2);
//		Wordlist german = builder2.getWordlist();
//		addWordlist(german);
	}

	public void reset(SQLiteDatabase db) {
		close();
		File file = new File(db.getPath());
		file.deleteOnExit();
	}

	public void addWordlist(Wordlist wordlist) {

		ContentValues values = new ContentValues();
		values.put("Name", wordlist.getName());
		
		SQLiteDatabase db = this.getWritableDatabase();

		db.insert("Dictionary", null, values);
		db.close();
	}

	
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private static final String DATABASE_NAME = "localDatabase.db";
	private static final int DATABASE_VERSION = 1;

	

}
