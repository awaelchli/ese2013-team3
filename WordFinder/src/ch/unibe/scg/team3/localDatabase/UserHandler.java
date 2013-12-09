package ch.unibe.scg.team3.localDatabase;

import java.util.ArrayList;
import ch.unibe.scg.team3.user.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

/**
 * This class provides access to the users in the database.
 * 
 * @author nils
 * @author adrian
 */
public class UserHandler extends DataHandler {

	public static final String COL_EMAIL = "Email";
	public static final String COL_NAME = "Name";
	public static final String TABLE_USER = "User";
	public static final String COL_USER_ID = "user_id";

	public UserHandler(Context context) {
		super(context);
	}

	public boolean setUserId(String userId, int id) {

		ContentValues values = new ContentValues();
		values.put(COL_USER_ID, userId);

		String whereClause = COL_ID + " = ?";
		String[] whereArgs = { String.valueOf(id) };
		try {
			helper.update(TABLE_USER, values, whereClause, whereArgs);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param user
	 *            The user to insert, should not be null.
	 * @return True, if the user was inserted successfully and false otherwise.
	 */
	public boolean insertUser(User user) {
		ContentValues values = new ContentValues();
		values.put(COL_NAME, user.getUsername());
		values.put(COL_EMAIL, user.getEmail());
		values.put(COL_USER_ID, user.getUserID());

		try {
			helper.insert(TABLE_USER, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * @param userId
	 *            The id of the user to find. Note: This is not the id of the
	 *            row in the database.
	 * @return True, if the user with the given id is already in the database
	 *         and false otherwise.
	 */
	public boolean isUserinDb(String userId) {
		String[] columns = { "*" };
		String selection = COL_USER_ID + " = ?";
		String[] selectionArgs = { userId };
		Cursor c = helper.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

		if (c != null && c.getCount() > 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	/**
	 * @return An ArrayList containing all users stored in the database.
	 */
	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();

		String[] selection = { "*" };
		Cursor cursor = helper.query(TABLE_USER, selection, null, null, null, null, null);

		if (cursor != null && cursor.getCount() != 0) {
			while (cursor.moveToNext()) {
				users.add(new User(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
			}
		}

		cursor.close();
		return users;

	}

	/**
	 * @param userId
	 *            The id of the user to be searched for.
	 * @return The user, containing the data that is stored in the database. If
	 *         the id was not found, the User will be empty.
	 */
	public User getUser(String userId) {
		User user = new User();

		String[] columns = { "*" };
		String selection = COL_USER_ID + " = ?";
		String[] selectionArgs = { userId };
		Cursor cursor = helper.query(TABLE_USER, columns, selection, selectionArgs, null, null,
				null);

		if (cursor != null && cursor.getCount() != 0) {
			cursor.moveToFirst();
			user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
		}

		cursor.close();
		return user;

	}

	/**
	 * @param user
	 *            Removes the given user if his id is in the database.
	 */
	public void remove(User user) {
		helper.delete(TABLE_USER, COL_USER_ID + " = ?", new String[] { user.getUserID() });
	}
}
