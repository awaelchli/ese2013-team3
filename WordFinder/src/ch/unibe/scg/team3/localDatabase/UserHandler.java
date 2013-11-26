package ch.unibe.scg.team3.localDatabase;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.user.Friend;
import ch.unibe.scg.team3.user.MainUser;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserHandler extends DataHandler {

	public UserHandler(Context context) {
		super(context);
	}

	public boolean setUserName(String name) {
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

	public boolean setUserEmail(String email) {
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

	public boolean setUserId(String userid, int id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		try {
	
			String[] args = { userid };
			db.execSQL("UPDATE User SET user_id = ? WHERE _id =" + id, args);
		} catch (SQLException e) {
			db.close();
			e.printStackTrace();
			return false;
		}
		db.close();
		return true;
	}

	public boolean setUser(MainUser muser) {
		ContentValues c = new ContentValues();
		c.put("Name", muser.getUserName());
		c.put("Email", muser.getEmail());
		SQLiteDatabase db = helper.getReadableDatabase();
		try {
			db.insert("User", null, c);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return false;
		}
		db.close();
		return true;
	}


	public Friend getFriendByEmail(String email) {
		
			SQLiteDatabase db = helper.getReadableDatabase();
			String[] query = { email };
			Cursor c = db.rawQuery("SELECT * FROM User WHERE Email = ? ", query);
			Friend friend = new Friend();
			if (c != null && c.getCount() != 0) {
				c.moveToFirst();
				friend.setUserName(c.getString(1));
				friend.setEmail(c.getString(2));
				friend.setFriendId(c.getString(3));
				c.close();
				db.close();
	
				return friend;
			} else {
				c.close();
				db.close();
				return friend;
			
		}
	}

}
