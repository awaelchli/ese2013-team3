package ch.unibe.scg.team3.localDatabase;

import ch.unibe.scg.team3.user.AbstractUser;
import ch.unibe.scg.team3.user.Friend;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainUserHandler extends UserHandler {

	public MainUserHandler(Context context) {
		super(context);
	}

	public AbstractUser getUserByEmail(String email) {
		
			SQLiteDatabase db = helper.getReadableDatabase();
			String[] query = { email };
			Cursor c = db.rawQuery("SELECT * FROM User WHERE Email = ? ", query);
			Friend friend = new Friend();
			if (c != null && c.getCount() != 0) {
				c.moveToFirst();
	
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
