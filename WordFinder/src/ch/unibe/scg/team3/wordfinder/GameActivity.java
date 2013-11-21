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
	public static Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		activity = this;
		wordlistHandler = new WordlistHandler(this);

		found = (FoundWordsView) findViewById(R.id.foundWordsField);
		scoreView = (ScoreView) findViewById(R.id.score_view);
		wordCounter = (WordCounterView) findViewById(R.id.foundCounter);
		countDownView = (CountDownView) findViewById(R.id.timer_field);
		boardUI = (BoardUI) findViewById(R.id.tableboardUI);

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (game == null) {
			loadGame();

			game.addObserver(boardUI);
			game.addObserver(found);
			game.addObserver(scoreView);
			game.addObserver(wordCounter);
			game.addObserver(countDownView);
			game.addObserver(this);
			
			boardUI.setOnTouchListener(new BoardOnTouchListener(this, game));

			game.notifyObservers(new Event(Event.BOARD_UPDATED));

		} else {
			game.startTime();
		}


		// Timer timer = new Timer(Game.TIME_LIMIT, countDownView, this) {
		//
		// @Override
		// public void onFinish() {
		// finishGameSession();
		// }
		// };
		//
		// game.setTimer(timer);

		//
		// if(game!= null){
		// if(game.remainigTime != Game.TIME_LIMIT){
		// Timer timer = new Timer(game.remainigTime, countDownView, this) {
		// @Override
		// public void onFinish() {
		// finishGameSession();
		// }
		// };
		// game.setTimer(timer);
		// game.startTime();
		// }
		//
		// }

	}

	private void loadGame() {
		Intent intent = getIntent();
		SavedGame savedGame = (SavedGame) intent.getSerializableExtra("saved_game");

		if (savedGame != null) {
			game = new Game(savedGame, wordlistHandler);
			update(game, new Event(Event.BOARD_CREATED));
//			game.startTime();
		} else {
			int wordlistId = getSelectedWordlistId();
			game = new Game(wordlistHandler, wordlistId);

		}
	}

	public void quit(View view) {
		finishGameSession();
	}

	public void pause(View view) {
		pauseGameSession();
	}

	public void finishGameSession() {
		game.stopTime();

		SavedGame savedGame = game.save();
		Intent intent = new Intent(this, EndGameActivity.class);
		intent.putExtra("saved_game", savedGame);

		startActivity(intent);
		finish();
	}

	public void pauseGameSession() {
		game.pauseTime();
		SavedGame savedGame = game.save();
		Intent intent = new Intent(this, EndGameActivity.class);
		intent.putExtra("saved_game", savedGame);
		startActivity(intent);
	}

	@Override
	public void update(AbstractGame game, Event event) {

		switch (event.getAction()) {

		case Event.BOARD_CREATED:
			hideProgressBar();
			enableNavigationButtons();
			this.game.startTime();
			break;
		case Event.GAME_OVER:
			finishGameSession();
			break;
		}
	}

	private void enableNavigationButtons() {
		Button quit = (Button) findViewById(R.id.quit_button);
		Button pause = (Button) findViewById(R.id.pause_button);
		quit.setEnabled(true);
		pause.setEnabled(true);
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

	@Override
	protected void onPause() {
		super.onPause();
		game.pauseTime();
	}

}
