package ch.unibe.scg.team3.localDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ch.unibe.scg.team3.wordfinder.R;

/**
 * 
 * This class is responsible for creating, opening and importing the database. It holds the name of the
 * database, provides methods for creating and updating of databases. Its the main
 * access to perform SQLite statements on the database. 
 * 
 * @author nils
 * @author adrian
 * 
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "localDatabase.db";
	private static int DATABASE_VERSION = 8;

	// TODO: read about this hint
	public static String DB_FILEPATH = "/data/data/ch.unibe.scg.team3.wordfinder/databases/";

	// TODO: dont need this in final release
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	private final Context context;
	private static MySQLiteHelper instance = null;

	private MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	// TODO: check if needed
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			importDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}
	
	public void importDatabase() throws IOException {
		
		InputStream input = context.getResources().openRawResource(
				R.raw.localdatabase);
		String outFileName = DB_FILEPATH + DATABASE_NAME;
		OutputStream output = new FileOutputStream(outFileName);

		byte[] buffer = new byte[1024];
		int length;

		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}

		output.flush();
		output.close();
		input.close();
	}

	public static MySQLiteHelper getInstance(Context context) {
		if (instance == null) {
			instance = new MySQLiteHelper(context.getApplicationContext());
		}
		return instance;
	}

}
