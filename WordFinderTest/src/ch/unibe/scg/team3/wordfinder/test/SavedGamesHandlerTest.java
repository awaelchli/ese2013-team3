package ch.unibe.scg.team3.wordfinder.test;

import java.io.IOException;

import android.test.AndroidTestCase;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.localDatabase.SavedGamesHandler;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.wordfinder.R;

public class SavedGamesHandlerTest extends AndroidTestCase implements
		IDataHandlerTest {

	protected String defaultBoard = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	protected WordlistHandler wordlistHandler;
	protected SavedGamesHandler savedGamesHandler;

	public void testGetSavedGames() {
		SavedGame saved = new SavedGame();
		SavedGame saved2 = new SavedGame();
		saved = initSavedGame(saved, "Test1");
		saved2 = initSavedGame(saved2, "Test2");
		savedGamesHandler.saveGame(saved);
		savedGamesHandler.saveGame(saved2);
		assertEquals(savedGamesHandler.getSavedGames().size(), 2);
	}

	public void testGetSavedGameAndSaveGame() {
		SavedGame saved = new SavedGame();
		SavedGame saved2 = new SavedGame();
		SavedGame saved3 = new SavedGame();
		saved = initSavedGame(saved, "Test1");
		long temp = savedGamesHandler.saveGame(saved);
		saved2 = savedGamesHandler.getSavedGame("Test1");
		saved3 = savedGamesHandler.getSavedGame(temp);

		assertEquals(saved2.getName(), "Test1");
		assertEquals(saved2.getStringBoard(), defaultBoard);
		assertEquals(saved2.isPersonal(), true);
		assertEquals(saved2.getScore(), 914356341);
		assertEquals(saved2.getRemainingTime(), 1111);
		assertEquals(saved2.getNumberOfAttempts(), 1332434);
		assertEquals(saved2.getWordlistId(), 1);

		assertEquals(saved3.getName(), "Test1");
		assertEquals(saved3.getStringBoard(), defaultBoard);
		assertEquals(saved3.isPersonal(), true);
		assertEquals(saved3.getScore(), 914356341);
		assertEquals(saved3.getRemainingTime(), 1111);
		assertEquals(saved3.getNumberOfAttempts(), 1332434);
		assertEquals(saved3.getWordlistId(), 1);
	}

	public void testGameInDatabase() {
		SavedGame saved = new SavedGame();
		saved = initSavedGame(saved, "Test1");
		long temp = savedGamesHandler.saveGame(saved);
		assertTrue(savedGamesHandler.gameInDatabase("Test1"));
		assertTrue(savedGamesHandler.gameInDatabase(temp));
	}

	public void testRemove() {
		SavedGame saved = new SavedGame();
		SavedGame saved2 = new SavedGame();
		saved = initSavedGame(saved, "Test1");
		saved2 = initSavedGame(saved2, "Test2");
		savedGamesHandler.saveGame(saved);
		long temp = savedGamesHandler.saveGame(saved2);
		assertTrue(savedGamesHandler.gameInDatabase("Test1"));
		assertTrue(savedGamesHandler.gameInDatabase(temp));
		assertTrue(savedGamesHandler.removeGame("Test1"));
		assertTrue(savedGamesHandler.removeGame(temp));
		assertFalse(savedGamesHandler.gameInDatabase("Test1"));
		assertFalse(savedGamesHandler.removeGame("Test1"));
		assertFalse(savedGamesHandler.gameInDatabase(temp));
		assertFalse(savedGamesHandler.removeGame(temp));
	}
	

	public void testSetIsPrivate() {
		SavedGame saved = new SavedGame();
		SavedGame saved2 = new SavedGame();
		initSavedGame(saved, "Test1");
		long temp = savedGamesHandler.saveGame(saved);
		saved2 = savedGamesHandler.getSavedGame(temp);
		assertEquals(saved2.isPersonal(),true);
		savedGamesHandler.setIsPrivate(temp, false);
		saved2 = savedGamesHandler.getSavedGame(temp);
		assertEquals(saved2.isPersonal(),false);
		savedGamesHandler.setIsPrivate(temp, true);
		saved2 = savedGamesHandler.getSavedGame(temp);
		assertEquals(saved2.isPersonal(),true);
	}

	public void testSetName() {
		SavedGame saved = new SavedGame();
		SavedGame saved2 = new SavedGame();
		initSavedGame(saved, "Test1");
		long temp = savedGamesHandler.saveGame(saved);
		saved2 = savedGamesHandler.getSavedGame(temp);
		assertEquals(saved2.getName(),"Test1");
		savedGamesHandler.setName(temp, "foo");
		saved2 = savedGamesHandler.getSavedGame(temp);
		assertEquals(saved2.getName(),"foo");

	}
	private SavedGame initSavedGame(SavedGame saved, String name) {
		String board = defaultBoard;
		saved.setName(name);
		saved.setStringBoard(board);
		saved.setDate("now");
		saved.setPersonal(true);
		saved.setScore(914356341);
		saved.setRemainingTime(1111);
		saved.setTimesPlayed(0);
		saved.setAttempts(1332434);
		saved.setWordlistId(1);
		return saved;
	}
	
	public void setUp() {
		wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		savedGamesHandler = new SavedGamesHandler(
				mContext.getApplicationContext());
		android.preference.PreferenceManager.setDefaultValues(
				mContext.getApplicationContext(), R.xml.preferences, false);
		try {
			wordlistHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
