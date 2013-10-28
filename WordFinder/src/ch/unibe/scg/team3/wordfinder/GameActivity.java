package ch.unibe.scg.team3.wordfinder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.game.Game;
import ch.unibe.scg.team3.gameui.BoardOnTouchListener;
import ch.unibe.scg.team3.gameui.BoardUI;
import ch.unibe.scg.team3.gameui.FoundWordsView;
import ch.unibe.scg.team3.gameui.ScoreView;
import ch.unibe.scg.team3.localDatabase.DataManager;
import ch.unibe.scg.team3.localDatabase.WordlistManager;

/**
 * @author faerber
 * @author adrian
 * @author nils
 */

@SuppressLint("NewApi")
public class GameActivity extends Activity {

	private Game game;
	private Timer timer;
	private long remainingTime;
	private TextView timerDisplayer; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		WordlistManager data = new WordlistManager(this);
		game = new Game(data);

		BoardUI boardUI = (BoardUI) findViewById(R.id.tableboardUI);
		FoundWordsView found = (FoundWordsView) findViewById(R.id.foundWordsField);
		ScoreView scoreView = (ScoreView) findViewById(R.id.score_view);
		
		boardUI.setOnTouchListener(new BoardOnTouchListener(this, game));

		
		game.addObserver(boardUI);
		game.addObserver(found);
		game.addObserver(scoreView);
		
		game.notifyObservers();
		
        //@param Minuten, Interval in seconds, TextFeld to display timer
		timerDisplayer = (TextView) findViewById(R.id.timer_field);
		timer = new Timer(5*60000, 1000, timerDisplayer);
        timer.start();
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		remainingTime = timer.getRemainingTime();
		timer.cancel();
	}

	@Override
	protected void onResume() {
		super.onResume();
		timer = new Timer(remainingTime, 1000, timerDisplayer);
        timer.start();
	}

	public void quit(View view) {

		Intent intent = new Intent(this, EndGameActivity.class);
		startActivity(intent);
		finish();
	}
}
