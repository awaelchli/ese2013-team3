package ch.unibe.scg.team3.localDatabase;

import java.util.ArrayList;
import java.util.Date;

import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.RawBoardBuilder;
import ch.unibe.scg.team3.game.AbstractGame;
import ch.unibe.scg.team3.game.SavedGame;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

/**
 * This class give the options to save games to the main database. It also
 * allows also to edit these games in different ways.
 * 
 * @author nils
 * @author adrian
 * 
 */
public class SavedGamesHandler extends DataHandler {

	public SavedGamesHandler(Context context) {
		super(context);
	}

	public boolean saveGame(SavedGame game) {
		String name = game.getName();
		String board = game.getStringBoard();
		int words = game.getFoundWords().size();
		String time = game.getTime();
		int score = game.getScore();
		boolean isPersonal = game.isPersonal();
		int guesses = game.getNumberOfGuesses();

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String wordlist = preferences.getString("choose_wordlist", null);
		int timesPlayed = 1;
		String date = (new Date().toString());
		if(!gameInDatabase(name)){
		String sql = "INSERT INTO Games VALUES(NULL, ?, '" + board + "', "
				+ words + ", '" + time + "', '" + date + "', '" + wordlist
				+ "', " + score + ", '" + isPersonal + "', " + timesPlayed
				+ ", " + guesses + ")";
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL(sql, new String[] { name });
		db.close();
		return true;
		}
		else{
			return false;
		}

	}

	public ArrayList<SavedGame> getSavedGames() {

		ArrayList<SavedGame> list = new ArrayList<SavedGame>();
		
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM Games", null);

		if (c != null && c.getCount() != 0) {

			while (c.moveToNext()) {
				SavedGame game = new SavedGame();
				game.setId(c.getInt(0));
				game.setName(c.getString(1));
				game.setStringBoard(c.getString(2));
				// game.setFoundWords(c.getInt(3));
				game.setTime(c.getString(4));
				game.setDate(c.getString(5));
				game.setWordlistId(c.getInt(6));
				game.setScore(c.getInt(7));
				game.setPrivate(Boolean.parseBoolean(c.getString(8)));
				game.setTimesPlayed(c.getInt(9));
				game.setGuesses(c.getInt(10));
				list.add(game);
			}
			c.close();
			db.close();

			return list;
		}
		else {
			c.close();
			db.close();
			return list;
		}

	}

	public SavedGame getSavedGameByName(String name) {
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] query={name};
		Cursor c = db.rawQuery("SELECT * FROM Games WHERE Name = ?", query);
		SavedGame game = new SavedGame();
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();

			game.setId(c.getInt(0));
			game.setName(c.getString(1));
			game.setStringBoard(c.getString(2));
//			game.setFoundWords(c.getInt(3));
			game.setTime(c.getString(4));
			game.setDate(c.getString(5));
			game.setWordlistId(c.getInt(6));
			game.setScore(c.getInt(7));
			game.setPrivate(Boolean.parseBoolean(c.getString(8)));
			game.setTimesPlayed(c.getInt(9));
			game.setGuesses(c.getInt(10));

			c.close();
			db.close();

			return game;
		} else {
			c.close();
			db.close();
			return game;
		}
	}
	public boolean gameInDatabase(String gameName){
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT * FROM Games WHERE Name = ?", new String[] {gameName});
		if (c != null && c.getCount() != 0) {
			c.close();
			db.close();
			return true;
		}
		else{
			c.close();
			db.close();
			return false;
		}
	}
	public boolean removeGameByName(String name){
		SQLiteDatabase db = helper.getReadableDatabase();
		
		String[] query={name};
		try {
			db.execSQL("DELETE FROM Games WHERE Name = ?", query);
		} catch (SQLException e) {
			db.close();
			return false;
		}
		db.close();
		return true;
	}

}
