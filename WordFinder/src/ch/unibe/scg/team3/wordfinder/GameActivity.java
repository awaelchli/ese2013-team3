package ch.unibe.scg.team3.wordfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.game.AbstractGame;
import ch.unibe.scg.team3.game.Game;
import ch.unibe.scg.team3.game.IGameObserver;
import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.gameui.BoardOnTouchListener;
import ch.unibe.scg.team3.gameui.BoardUI;
import ch.unibe.scg.team3.gameui.FoundWordsView;
import ch.unibe.scg.team3.gameui.ScoreView;
import ch.unibe.scg.team3.gameui.Timer;
import ch.unibe.scg.team3.gameui.WordCounterView;
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
	private TextView countDownView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		wordlistHandler = new WordlistHandler(this);

		found = (FoundWordsView) findViewById(R.id.foundWordsField);
		scoreView = (ScoreView) findViewById(R.id.score_view);
		wordCounter = (WordCounterView) findViewById(R.id.foundCounter);
		countDownView = (TextView) findViewById(R.id.timer_field);
		boardUI = (BoardUI) findViewById(R.id.tableboardUI);

		makeLoadPreview();

		new LoadGameTask(this).execute();

	}

	private void makeLoadPreview() {

		Game empty = new Game(new Board(), wordlistHandler, 1);

		countDownView.setText("00:00");

		empty.addObserver(boardUI);
		empty.addObserver(scoreView);
		empty.addObserver(wordCounter);
		empty.addObserver(wordCounter);
		empty.addObserver(boardUI);

		empty.notifyObservers();
	}

	private void loadGame() {
		Intent intent = getIntent();
		SavedGame savedGame = (SavedGame) intent.getSerializableExtra("saved_game");

		if (savedGame != null) {
			game = new Game(savedGame, wordlistHandler);

		} else {
			int wordlistId = getSelectedWordlistId();
			game = new Game(wordlistHandler, wordlistId);

		}
	}

	private int getSelectedWordlistId() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

		String selectedWordlist = preferences.getString("choose_wordlist", null);

		int id = Integer.parseInt(selectedWordlist);
		return id;
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
	public void update(AbstractGame game) {
		if (game.isOver()) {
			finishGameSession();
		}
	}

	private class LoadGameTask extends AsyncTask<String, Void, Game> {

		private final Activity context;

		public LoadGameTask(Activity activity) {
			context = activity;
		}

		/**
		 * The board for the game gets generated in the background
		 */
		protected Game doInBackground(String... urls) {
			loadGame();
			return game;
		}

		/**
		 * Will be executed after the loading of the game is done
		 */
		protected void onPostExecute(Game result) {

			ProgressBar progress = (ProgressBar) findViewById(R.id.loadingGameBar);
			progress.setVisibility(View.GONE);

			boardUI.setOnTouchListener(new BoardOnTouchListener(context, game));

			Timer timer = new Timer(Game.TIME_LIMIT, countDownView, context) {

				@Override
				public void onFinish() {
					finishGameSession();
				}
			};

			game.setTimer(timer);

			game.addObserver(boardUI);
			game.addObserver(found);
			game.addObserver(scoreView);
			game.addObserver(wordCounter);
			game.addObserver(GameActivity.this);

			Button quit = (Button) context.findViewById(R.id.quit_button);
			quit.setEnabled(true);

			game.notifyObservers();
			game.startTime();
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
