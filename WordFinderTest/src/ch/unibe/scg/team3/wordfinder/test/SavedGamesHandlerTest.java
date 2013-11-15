package ch.unibe.scg.team3.wordfinder.test;

import java.io.IOException;

import android.test.AndroidTestCase;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.localDatabase.SavedGamesHandler;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.wordfinder.R;

public class SavedGamesHandlerTest extends AndroidTestCase implements IDataHandlerTest {
	
	
	protected String defaultBoard = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	protected WordlistHandler wordlistHandler;
	protected SavedGamesHandler savedGamesHandler;

	public void testGetSavedGames(){
		SavedGame saved = new SavedGame();
		SavedGame saved2 = new SavedGame();
		saved = initSavedGame(saved, "Test1");
		saved2 = initSavedGame(saved2, "Test2");
		savedGamesHandler.saveGame(saved);
		savedGamesHandler.saveGame(saved2);
		assertEquals(savedGamesHandler.getSavedGames().size(),2);
	}

	public void testGetSavedGameByNameAndSaveGame(){
		SavedGame saved = new SavedGame();
		SavedGame saved2 = new SavedGame();
		saved = initSavedGame(saved, "Test1");
		savedGamesHandler.saveGame(saved);
		saved2 = savedGamesHandler.getSavedGameByName("Test1");
		assertEquals(saved2.getName(), "Test1");
		assertEquals(saved2.getStringBoard(), defaultBoard);
		assertEquals(saved2.isPrivate(), true);
		assertEquals(saved2.getScore(), 914356341);
		assertEquals(saved2.getTime(),"now");
		assertEquals(saved2.getNumberOfAttempts(), 1332434);
		assertEquals(saved2.getWordlistId(), 1);
	}
	public void testGameInDatabase(){
		SavedGame saved = new SavedGame();
		saved = initSavedGame(saved, "Test1");
		savedGamesHandler.saveGame(saved);
		assertTrue(savedGamesHandler.gameInDatabase("Test1"));
	}
	public void testRemoveGameByName(){
		SavedGame saved = new SavedGame();
		saved = initSavedGame(saved, "Test1");
		savedGamesHandler.saveGame(saved);
		assertTrue(savedGamesHandler.gameInDatabase("Test1"));
		assertTrue(savedGamesHandler.removeGameByName("Test1"));
		assertFalse(savedGamesHandler.gameInDatabase("Test1"));
		assertFalse(savedGamesHandler.removeGameByName("Test1"));
	}
	
	private SavedGame initSavedGame(SavedGame saved, String name) {
		String board = defaultBoard;
		saved.setName(name);
		saved.setStringBoard(board);
		saved.setDate("now");
		saved.setPrivate(true);
		saved.setScore(914356341);
		saved.setTime("now");
		saved.setTimesPlayed(0);
		saved.setAttempts(1332434);
		saved.setWordlistId(1);
		return saved;
	}

	public void setUp() {
		 wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		 savedGamesHandler = new SavedGamesHandler(mContext.getApplicationContext());
		android.preference.PreferenceManager.setDefaultValues(
				mContext.getApplicationContext(), R.xml.preferences, false);
		try {
			wordlistHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
