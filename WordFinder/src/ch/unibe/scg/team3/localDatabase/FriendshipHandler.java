package ch.unibe.scg.team3.localDatabase;

import java.util.ArrayList;

import ch.unibe.scg.team3.sharingService.Friendship;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class FriendshipHandler extends DataHandler {

	public FriendshipHandler(Context context) {
		super(context);
	}

	public boolean isFriendshipinDb(String objectId) {
		Cursor c = helper.rawQuery(
				"SELECT * FROM Friendship WHERE friendship_id = '" + objectId
						+ "'", null);
		if (c != null && c.getCount() != 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	public void setFriendship(Friendship dbfriendship) {
		ContentValues c = new ContentValues();
		c.put("friendship_id", dbfriendship.getFriendshipId());
		c.put("user_id", dbfriendship.getUserId());
		c.put("friend_id", dbfriendship.getFriendId());
		if (!isFriendshipinDb(dbfriendship.getFriendshipId())) {
			helper.insert("Friendship", null, c);

		}
	}

	public ArrayList<Friendship> getFriendships() {
		ArrayList<Friendship> friendships = new ArrayList<Friendship>();
		Cursor c = helper.rawQuery("SELECT * FROM Friendship", null);
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				friendships.add(new Friendship(c.getString(2), c.getString(0),
						c.getString(1)));
				c.moveToNext();
			}
			
			c.close();
		} else {
			c.close();
		}
		return friendships;
	}

	public void remove(Friendship deletedfriendship) {
		int test;
		test = helper.delete("Friendship", "friendship_id = ?",
				new String[] {deletedfriendship.getFriendshipId()});

	}

	public ArrayList<String> getFriendshipsOfUser(String userid) {
		ArrayList<String> list = new ArrayList<String>();
		Cursor c = helper.rawQuery(
				"SELECT user_id, friend_id FROM Friendship WHERE (user_id = ? "
						+ "OR friend_id = ? )", new String[] { userid, userid });
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				if (c.getString(0).equals(userid)) {
					list.add(c.getString(1));
				} else {
					list.add(c.getString(0));
				}
				c.moveToNext();
			}
		} else {
			c.close();
		}
		return list;
	}

	public String getFriendship(String userId, String friendId) {
		String friendshipId = null;
		Cursor c = helper.rawQuery(
				"SELECT friendship_id FROM Friendship WHERE (user_id = ? "
						+ "AND friend_id =?) OR (user_id = ? "
						+ "AND friend_id =?)", new String[] { userId, friendId,
						friendId, userId });
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			friendshipId = c.getString(0);

		} else {
			c.close();
		}
		return friendshipId;

	}

}
