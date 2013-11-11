package ch.unibe.scg.team3.wordfinder;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.gameui.BoardUI;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class ViewSavedGameActivity extends Activity {

	private SavedGame savedGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_saved_game);

		Intent intent = getIntent();
		savedGame = (SavedGame) intent.getSerializableExtra("saved_game");

		BoardUI boardUI = (BoardUI) findViewById(R.id.tableboardUI);
		boardUI.update(savedGame);

		String labels = "Title: %s\nLast played: %s\nFound words: %s\nGuessed words: %s\nElapsed time: %s\nScore: %s";
		
		String text = String.format(labels, savedGame.getName(), savedGame.getDate(),
				savedGame.getNumberOfFoundWords(), savedGame.getNumberOfGuesses(),
				savedGame.getTime(), savedGame.getScore());

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