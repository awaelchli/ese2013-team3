package ch.unibe.scg.team3.localDatabase;

import ch.unibe.scg.team3.user.AbstractUser;
import ch.unibe.scg.team3.user.Friend;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 
 * @author nils
 *
 */
public class FriendsHandler extends UserHandler {

	public FriendsHandler(Context context) {
		super(context);
		
	}

	public boolean setFriend(Friend user) {
		ContentValues c = new ContentValues();
		c.put("Name", user.getUserName());
		c.put("Email", user.getEmail());
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
	public boolean setFriendId(String friendid){
		return setUserId(friendid);
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
