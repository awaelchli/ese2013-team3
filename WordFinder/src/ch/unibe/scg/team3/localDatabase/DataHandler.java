package ch.unibe.scg.team3.localDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class provides the connection between the SQLite database and the
 * classes which need the data. This class holds an instance of MySQLiteHelper
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

	
}
