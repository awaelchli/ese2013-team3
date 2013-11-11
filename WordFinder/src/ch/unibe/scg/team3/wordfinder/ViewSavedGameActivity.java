package ch.unibe.scg.team3.wordfinder;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.gameui.BoardUI;
import ch.unibe.scg.team3.gameui.CountDownView;
import ch.unibe.scg.team3.gameui.FoundWordsView;
import ch.unibe.scg.team3.gameui.ScoreView;
import ch.unibe.scg.team3.gameui.WordCounterView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ViewSavedGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_saved_game);
		
		Intent intent = getIntent();
		SavedGame savedGame = (SavedGame) intent.getSerializableExtra("saved_game");
		savedGame.setStringBoard(savedGame.getStringBoard());
		
		BoardUI boardUI = (BoardUI) findViewById(R.id.tableboardUI);
		boardUI.update(savedGame);
		String text = "Game Title    : " + savedGame.getName() +"\n" +
					  "Last Played   : " + savedGame.getDate() + "\n" +
				      "Found Words   : " + savedGame.getNumberOfFoundWords() +"\n" +
				      "Guessed Words : " + savedGame.getNumberOfGuesses() +"\n" +
				      "Elapsed Time  : " + savedGame.getTime() + "\n" +
				      "Gained Score  : " + savedGame.getScore() +"\n";
		TextView gameOverview = (TextView) findViewById(R.id.game_overview_text);
		gameOverview.setText(text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_saved_game, menu);
		return true;
	}

}
