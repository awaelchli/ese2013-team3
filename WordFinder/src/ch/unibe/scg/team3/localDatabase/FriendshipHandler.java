package ch.unibe.scg.team3.localDatabase;

import ch.unibe.scg.team3.user.Friend;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FriendshipHandler extends DataHandler {

	public FriendshipHandler(Context context) {
		super(context);
	}
	
	public boolean setFriend(Friend friend, int friend_id){
			
		ContentValues c = new ContentValues();
		c.put("user_id", 1);
		c.put("friend_id", friend.getUserID());
		SQLiteDatabase db = helper.getReadableDatabase();
		
		try {
			db.insert("Friendship", null, c);
		} catch (Exception e) {
			e.printStackTrace();
			db.close();
			return false;
		}
		db.close();
		return true;
		 
	}

}
