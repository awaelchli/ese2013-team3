package ch.unibe.scg.team3.localDatabase;

import java.util.List;

import ch.unibe.scg.team3.sharingService.Friendship;
import ch.unibe.scg.team3.user.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FriendshipHandler extends DataHandler {

	public FriendshipHandler(Context context) {
		super(context);
	}
	
	public boolean setFriend(User friend, int friend_id){
		
		ContentValues c = new ContentValues();
		c.put("user_id", 1);
		c.put("friend_id", friend.getUserID());		
		try {
			helper.insert("Friendship", null, c);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean isFriendshipinDb(String objectId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setFriendship(Friendship dbfriendship) {
		// TODO Auto-generated method stub
		
	}

	public List<Friendship> getFriendships() {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(Friendship deletedfriendship) {
		// TODO Auto-generated method stub
		
	}

}
