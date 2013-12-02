package ch.unibe.scg.team3.localDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.user.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserHandler extends DataHandler {

	public UserHandler(Context context) {
		super(context);
	}

	

	public boolean setUserId(String userid, int id) {

		ContentValues c = new ContentValues();
		c.put("user_id", userid);
		try {
			helper.update("User", c, "_id =" + id, null);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setUser(User user) {
		ContentValues c = new ContentValues();
		c.put("Name", user.getUserName());
		c.put("Email", user.getEmail());
		c.put("user_id", user.getUserID());
		try {
			helper.insert("User", null, c);
		} catch (Exception e) {
			e.printStackTrace();
		
			return false;
		}
		
		return true;
	}

	

	public boolean isUserinDb(String userid) {
		Cursor c = helper.rawQuery("SELECT * FROM User WHERE user_id = ? ",
				new String[] { userid });
		if (c.getCount() > 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		Cursor c = helper.rawQuery("SELECT * FROM User", null);
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				users.add(new User(c.getString(0), c.getString(1), c
						.getString(2)));
				c.moveToNext();
			}
			c.close();
		} else {
			c.close();
		}
		return users;

	}
	
	public User getUser(String userid) {
		User user = new User();
		Cursor c = helper.rawQuery("SELECT * FROM User WHERE user_id = ?", new String[] {userid});
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			user = new User(c.getString(0), c.getString(1), c.getString(2));
			c.close();
		} else {
			c.close();
		}
		return user;

	}

	public void remove(User deleteduser) {
		helper.delete("User", "user_id = ?", new String[] {deleteduser.getUserID()});
		
	}

}
