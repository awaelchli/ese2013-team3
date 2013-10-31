package ch.unibe.scg.team3.localDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
public class DataHandler {

	protected Context context;
	protected MySQLiteHelper helper;

	public DataHandler(Context context) {
		this.context = context;
		this.helper = MySQLiteHelper.getInstance(context);
	}

	//TODO: remove in final version
	private void initDB(Context context) {
		WordlistHandler wordlist = new WordlistHandler(context);
		try {
			wordlist.addWordlistByFileInRaw("English", "english");
		} catch (WordlistAlreadyInDataBaseException e) {
			e.printStackTrace();
		}
		try {
			wordlist.addWordlistByFileInRaw("Deutsch", "german");
		} catch (WordlistAlreadyInDataBaseException e) {
			e.printStackTrace();
		}
		
	}
	
	//TODO: remove in final version
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

	}
}
