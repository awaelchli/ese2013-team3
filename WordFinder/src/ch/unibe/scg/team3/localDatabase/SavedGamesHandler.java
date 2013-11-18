package ch.unibe.scg.team3.localDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
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
 * 			Method to transfer a SavedGameObject to Database.  
 * 
 * @param game
 * 			takes a Saved game not Null
 * @return
 * 			Boolean value which indicates that saving was successfull or not.
 */
	public boolean saveGame(SavedGame game) {
		String name = game.getName();
		String board = game.getStringBoard();
		int words = game.getNumberOfFoundWords();
		String time = game.getTime();
		int score = game.getScore();
		boolean isPrivate = game.isPrivate();
		int guesses = game.getNumberOfAttempts();
		int wordlist = game.getWordlistId();
		int timesPlayed = 1;
		String date = (Long.toString(new Date().getTime()));
		if (!gameInDatabase(name)) {
			String sql = "INSERT INTO Games VALUES(NULL, ?, ? , " + words
					+ ", '" + time + "', '" + date + "', " + wordlist + ", "
					+ score + ", '" + isPrivate + "', " + timesPlayed + ", "
					+ guesses + ")";
			SQLiteDatabase db = helper.getReadableDatabase();
			db.execSQL(sql, new String[] { name, board });
			db.close();
			return true;
		} else {
			return false;
		}

	}
	/**
	 * 
	 * @return
	 * 		an ArrayList of SavedGames which contains all Games that are saved in the Database.
	 * 		If there are no Games in the Database the return is an empty ArrayList.
	 */

	public ArrayList<SavedGame> getSavedGames() {

		ArrayList<SavedGame> list = new ArrayList<SavedGame>();

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM Games ORDER BY Date DESC", null);

		if (c != null && c.getCount() != 0) {

			while (c.moveToNext()) {
				SavedGame game = new SavedGame();
				writeDataentryToGame(c, game);
				list.add(game);
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
 * 		the name of the Wordlist which is saved in the DataBase
 * @return
 * 		a SavedGame which can be empty when ther was no entry with this name in the Database.
 */
	public SavedGame getSavedGameByName(String name) {
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
 * @param gameName
 * @return
 * 		Boolean value which indicates whether a Game by the Name is in the Database.
 */
	public boolean gameInDatabase(String gameName) {
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
	private void writeDataentryToGame(Cursor c, SavedGame game) {
		game.setId(c.getInt(0));
		game.setName(c.getString(1));
		game.setStringBoard(c.getString(2));
		game.setNumberOfFoundWords(c.getInt(3));
		game.setTime(c.getString(4));
		game.setDate(sdf.format(new Date(Long.parseLong(c.getString(5)))));
		game.setWordlistId(c.getInt(6));
		game.setScore(c.getInt(7));
		game.setPrivate(Boolean.parseBoolean(c.getString(8)));
		game.setTimesPlayed(c.getInt(9));
		game.setAttempts(c.getInt(10));
	}
/**
 * 
 * @param name
 * 		of the Database to remove from Database.
 * @return
 * 		whether the deletion was successful.
 */
	public boolean removeGameByName(String name) {
		if (gameInDatabase(name)){
			SQLiteDatabase db = helper.getReadableDatabase();
			String[] query = { name };
			db.execSQL("DELETE FROM Games WHERE Name = ?", query);
			db.close();
			return true;
		}
		else{
		return false;
		}
	}

}
