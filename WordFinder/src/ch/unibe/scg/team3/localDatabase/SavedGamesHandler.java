package ch.unibe.scg.team3.localDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import ch.unibe.scg.team3.game.SavedGame;

/**
 * This class give the options to save games to the main database. It also
 * allows also to edit these games in different ways.
 * 
 * @author nils
 * @author adrian
 * 
 */
public class SavedGamesHandler extends DataHandler {
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm");

	public SavedGamesHandler(Context context) {
		super(context);
	}

	/**
	 * Stores a game in the database. If the id of this game already exists in
	 * the database, the changed values get updated if the score is higher than
	 * the old one.
	 * 
	 * @param game
	 *            a game to be saved or updated
	 * @return The id of the game. If the game was updated, the id is the same
	 *         and otherwise the id of the new entry will be returned.
	 */
	public long saveGame(SavedGame game) {

		if (game.getId() == -1) {
			return saveAsNewGame(game);
		} else {
			updateGame(game);
			return game.getId();
		}
	}

	/**
	 * Update only if score increased.
	 */
	private void updateGame(SavedGame game) {
		long id = game.getId();

		SavedGame old = getSavedGame(id);
		
		SQLiteDatabase db = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("TimesPlayed", game.getTimesPlayed());
		
		if (old.getScore() < game.getScore()) {
			
			values.put("Words", game.getNumberOfFoundWords());
			values.put("Time", game.getRemainingTime());
			String date = (Long.toString(new Date().getTime()));
			values.put("Date", date);
			values.put("Score", game.getScore());
			values.put("Guesses", game.getNumberOfAttempts());
		}
		
		String whereClause = "_id = ?";
		String[] whereArgs = { String.valueOf(id) };
		
		db.update("Games", values, whereClause, whereArgs);
		db.close();
	}

	private long saveAsNewGame(SavedGame game) {
		
		String name = game.getName();
		String board = game.getStringBoard();
		int words = game.getNumberOfFoundWords();
		long remainingTime = game.getRemainingTime();
		int score = game.getScore();
		boolean isPersonal = game.isPersonal();
		int guesses = game.getNumberOfAttempts();
		int wordlist = game.getWordlistId();
		int timesPlayed = 1;
		String date = (Long.toString(new Date().getTime()));

		SQLiteDatabase db = helper.getReadableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			if (name != null) {
				values.put("Name", name);
			}
			values.put("Board", board);
			values.put("Words", words);
			values.put("Time", remainingTime);
			values.put("Date", date);
			values.put("Dictionary", wordlist);
			values.put("Score", score);
			values.put("IsPersonal", Boolean.toString(isPersonal));
			values.put("TimesPlayed", timesPlayed);
			values.put("Guesses", guesses);
			long id = db.insert("Games", null, values);
			game.setId(id);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.close();
		return -1;
	}

	// /**
	// *
	// * @return an ArrayList of SavedGames which contains all Games that are
	// * saved in the Database. If there are no Games in the Database the
	// * return is an empty ArrayList.
	// */
	//
	// public ArrayList<SavedGame> getSavedGames() {
	//
	// ArrayList<SavedGame> list = new ArrayList<SavedGame>();
	//
	// SQLiteDatabase db = helper.getReadableDatabase();
	//
	// Cursor c = db.rawQuery("SELECT * FROM Games ORDER BY Date DESC", null);
	//
	// if (c != null && c.getCount() != 0) {
	//
	// while (c.moveToNext()) {
	// SavedGame game = new SavedGame();
	// if (writeDataentryToGame(c, game)) {
	// list.add(game);
	// }
	// }
	// c.close();
	// db.close();
	//
	// return list;
	// } else {
	// c.close();
	// db.close();
	// return list;
	// }
	//
	// }

	/**
	 * 
	 * @param name
	 *            the name of the Wordlist which is saved in the DataBase
	 * @return a SavedGame which can be empty when ther was no entry with this
	 *         name in the Database.
	 */
	public SavedGame getSavedGame(String name) {
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] query = { name };
		Cursor c = db.rawQuery("SELECT * FROM Games WHERE Name = ? ", query);
		SavedGame game = new SavedGame();
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();

			writeDataentryToGame(c, game);

			c.close();
			db.close();

			return game;
		} else {
			c.close();
			db.close();
			return game;
		}
	}

	/**
	 * 
	 * @param id
	 *            the name of the Wordlist which is saved in the DataBase
	 * @return a SavedGame which can be null when there was no entry with this
	 *         id in the Database.
	 */
	public SavedGame getSavedGame(long id) {
		SavedGame game = new SavedGame();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Games WHERE _id = " + id, null);
			if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			writeDataentryToGame(c, game);
			c.close();
			db.close();
			return game;
		} else {
			c.close();
			db.close();
			return null;
		}
	}

	/**
	 * 
	 * @param gameName
	 * @return Boolean value which indicates whether a Game by the Name is in
	 *         the Database.
	 */
	public boolean gameInDatabase(String gameName) {
		if (gameName == null) {
			return false;
		}
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Games WHERE Name = ?", new String[] { gameName });
		if (c != null && c.getCount() != 0) {
			c.close();
			db.close();
			return true;
		} else {
			c.close();
			db.close();
			return false;
		}
	}

	/**
	 * 
	 * @param gameId
	 * @return Boolean value which indicates whether a Game by the Id is in the
	 *         Database.
	 */
	public boolean gameInDatabase(long id) {

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Games WHERE _id = " + id, null);
		if (c != null && c.getCount() != 0) {
			c.close();
			db.close();
			return true;
		} else {
			c.close();
			db.close();
			return false;
		}
	}

	/**
	 * 
	 * @param c
	 *            cursor which holds the information of a saved game from
	 *            database.
	 * @param game
	 *            which should be the object
	 * @return whether the object creation from database was successful.
	 */

	private boolean writeDataentryToGame(Cursor c, SavedGame game) {

		game.setId(c.getInt(0));
		game.setName(c.getString(1));
		game.setStringBoard(c.getString(2));
		game.setNumberOfFoundWords(c.getInt(3));
		game.setRemainingTime(c.getLong(4));
		game.setDate(sdf.format(new Date(Long.parseLong(c.getString(5)))));
		game.setWordlistId(c.getInt(6));
		game.setScore(c.getInt(7));
		game.setPersonal(Boolean.parseBoolean(c.getString(8)));
		game.setTimesPlayed(c.getInt(9));
		game.setAttempts(c.getInt(10));
		return true;
	}

//	/**
//	 * 
//	 * @param name
//	 *            of the Database to remove from Database.
//	 * @return whether the deletion was successful.
//	 */
//	public boolean removeGame(String name) {
//		if (gameInDatabase(name)) {
//			SQLiteDatabase db = helper.getReadableDatabase();
//			String[] query = { name };
//			db.execSQL("DELETE FROM Games WHERE Name = ?", query);
//			db.close();
//			return true;
//		} else {
//			return false;
//		}
//	}

	/**
	 * @param id
	 *            The id of the game to remove from the database.
	 * @return True if the deletion was successful and false otherwise. 
	 */
	public boolean removeGame(long id) {
		if (gameInDatabase(id)) {
			SQLiteDatabase db = helper.getReadableDatabase();
			db.execSQL("DELETE FROM Games WHERE _id = " + id);
			db.close();
			return true;
		} else {
			return false;
		}
	}
	public boolean removeGame(SavedGame game){
		return removeGame(game.getId());
		
	}

	/**
	 * 
	 * @param id
	 *            from the game which has to be edited
	 * @param isPrivate
	 *            boolean variable to set in the database game.
	 * 
	 * @return whether the action was successful.
	 */

	private boolean setIsPrivate(long id, boolean isPrivate) {
		if (id >= 0) {
			SQLiteDatabase db = helper.getReadableDatabase();
			try {
				db.execSQL("UPDATE Games SET IsPersonal = '" + isPrivate + "' WHERE _id = " + id);
				db.close();
			} catch (SQLException e) {
				db.close();
				e.printStackTrace();
				return false;

			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param id
	 *            from the game to be renamed.
	 * @param name
	 *            String which should be the new game title.
	 * @return whether renaming was successful.
	 */
	private boolean setName(long id, String name) {
		if (id >= 0) {
			SQLiteDatabase db = helper.getReadableDatabase();
			try {
				String[] query = { name };
				db.execSQL("UPDATE Games SET Name = ? WHERE _id = " + id, query);
				db.close();
			} catch (SQLException e) {
				db.close();
				e.printStackTrace();
				return false;

			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tags a saved game with a name chosen from the user.
	 * 
	 * @param name
	 *            The name of the tag.
	 * @param id
	 *            The id of the saved game
	 * @return True if the id was found in the database and the name was set
	 *         successfully, false otherwise.
	 */
	public boolean tagSavedGame(String name, long id) {

		SQLiteDatabase db = helper.getReadableDatabase();

		ContentValues values = new ContentValues();
		values.put("IsPersonal", true);
		values.put("Name", name);

		String whereClause = "_id = ?";
		String[] whereArgs = { String.valueOf(id) };

		int affected = db.update("Games", values, whereClause, whereArgs);

		if (affected == 0) {
			return false;
		}
		return true;
	}

	/**
	 * @return A list of all saved games that have a tag, thus all the games the
	 *         user explicitly saved.
	 */
	public ArrayList<SavedGame> getTaggedGames() {
		ArrayList<SavedGame> list = new ArrayList<SavedGame>();

		SQLiteDatabase db = helper.getReadableDatabase();

		String[] columns = { "_id" };
		String selection = "IsPersonal = ?";
		String[] selectionArgs = { "1" }; // 1 means true
		String orderBy = "Date DESC";

		Cursor cursor = db.query("Games", columns, selection, selectionArgs, null, null, orderBy);

		if (cursor != null && cursor.getCount() != 0) {

			while (cursor.moveToNext()) {
				long id = cursor.getLong(0);
				SavedGame game = getSavedGame(id);
				list.add(game);
			}
			cursor.close();
		}

		db.close();
		return list;
	}

}
