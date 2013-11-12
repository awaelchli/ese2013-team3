package ch.unibe.scg.team3.wordfinder.test;

import java.io.File;
import java.io.IOException;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.localDatabase.SavedGamesHandler;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.wordfinder.R;
import android.test.AndroidTestCase;

public class SavedGamesHandlerTest extends AndroidTestCase implements IDataHandlerTest {
	protected WordlistHandler wordlistHandler;
	protected SavedGamesHandler savedGamesHandler;

	public void setUp() {
		wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		File database = new File(
				"/data/data/ch.unibe.scg.team3.wordfinder/databases/localDatabase.db");
		try {
			wordlistHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	



	public void testGetSavedGames(){
		SavedGame saved = new SavedGame();
		SavedGame saved2 = new SavedGame();
		saved.setName("Test1");
		saved2.setName("Test2");
		savedGamesHandler = new SavedGamesHandler(mContext.getApplicationContext());
		savedGamesHandler.saveGame(saved);
		savedGamesHandler.saveGame(saved2);
		assertEquals(savedGamesHandler.getSavedGames().size(),2);
	}
	public void testGetSavedGameByNameAndSaveGame(){
		SavedGame saved = new SavedGame();
		SavedGame saved2 = new SavedGame();
		String board ="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		saved.setName("Test");
		saved.setStringBoard(board);
		saved.setDate("now");
		saved.setPrivate(true);
		saved.setScore(914356341);
		saved.setTime("now");
		saved.setTimesPlayed(0);
		saved.setGuesses(1332434);
		saved.setWordlistId(1);
		savedGamesHandler = new SavedGamesHandler(mContext.getApplicationContext());
		savedGamesHandler.saveGame(saved);
		saved2 = savedGamesHandler.getSavedGameByName("Test");
		assertEquals(saved2.getName(), "Test");
		assertEquals(saved2.getStringBoard(), board);
		assertEquals(saved2.isPrivate(), true);
		assertEquals(saved2.getScore(), 914356341);
		assertEquals(saved2.getTime(),"now");
		assertEquals(saved2.getNumberOfGuesses(), 1332434);
		assertEquals(saved2.getWordlistId(), 1);
	}
	public void testGameInDatabase(){
		SavedGame saved = new SavedGame();
		saved.setName("Test");
		savedGamesHandler = new SavedGamesHandler(mContext.getApplicationContext());
		savedGamesHandler.saveGame(saved);
		assertTrue(savedGamesHandler.gameInDatabase("Test"));
	}
	public void testRemoveGameByName(){
		SavedGame saved = new SavedGame();
		saved.setName("Test");
		savedGamesHandler = new SavedGamesHandler(mContext.getApplicationContext());
		savedGamesHandler.saveGame(saved);
		assertTrue(savedGamesHandler.gameInDatabase("Test"));
		savedGamesHandler.removeGameByName("Test");
		assertFalse(savedGamesHandler.gameInDatabase("Test"));
	}

}
