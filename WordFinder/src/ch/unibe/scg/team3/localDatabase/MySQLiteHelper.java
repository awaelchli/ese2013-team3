package ch.unibe.scg.team3.localDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ch.unibe.scg.team3.wordfinder.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * 
 * This class is responsible for creating the database. It holds the name of the db
 * 	provides methods for creating and updating of databases. Its the main access
 * 	to perform SQLite statments on the db.
 * 
 * 
 * @author nils
 * @author adrian
 * 
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
	private Context context;
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context=context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String dictable = "CREATE TABLE Dictionary (_id INTEGER PRIMARY KEY ASC, Name TEXT)";
		db.execSQL(dictable);
		db.beginTransaction();
		try{
		for (int i = 0; i < ALPHABET.length(); i++) {
			String shorttable = "CREATE TABLE " + ALPHABET.charAt(i)
					+ "short(_id INTEGER PRIMARY KEY ASC, Dictionary, "
					+ "Content TEXT, FOREIGN KEY(Dictionary) REFERENCES Dictionary(_id) " +
					"ON DELETE CASCADE ON UPDATE CASCADE)" ;
			db.execSQL(shorttable);
			String longtable = "CREATE TABLE "
					+ ALPHABET.charAt(i)
					+ "long(_id INTEGER PRIMARY KEY ASC,Dictionary,"
					+ "Content TEXT, FOREIGN KEY(Dictionary) REFERENCES Dictionary(_id) " +
					"ON DELETE CASCADE ON UPDATE CASCADE)";
			db.execSQL(longtable);
		}
		db.setTransactionSuccessful();
		}
		finally{
			db.endTransaction();
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onOpen(SQLiteDatabase db) {
	    super.onOpen(db);
	    if (!db.isReadOnly()) {
	        // Enable foreign key constraints
	        db.execSQL("PRAGMA foreign_keys=ON;");
	    }
	}
	public static String DB_FILEPATH = "/data/data/com.scg.team3.wordfinder/databases/localDatabase.db";

	/**
	 * Copies the database file at the specified location over the current
	 * internal application database.
	 * */
	public boolean importDatabase() throws IOException {

	    // Close the SQLiteOpenHelper so it will commit the created empty
	    // database to internal storage.
	    close();
	    File newDb = new File("raw/localdatabase.db");
	    
	    File oldDb = new File(DB_FILEPATH);
	    if (newDb.exists()) {
	        FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
	        // Access the copied database so SQLiteHelper will cache it and mark
	        // it as created.
	        getWritableDatabase().close();
	        return true;
	    }
	    return false;
	}

	private static final String DATABASE_NAME = "localDatabase.db";
	private static final int DATABASE_VERSION = 1;
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

}
