package ch.unibe.scg.team3.localDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.wordlist.*;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author nils
 * @author adrian
 * 
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
	
//	private static final MySQLiteHelper instance = new MySQLiteHelper();

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
//	public static MySQLiteHelper getInstance(){
//		return instance;
//	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create Wordlists Table
		String CREATE_WORDLIST_TABLE = "CREATE TABLE " + WORDLISTS + "("
				+ W_COLUMN_ID + " INTEGER PRIMARY KEY," + W_COLUMN_NAME
				+ " TEXT," + W_COLUMN_CONTENT + " INTEGER" + ")";
		db.execSQL(CREATE_WORDLIST_TABLE);

	}
	public void reset(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + WORDLISTS);
		
		onCreate(db);
		db.close();
	}
	

	public void addWordlist(Wordlist wordlist) {

		ContentValues values = new ContentValues();
		values.put(W_COLUMN_NAME, wordlist.getName());
		values.put(W_COLUMN_CONTENT, wordlist.getContent());

		SQLiteDatabase db = this.getWritableDatabase();

		db.insert(WORDLISTS, null, values);
		db.close();
	}

	public List<Wordlist> getAllWordlists() {
		ArrayList<Wordlist> wordlists = new ArrayList<Wordlist>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + WORDLISTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(1);
				String words = cursor.getString(2);
				WordlistBuilder builder = new WordlistBuilder(name);
				builder.addWords(words);
				
				Wordlist wordlist = builder.getWordlist();
				wordlists.add(wordlist);
			} while (cursor.moveToNext());
		}

		// return contact list
		return wordlists;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	private static final String DATABASE_NAME = "localDatabase.db";
	private static final int DATABASE_VERSION = 1;

	// Wordlist Table
	private static final String WORDLISTS = "Wordlists";
	private static final String W_COLUMN_ID = "_id";
	private static final String W_COLUMN_NAME = "name";
	private static final String W_COLUMN_CONTENT = "content";

	// Games Table
	private static final String GAME_NAME = "Games";
	private static final String G_COLUMN_ID = "_id";
	private static final String G_COLUMN_NAME = "name";
	private static final String G_COLUMN_BOARD = "board";
	private static final String G_COLUMN_WORDLIST = "wordlist";
	private static final String G_COLUMN_played = "played";
	private static final String G_COLUMN_Score = "score";

	// Boards Table
	private static final String BOARD_NAME = "Boards";
	private static final String B_COLUMN_ID = "_id";
	private static final String B_COLUMN_NAME = "name";
	private static final String B_COLUMN_Content = "content";

}
