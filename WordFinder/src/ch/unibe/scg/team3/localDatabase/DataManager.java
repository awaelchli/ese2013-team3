package ch.unibe.scg.team3.localDatabase;

import ch.unibe.scg.team3.wordlist.Wordlist;
import ch.unibe.scg.team3.wordlist.WordlistBuilder;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
/**
 * 
 * @author nils
 * 
 */
public class DataManager {
	Context context;
	MySQLiteHelper helper;
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	public DataManager(Context context) {
		this.context = context;
		this.helper = new MySQLiteHelper(context);
	}

	public void addWordlist(Wordlist wordlist) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("INSERT INTO Dictionary VALUES(NULL,'" + wordlist.getName()
				+ "')");

		String content = wordlist.getContent();
		String[] words = content.split(";");
		for (int i = 0; i < words.length; i++) {
			if (words[i].length() < 5 && words[i].length() > 0) {
				// System.out.println(words[i]);
				db.execSQL("INSERT INTO "
						+ words[i].substring(0, 1).toLowerCase()
						+ "short VALUES(NULL, '" + wordlist.getName() + "', '"
						+ words[i] + "')");

			} else if (words[i].length() > 0) {
				// System.out.println(words[i]);
				db.execSQL("INSERT INTO "
						+ words[i].substring(0, 1).toLowerCase()
						+ "long VALUES(NULL, '" + wordlist.getName() + "', '"
						+ words[i] + "')");

			}
		}
		db.close();
	}
	private void initDB(Context context) {
		 WordlistBuilder builder1 = new WordlistBuilder("English");
		 builder1.initialize(context);
		 Wordlist english = builder1.getWordlist();
		 addWordlist(english);
	}
	public void reset(Context context) {
		SQLiteDatabase db =helper.getWritableDatabase();
		for(int i = 0;i<ALPHABET.length();i++){
			db.execSQL("DROP TABLE IF EXISTS "+ ALPHABET.substring(i,i+1 ) +"short");
			db.execSQL("DROP TABLE IF EXISTS "+ ALPHABET.substring(i, i+1) +"long");
			
		}
		db.execSQL("DROP TABLE IF EXISTS Dictionary");
		helper.onCreate(db);
		initDB(context);
	}
}
