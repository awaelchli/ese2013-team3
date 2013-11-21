package ch.unibe.scg.team3.wordfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import ch.unibe.scg.team3.game.*;
import ch.unibe.scg.team3.gameui.BoardUI;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;

public class ViewSavedGameActivity extends Activity {

	private SavedGame savedGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_saved_game);

		Intent intent = getIntent();
		savedGame = (SavedGame) intent.getSerializableExtra("saved_game");
		WordlistHandler wh = new WordlistHandler(this);
		BoardUI boardUI = (BoardUI) findViewById(R.id.tableboardUI);
		boardUI.update(savedGame, new Event(Event.BOARD_UPDATED));

		String labels = "Title: %s\nLast played: %s\nFound words: %s\nAttempts: %s\nElapsed time: %s\nScore: %s\nWordlist: %s";

		String text = String.format(labels, savedGame.getName(), savedGame.getDate(),
				savedGame.getNumberOfFoundWords(), savedGame.getNumberOfAttempts(),
				Timer.format(savedGame.getRemainingTime()), savedGame.getScore(),
				wh.getWordlistNameById(savedGame.getWordlistId()));

		TextView gameOverview = (TextView) findViewById(R.id.game_overview_text);
		gameOverview.setText(text);
	}

	public void replaySavedGame(View view) {
		Intent intent = getIntent();
		intent.setClass(this, GameActivity.class);
		startActivity(intent);
		finish();
	}

}
