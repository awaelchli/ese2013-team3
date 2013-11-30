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
		try {
	
			String[] args = { name };
			helper.execSQL("UPDATE User SET Name = ? WHERE _id = 1", args);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setUserEmail(String email) {
		try {
	
			String[] args = { email };
			helper.execSQL("UPDATE User SET Email = ? WHERE _id = 1", args);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setUserId(String userid, int id) {
		
		ContentValues c= new ContentValues();
		c.put("user_id", userid);
		try {
			helper.update("User", c, "_id =" + id,null);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setUser(MainUser muser) {
		ContentValues c = new ContentValues();
		c.put("Name", muser.getUserName());
		c.put("Email", muser.getEmail());
		try {
			helper.insert("User", null, c);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public Friend getFriendByEmail(String email) {
			String[] query = { email };
			Cursor c = helper.rawQuery("SELECT * FROM User WHERE Email = ? ", query);
			Friend friend = new Friend();
			if (c != null && c.getCount() != 0) {
				c.moveToFirst();
				friend.setUserName(c.getString(1));
				friend.setEmail(c.getString(2));
				friend.setFriendId(c.getString(3));
				c.close();
	
				return friend;
			} else {
				c.close();
				return friend;
			
		}
	}

}
