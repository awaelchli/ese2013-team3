package ch.unibe.scg.team3.wordfinder.test;

import java.io.IOException;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.localDatabase.*;
import android.test.AndroidTestCase;

public class SavedGamesHandlerTest extends AndroidTestCase implements IDataHandlerTest {
	protected WordlistHandler wordlistHandler;
	protected SavedGamesHandler savedGamesHandler;

	public void setUp() {
		wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		try {
			wordlistHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


	public void testSaveGame(){
		SavedGame sG = new SavedGame();
		savedGamesHandler = new SavedGamesHandler(mContext.getApplicationContext());
		savedGamesHandler.saveGame(sG);
		assertTrue(savedGamesHandler.gameInDatabase("Test"));
		
	}
	public void testGetSavedGames(){
		SavedGame sG = new SavedGame();
		String board ="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		savedGamesHandler = new SavedGamesHandler(mContext.getApplicationContext());
		savedGamesHandler.saveGame(sG);
		
	}
	public void testGetSavedGameByName(){
		
	}
	public void testGameInDatabase(){
		
	}
	public void testRemoveGameByName(){
		
	}

}
