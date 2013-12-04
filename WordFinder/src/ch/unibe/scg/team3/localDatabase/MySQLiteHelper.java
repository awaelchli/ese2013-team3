package ch.unibe.scg.team3.localDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	private static int DATABASE_VERSION = 15;

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
	public synchronized void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}
	
	public synchronized void importDatabase() throws IOException {
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

	public static synchronized MySQLiteHelper getInstance(Context context) {
		if (instance == null) {
			instance = new MySQLiteHelper(context.getApplicationContext());
		}
		return instance;
	}
	
	public synchronized long insert(String table, String nullColumnHack, ContentValues values){
		return getWritableDatabase().insert(table, nullColumnHack, values);
	}
	
	public synchronized int delete(String table, String whereClause, String[] whereArgs){
		return getWritableDatabase().delete(table, whereClause, whereArgs);
	}
	public synchronized int update(String table, ContentValues values, String whereClause, String[] whereArgs){
		return getWritableDatabase().update(table, values, whereClause, whereArgs);
	}
	
	public synchronized void execSQL(String sql, String[] bindArgs){
		getWritableDatabase().execSQL(sql, bindArgs);
	}
	public synchronized void execSQL(String sql){
		getWritableDatabase().execSQL(sql);
	}
	
	public synchronized void copyDB() throws IOException {
		importDatabase();
		
	}

	public synchronized Cursor rawQuery(String sql, String[] selectionArgs) {
		return getWritableDatabase().rawQuery(sql, selectionArgs);
	}
	public void close(){
		getWritableDatabase().close();
	}


	public synchronized Cursor query(String sql, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return getWritableDatabase().query(sql, columns, selection, selectionArgs, groupBy, having, orderBy);
		
	}


	public void open() {
		getWritableDatabase ();
		
	}

}
