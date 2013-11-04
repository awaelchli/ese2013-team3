package ch.unibe.scg.team3.localDatabase;

import java.util.Date;

import ch.unibe.scg.team3.board.Board;
import android.content.Context;
import android.content.SharedPreferences;
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
	

}
