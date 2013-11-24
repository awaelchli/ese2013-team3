package ch.unibe.scg.team3.localDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.game.Timer;

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
	 * Method to transfer a SavedGameObject to Database.
	 * 
	 * @param game
	 *            takes a Saved game not Null
	 * @return Long value which indicates the id of the saved game in Database.
	 * 			Returns -1 when something went wrong 
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
		
			
			SQLiteDatabase db = helper.getReadableDatabase();
			try {				
				ContentValues c = new ContentValues();
				if(name != null){
						c.put("Name",name);
				}
				c.put("Board", board);
				c.put("Words", words);
				c.put("Time", remainingTime);
				c.put("Date", date);
				c.put("Dictionary", wordlist);
				c.put("Score", score);
				c.put("IsPersonal", Boolean.toString(isPersonal));
				c.put("TimesPlayed", timesPlayed);
				c.put("Guesses", guesses);
				long id = db.insert("Games", null, c);
				db.close();
				return id;
			} catch (Exception e) {
				db.close();
				e.printStackTrace();
				return -1;
				
			}
		
	}

	/**
	 * 
	 * @return an ArrayList of SavedGames which contains all Games that are
	 *         saved in the Database. If there are no Games in the Database the
	 *         return is an empty ArrayList.
	 */

	public ArrayList<SavedGame> getSavedGames() {

		ArrayList<SavedGame> list = new ArrayList<SavedGame>();

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM Games ORDER BY Date DESC", null);

		if (c != null && c.getCount() != 0) {

			while (c.moveToNext()) {
				SavedGame game = new SavedGame();
				if (writeDataentryToGame(c, game)) {
					list.add(game);
				}
			}
			c.close();
			db.close();

			return list;
		} else {
			c.close();
			db.close();
			return list;
		}

	}

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
	 * @return a SavedGame which can be empty when there was no entry with this
	 *         id in the Database.
	 */
	public SavedGame getSavedGame(long id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Games WHERE _id = " + id , null);
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
	 * @param gameName
	 * @return Boolean value which indicates whether a Game by the Name is in
	 *         the Database.
	 */
	public boolean gameInDatabase(String gameName) {
		if (gameName == null) {
			return false;
		}
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Games WHERE Name = ?",
				new String[] { gameName });
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
	 * @return Boolean value which indicates whether a Game by the Id is in
	 *         the Database.
	 */
	public boolean gameInDatabase(long id) {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Games WHERE _id = "+id,null);
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
	 * @param c cursor which holds the information of a saved game from database.
	 * @param game which should be the object
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

	/**
	 * 
	 * @param name
	 *            of the Database to remove from Database.
	 * @return whether the deletion was successful.
	 */
	public boolean removeGame(String name) {
		if (gameInDatabase(name)) {
			SQLiteDatabase db = helper.getReadableDatabase();
			String[] query = { name };
			db.execSQL("DELETE FROM Games WHERE Name = ?", query);
			db.close();
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 
	 * @param id
	 *            of the Database to remove from Database.
	 * @return whether the deletion was successful.
	 */
	public boolean removeGame(long id) {
		if (gameInDatabase(id)) {
			SQLiteDatabase db = helper.getReadableDatabase();
			db.execSQL("DELETE FROM Games WHERE _id = "+id);
			db.close();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param id from the game which has to be edited
	 * @param isPrivate boolean variable to set in the database game.
	 * 
	 * @return whether the action was successful.
	 */

	public boolean setIsPrivate(long id, boolean isPrivate){
		if (id >= 0) {
			SQLiteDatabase db = helper.getReadableDatabase();
			try {
				db.execSQL("UPDATE Games SET IsPersonal = '"+isPrivate+"' WHERE _id = " + id);
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
	 * @param id from the game to be renamed.
	 * @param name String which should be the new game title.
	 * @return whether renaming was successful.
	 */
	public boolean setName(long id, String name){
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

}
