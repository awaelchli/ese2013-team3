package ch.unibe.scg.team3.localDatabase;

import ch.unibe.scg.team3.user.AbstractUser;
import ch.unibe.scg.team3.user.Friend;
import ch.unibe.scg.team3.user.MainUser;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainUserHandler extends UserHandler {

	public MainUserHandler(Context context) {
		super(context);
	}

	public MainUser getMainUser() {
		
			SQLiteDatabase db = helper.getReadableDatabase();
			
			Cursor c = db.rawQuery("SELECT * FROM User WHERE _id = 1 ",null);
			MainUser user = new MainUser();
			if (c != null && c.getCount() != 0) {
				c.moveToFirst();
				user.setUserName(c.getString(1));
				user.setEmail(c.getString(2));
				user.setUserId(c.getString(3));
				c.close();
				db.close();
				
				return user;
			} else {
				c.close();
				db.close();
				return user;
			
		}
	}

}
