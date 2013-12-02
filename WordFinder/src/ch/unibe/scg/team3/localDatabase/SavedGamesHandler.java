package ch.unibe.scg.team3.localDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import ch.unibe.scg.team3.game.SavedGame;

/**
 * This class gives the options to save games to the main database. It also
 * allows also to edit these games in different ways.
 * 
 * @author nils
 * @author adrian
 * 
 */
public class SavedGamesHandler extends DataHandler {
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.ENGLISH);

	public SavedGamesHandler(Context context) {
		super(context);
	}

	/**
	 * Stores a game in the database. If the id of this game already exists in
	 * the database, the game will be saved under a new id.
	 * 
	 * @param game
	 *            a game to be saved.
	 * @return The id of the newly saved game.
	 */
	public long saveGame(SavedGame game) {

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
			long id = helper.insert("Games", null, values);
			game.setId(id);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * @param game
	 *            The game to be updated. If the id of the game does not exist
	 *            in the database, nothing will happen.
	 */
	public void updateGame(SavedGame game) {

		if (!isGameInDatabase(game)) {
			return;
		}

		long id = game.getId();

		ContentValues values = new ContentValues();

		values.put("Words", game.getNumberOfFoundWords());
		values.put("Time", game.getRemainingTime());
		String date = (Long.toString(new Date().getTime()));
		values.put("Date", date);
		values.put("Score", game.getScore());
		values.put("Guesses", game.getNumberOfAttempts());
		values.put("TimesPlayed", game.getTimesPlayed());

		String whereClause = "_id = ?";
		String[] whereArgs = { String.valueOf(id) };

		helper.update("Games", values, whereClause, whereArgs);
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

	// /**
	// *
	// * @param name
	// * the name of the Wordlist which is saved in the DataBase
	// * @return a SavedGame which can be empty when ther was no entry with this
	// * name in the Database.
	// */
	// public SavedGame getSavedGame(String name) {
	// String[] query = { name };
	// Cursor c = helper.rawQuery("SELECT * FROM Games WHERE Name = ? ", query);
	// SavedGame game = new SavedGame();
	// if (c != null && c.getCount() != 0) {
	// c.moveToFirst();
	// writeDataToObject(c, game);
	// c.close();
	// return game;
	// } else {
	// c.close();
	// return game;
	// }
	// }

	/**
	 * 
	 * @param id
	 *            The id of the game to be extracted from the database.
	 * @return A SavedGame which can be null when there was no entry with this
	 *         id in the dsatabase.
	 */
	public SavedGame getSavedGame(long id) {
		SavedGame game = new SavedGame();
		Cursor c = helper.rawQuery("SELECT * FROM Games WHERE _id = " + id, null);
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			writeDataToObject(c, game);
			c.close();
			return game;
		} else {
			c.close();
			return null;
		}
	}

	 /**
	 *
	 * @param gameName
	 * @return Boolean value which indicates whether a Game by the Name is in
	 * the Database.
	 */
	 public boolean gameInDatabase(String gameName) {
	 if (gameName == null) {
	 return false;
	 }
	 Cursor c = helper.rawQuery("SELECT * FROM Games WHERE Name = ?", new
	 String[] { gameName });
	 if (c != null && c.getCount() != 0) {
	 c.close();
	 return true;
	 } else {
	 c.close();
	 return false;
	 }
	 }

	/**
	 * @param id
	 *            The id of the game
	 * @return True if the game is in the database and false otherwise.
	 */
	public boolean isGameInDatabase(long id) {

		Cursor c = helper.rawQuery("SELECT * FROM Games WHERE _id = " + id, null);
		if (c != null && c.getCount() != 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	public boolean isGameInDatabase(SavedGame game) {
		return isGameInDatabase(game.getId());
	}

	/**
	 * @param cursor
	 *            The cursor which holds the information of a {@link SavedGame}
	 *            from the database.
	 * @param game
	 *            The SavedGame object in whicht the data should be written.
	 * @return True, if the creation of the SavedGame object was successful and
	 *         false otherwise.
	 */

	private boolean writeDataToObject(Cursor cursor, SavedGame game) {

		game.setId(cursor.getInt(0));
		game.setName(cursor.getString(1));
		game.setStringBoard(cursor.getString(2));
		game.setNumberOfFoundWords(cursor.getInt(3));
		game.setRemainingTime(cursor.getLong(4));
		game.setDate(sdf.format(new Date(Long.parseLong(cursor.getString(5)))));
		game.setWordlistId(cursor.getInt(6));
		game.setScore(cursor.getInt(7));
		game.setPersonal(Boolean.parseBoolean(cursor.getString(8)));
		game.setTimesPlayed(cursor.getInt(9));
		game.setAttempts(cursor.getInt(10));

		return true;
	}

	/**
	 * @param id
	 *            The id of the game to remove from the database.
	 * @return True if the deletion was successful and false otherwise.
	 */
	public boolean removeGame(long id) {
		if (isGameInDatabase(id)) {
			helper.delete("Games", "_id = " + id, null);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeGame(SavedGame game) {
		return removeGame(game.getId());
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

		ContentValues values = new ContentValues();
		values.put("IsPersonal", true);
		values.put("Name", name);

		String whereClause = "_id = ?";
		String[] whereArgs = { String.valueOf(id) };

		int affected = helper.update("Games", values, whereClause, whereArgs);

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

		String[] columns = { "_id" };
		String selection = "IsPersonal = ?";
		String[] selectionArgs = { "1" }; // 1 means true
		String orderBy = "Date DESC";

		Cursor cursor = helper.query("Games", columns, selection, selectionArgs, null, null,
				orderBy);

		if (cursor != null && cursor.getCount() != 0) {

			while (cursor.moveToNext()) {
				long id = cursor.getLong(0);
				SavedGame game = getSavedGame(id);
				list.add(game);
			}
			cursor.close();
		}
		return list;
	}

}
