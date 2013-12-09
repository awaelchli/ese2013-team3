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
 */
public class SavedGamesHandler extends DataHandler {
	
	public static final String COL_PERSONAL = "IsPersonal";
	public static final String COL_WORDLIST = "Dictionary";
	public static final String COL_NAME = "Name";
	public static final String COL_BOARD = "Board";
	public static final String COL_TIMES_PLAYED = "TimesPlayed";
	public static final String COL_GUESSES = "Guesses";
	public static final String COL_SCORE = "Score";
	public static final String COL_DATE = "Date";
	public static final String COL_TIME = "Time";
	public static final String COL_WORDS = "Words";
	public static final String TABLE_GAMES = "Games";

	private final SimpleDateFormat sdf;

	public SavedGamesHandler(Context context) {
		super(context);
		sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.ENGLISH);
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
		String date = Long.toString(new Date().getTime());

		long id = -1;

		try {
			ContentValues values = new ContentValues();
			if (name != null) {
				values.put(COL_NAME, name);
			}
			values.put(COL_BOARD, board);
			values.put(COL_WORDS, words);
			values.put(COL_TIME, remainingTime);
			values.put(COL_DATE, date);
			values.put(COL_WORDLIST, wordlist);
			values.put(COL_SCORE, score);
			values.put(COL_PERSONAL, isPersonal);
			values.put(COL_TIMES_PLAYED, timesPlayed);
			values.put(COL_GUESSES, guesses);
			id = helper.insert(TABLE_GAMES, null, values);
			game.setId(id);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
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

		values.put(COL_WORDS, game.getNumberOfFoundWords());
		values.put(COL_TIME, game.getRemainingTime());
		String date = (Long.toString(new Date().getTime()));
		values.put(COL_DATE, date);
		values.put(COL_SCORE, game.getScore());
		values.put(COL_GUESSES, game.getNumberOfAttempts());
		values.put(COL_TIMES_PLAYED, game.getTimesPlayed());

		String whereClause = COL_ID + " = ?";
		String[] whereArgs = { String.valueOf(id) };

		helper.update(TABLE_GAMES, values, whereClause, whereArgs);
	}

	/**
	 * 
	 * @param id
	 *            The id of the game to be extracted from the database.
	 * @return A SavedGame which can be null when there was no entry with this
	 *         id in the database.
	 */
	public SavedGame getSavedGame(long id) {
		SavedGame game = new SavedGame();

		String[] columns = { "*" };
		String selection = COL_ID + " = ?";
		String[] selectionArgs = { String.valueOf(id) };
		Cursor c = helper.query(TABLE_GAMES, columns, selection, selectionArgs, null, null, null);

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
	 * @param id
	 *            The id of the game
	 * @return True if the game is in the database and false otherwise.
	 */
	public boolean isGameInDatabase(long id) {

		String[] columns = { COL_ID };
		String selection = COL_ID + " = ?";
		String[] selectionArgs = { String.valueOf(id) };

		Cursor cursor = helper.query(TABLE_GAMES, columns, selection, selectionArgs, null, null,
				null);

		return cursor != null && cursor.getCount() > 0;
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
		boolean personal = cursor.getInt(8) == 1 ? true : false;
		game.setPersonal(personal);
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
			helper.delete(TABLE_GAMES, COL_ID + " = " + id, null);
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
		values.put(COL_PERSONAL, true);
		values.put(COL_NAME, name);

		String whereClause = COL_ID + " = ?";
		String[] whereArgs = { String.valueOf(id) };

		int affected = helper.update(TABLE_GAMES, values, whereClause, whereArgs);

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

		String[] columns = { COL_ID };
		String selection = COL_PERSONAL + " = ?";
		String[] selectionArgs = { TRUE }; // 1 means true
		String orderBy = COL_DATE + " DESC";

		Cursor cursor = helper.query(TABLE_GAMES, columns, selection, selectionArgs, null, null,
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

	/**
	 * @param id
	 *            The id of the game
	 * @return True if the game has been tagged by the user and false if the
	 *         game is not tagged or the id is not in the database.
	 */
	public boolean isTaggedGame(long id) {
		SavedGame saved = getSavedGame(id);

		if (saved == null || !saved.isPersonal()) {
			return false;
		}
		return true;
	}

	public boolean isTaggedGame(SavedGame game) {
		return isTaggedGame(game.getId());
	}

}
