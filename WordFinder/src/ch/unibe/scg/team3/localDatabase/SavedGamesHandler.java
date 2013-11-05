package ch.unibe.scg.team3.localDatabase;

import java.util.ArrayList;
import java.util.Date;

import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.RawBoardBuilder;
import ch.unibe.scg.team3.game.SavedGame;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

public class SavedGamesHandler extends DataHandler {

	public SavedGamesHandler(Context context) {
		super(context);
	}
	public void saveGame(String name, String board, int words, String time, 
							int score, boolean isPersonal, int guesses){
		
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		
		String wordlist = preferences
				.getString("choose_wordlist", null);
		
		WordlistHandler wHandler = new WordlistHandler(context);
					
		int timesPlayed = 1;
		
		String date = (new Date().toString());
		
		String sql = "INSERT INTO Games VALUES(NULL, '" + name+"', '"+ board +
				"', "+ words +", '" + time +"', '"+ date +"', '" + wordlist +"', "
				+ score +", '"+ isPersonal +"', " + timesPlayed + ", " + guesses+")";
		SQLiteDatabase db = helper.getReadableDatabase();
		db.execSQL(sql);
		db.close();
		
	}
	public ArrayList<SavedGame> getSavedGames(){
		ArrayList<SavedGame> list = new ArrayList<SavedGame>();
		SQLiteDatabase db = helper.getReadableDatabase();
		SavedGame temp = new SavedGame();
		Cursor c = db.rawQuery("SELECT * FROM Games", null);
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			for(int i =0 ; i < c.getCount(); i++){
				temp.setId(c.getInt(0));
				temp.setName(c.getString(1));
				temp.setBoard((new RawBoardBuilder(c.getString(2))).getBoard());
				temp.setFoundWords(c.getInt(3));
				temp.setTime(c.getString(4));
				temp.setDate(c.getString(5));
				temp.setWordlistId(c.getInt(6));
				temp.setScore(c.getInt(7));
				temp.setPrivate(Boolean.parseBoolean(c.getString(8)));
				temp.setTimesPlayed(c.getInt(9));
				temp.setGuesses(c.getInt(10));
				list.add(temp);
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
	
	

}
