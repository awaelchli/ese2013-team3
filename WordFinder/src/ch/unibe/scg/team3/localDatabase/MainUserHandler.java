package ch.unibe.scg.team3.localDatabase;

import ch.unibe.scg.team3.user.AbstractUser;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MainUserHandler extends UserHandler {

	public MainUserHandler(Context context) {
		super(context);
	}

	public boolean setName(String name) {
		SQLiteDatabase db = helper.getReadableDatabase();
		try {

			String[] args = { name };
			db.execSQL("UPDATE User SET Name = ? WHERE _id = 1", args);
		} catch (SQLException e) {
			db.close();
			e.printStackTrace();
			return false;
		}
		db.close();
		return true;
	}
	
	public boolean setEmail(String email) {
		SQLiteDatabase db = helper.getReadableDatabase();
		try {

			String[] args = { email };
			db.execSQL("UPDATE User SET Email = ? WHERE _id = 1", args);
		} catch (SQLException e) {
			db.close();
			e.printStackTrace();
			return false;
		}
		db.close();
		return true;
	}
	
	public boolean setUserId(String userid) {
		SQLiteDatabase db = helper.getReadableDatabase();
		try {

			String[] args = { userid };
			db.execSQL("UPDATE User SET user_id = ? WHERE _id = 1", args);
		} catch (SQLException e) {
			db.close();
			e.printStackTrace();
			return false;
		}
		db.close();
		return true;
	}

}
