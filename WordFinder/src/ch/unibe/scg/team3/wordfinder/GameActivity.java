package ch.unibe.scg.team3.wordfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import ch.unibe.scg.team3.game.*;
import ch.unibe.scg.team3.gameui.*;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;

/**
 * @author faerber
 * @author adrian
 * @author nils
 */

public class GameActivity extends Activity implements IGameObserver {

	private Game game;
	private WordlistHandler wordlistHandler;

	private BoardUI boardUI;
	private FoundWordsView found;
	private ScoreView scoreView;
	private WordCounterView wordCounter;
	private CountDownView countDownView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		wordlistHandler = new WordlistHandler(this);

		found = (FoundWordsView) findViewById(R.id.foundWordsField);
		scoreView = (ScoreView) findViewById(R.id.score_view);
		wordCounter = (WordCounterView) findViewById(R.id.foundCounter);
		countDownView = (CountDownView) findViewById(R.id.timer_field);
		boardUI = (BoardUI) findViewById(R.id.tableboardUI);

		loadGame();

		boardUI.setOnTouchListener(new BoardOnTouchListener(this, game));

//		Timer timer = new Timer(Game.TIME_LIMIT, countDownView, this) {
//
//			@Override
//			public void onFinish() {
//				finishGameSession();
//			}
//		};
//
//		game.setTimer(timer);

		game.addObserver(boardUI);
		game.addObserver(found);
		game.addObserver(scoreView);
		game.addObserver(wordCounter);
		game.addObserver(countDownView);
		game.addObserver(this);
		
		game.notifyObservers(new Event(Event.BOARD_UPDATED));

	}

	private void loadGame() {
		Intent intent = getIntent();
		SavedGame savedGame = (SavedGame) intent.getSerializableExtra("saved_game");

		if (savedGame != null) {
			game = new Game(savedGame, wordlistHandler);
//			update(game, new Event(Event.BOARD_CREATED));
		} else {
			int wordlistId = getSelectedWordlistId();
			game = new Game(wordlistHandler, wordlistId);
		}
	}

	public void quit(View view) {
		finishGameSession();
	}

	public void finishGameSession() {
		game.stopTime();

		SavedGame savedGame = game.save();
		Intent intent = new Intent(this, EndGameActivity.class);
		intent.putExtra("saved_game", savedGame);

		startActivity(intent);
		finish();
	}

	@Override
	public void update(AbstractGame game, Event event) {

		switch (event.getAction()) {

		case Event.BOARD_CREATED:
			hideProgressBar();
			enableQuitButton();
			this.game.startTime();
			break;
		case Event.GAME_OVER:
			finishGameSession();
			break;
		}
	}

	private void enableQuitButton() {
		Button quit = (Button) findViewById(R.id.quit_button);
		quit.setEnabled(true);
	}

	private void hideProgressBar() {
		ProgressBar progress = (ProgressBar) findViewById(R.id.loadingGameBar);
		progress.setVisibility(View.GONE);
	}
	
	private int getSelectedWordlistId() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

		String selectedWordlist = preferences.getString("choose_wordlist", null);

		int id = Integer.parseInt(selectedWordlist);
		return id;
	}

}
