package ch.unibe.scg.team3.wordfinder.test;

import java.io.IOException;

import com.parse.Parse;
import com.parse.ParseAnalytics;

import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.game.AbstractGame;
import ch.unibe.scg.team3.game.Event;
import ch.unibe.scg.team3.game.Game;
import ch.unibe.scg.team3.game.IGameObserver;
import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.wordfinder.R;
import android.test.AndroidTestCase;

public class GameTest extends AndroidTestCase implements IGameObserver{

	private WordlistHandler wordlistHandler;
	private boolean boardGenerated;

	public void setUp() {
		wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		try {
			wordlistHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		android.preference.PreferenceManager.setDefaultValues(mContext.getApplicationContext(),
				R.xml.preferences, false);
		
		Parse.initialize(mContext, "ORYli0X0QqbH3Oefe0wvI2TsYa4d4Kw7sYKZFYuK",
				"FYUWqwq1E0VlFkVUXs6Fus1OZUN6CfqJo81EPbTJ");
		

		
	}

	public void testInitGameOfDefaultSize() {

		Game game = new Game(wordlistHandler, 1);
		game.addObserver(this);
		while(!boardGenerated){
			try {
				wait(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		boardGenerated = false;
		assertEquals(game.getBoardSize(), 6);
		game.stopTime();
		
	}
	

	public void testInitGameFromSavedGame() {

		SavedGame saved = new SavedGame();

		saved.setAttempts(3);
		saved.setRemainingTime(1001);
		saved.setTimesPlayed(4);
		saved.setScore(100);
		saved.setWordlistId(2);
		String stringBoard = "abcdefghi";
		saved.setStringBoard(stringBoard);

		Game game = new Game(saved, wordlistHandler);

		assertEquals(game.getBoardSize(), 3);
		assertEquals(game.getNumberOfAttempts(), 0);
//		assertEquals(game.getTime(), "00:00");
		assertEquals(game.getTimesPlayed(), 4);
		assertEquals(game.getScore(), 0);
		assertEquals(game.getWordlistId(), 2);

		Board board = game.getBoard();

		assertEquals(board.toString(), stringBoard);
		game.stopTime();
		
	}

	public void testStartStopTime(){
//		Game game = new Game(wordlistHandler, 0);
//		
//		game.startTime();
//		
//		long end = System.currentTimeMillis() + 1000;
//		while(System.currentTimeMillis() < end );
//		
//		assertEquals(game.getTime(), "4:59");
//		
//		game.stopTime();
//		assertTrue(game.isOver());
	}

	@Override
	public void update(AbstractGame game, Event event) {
		if(event.getAction() == event.BOARD_CREATED){
			boardGenerated = true;
		}
	}
}
