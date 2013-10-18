package ch.unibe.scg.team3.localDatabase;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.ContentValues;
import android.content.Context;
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
		String dictable = "CREATE TABLE Dictionary (_id INTEGER PRIMARY KEY, Name TEXT)";
		db.execSQL(dictable);
		for (int i = 0; i < 25; i++) {
			String shorttable = "CREATE TABLE " + ALPHABET.charAt(i)
					+ "short(_id INTEGER PRIMARY KEY ASC, Dictionary, "
					+ "Content TEXT, FOREIGN KEY(Dictionary) REFERENCES Dictionary(_id))" ;
			db.execSQL(shorttable);
			String longtable = "CREATE TABLE "
					+ ALPHABET.charAt(i)
					+ "long(_id INTEGER PRIMARY KEY ASC,Dictionary,"
					+ "Content TEXT, FOREIGN KEY(_id) REFERENCES Dictionary(_id))";
			db.execSQL(longtable);
			//initDB();
		}
	}

	private void initDB() {
		 File file1 = new File("res/raw/english.txt");
		 WordlistBuilder builder1 = new WordlistBuilder("English");
		 builder1.addWords(file1);
		 Wordlist english = builder1.getWordlist();
		 addWordlist(english);
		
		 File file2 = new File("res/raw/german.txt");
		 WordlistBuilder builder2 = new WordlistBuilder("German");
		 builder2.addWords(file2);
		 Wordlist german = builder2.getWordlist();
		 addWordlist(german);
	}

	public void reset(SQLiteDatabase db) {
//		close();
//		File file = new File(db.getPath());
//		file.delete();
		for(int i = 0;i<ALPHABET.length();i++){
			db.execSQL("DROP TABLE IF EXISTS "+ ALPHABET.substring(i,i+1 ) +"short");
			db.execSQL("DROP TABLE IF EXISTS "+ ALPHABET.substring(i, i+1) +"long");
			
		}
		db.execSQL("DROP TABLE IF EXISTS Dictionary");
		onCreate(db);
		initDB();
	}

	public void addWordlist(Wordlist wordlist) {

		ContentValues values = new ContentValues();
		values.put("Name", wordlist.getName());
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert("Dictionary", null, values);
		

		String content = wordlist.getContent();
		String[] words = content.split(";");
		for (int i = 0; i < words.length; i++) {
			if (words[i].length() < 5 && words[i].length() >0 ) {
				for (int j = 1; j < ALPHABET.length(); j++) {
					if (words[i].substring(0,1).equalsIgnoreCase(
							ALPHABET.substring(j-1, j))) {
						db.execSQL("INSERT INTO " + ALPHABET.substring(j-1, j) + "short VALUES(NULL, "
								+ wordlist.getName() + ", " + words[i]);
					}

				}
			} else if(words[i].length() >0) {
				for (int j = 1; j < ALPHABET.length(); j++) {
					if (words[i].substring(0, 1).equalsIgnoreCase(
							ALPHABET.substring(j-1, j))) {
						db.execSQL("INSERT INTO " + ALPHABET.substring(j-1, j) + "long VALUES(NULL, "
								+ wordlist.getName() + ", " + words[i]);
					}

				}
			}
		}db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private static final String DATABASE_NAME = "localDatabase.db";
	private static final int DATABASE_VERSION = 1;
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

}
