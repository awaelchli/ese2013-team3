package ch.unibe.scg.team3.localDatabase;

import java.io.IOException;

import android.content.Context;

/**
 * This class provides the connection between the SQLite database and the
 * classes which need the data. This class holds an instance of MySQLiteHelper
 * 
 * @author nils
 */
public class DataHandler {

	/*
	 * The row id in SQLite Database.
	 */
	public static final String COL_ID = "_id";
	/*
	 * SQLite represents boolean values as Strings.
	 */
	public static final String TRUE = "1";
	public static final String FALSE = "0";

	protected final Context context;
	protected final MySQLiteHelper helper;

	public DataHandler(Context context) {
		this.context = context;
		this.helper = MySQLiteHelper.getInstance(context);
		helper.open();
	}

	public synchronized void copyDB() throws IOException {
		helper.copyDB();
	}

	public synchronized void closeDB() {
		helper.close();
	}
	public synchronized void setDbLock(Boolean lock){
		helper.setLocked(lock);
	}

}
