package ch.unibe.scg.team3.localDatabase;

import ch.unibe.scg.team3.user.AbstractUser;
import ch.unibe.scg.team3.user.Friend;
import android.content.ContentValues;
import android.content.Context;
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

}
