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

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		WordlistManager data = new WordlistManager(this);
		game = new Game(data);

		BoardUI boardUI = (BoardUI) findViewById(R.id.tableboardUI);
		
		boardUI.setOnTouchListener(new BoardOnTouchListener(this, game));
		
//		Board board = game.getBoard();
//		board.addObserver(boardUI);
		
		game.assignBoardObserver(boardUI);
//		board.notifyObserver();
		
		FoundWordsView found = (FoundWordsView) findViewById(R.id.foundWordsField);
		//System.out.println(found == null);
		game.assignFoundListObserver(found);
		
		TextView timerDisplayer = (TextView) findViewById(R.id.timer_field);
        //@param Minuten, Interval in seconds, TextFeld to display timer
        Timer timer = new Timer(5, 1, timerDisplayer);
        timer.start();
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}

	// private void setLetters() {
	// Board board = game.getBoard();
	// for (int i = 0;i < board.getSize(); i++) {
	// for (int j = 0; j < board.getSize(); j++) {
	// String button = "button".concat(Integer.toString((i)*6+j+1));
	// int id = getResources().getIdentifier(button, "id",
	// this.getPackageName());
	// SquareField field = (SquareField)findViewById(id);
	//
	// field.setText("" + board.getToken(j, i).getLetter());
	// }
	// }
	// }

	public void quit(View view) {

		Intent intent = new Intent(this, EndGameActivity.class);
		startActivity(intent);
		finish();
	}
	// public void submitPath(List<Point> walked_coordinates, List<View> walked)
	// {
	//
	// this.walked = walked;
	// try {
	// game.submitWord(walked_coordinates);
	// this.setBackgroundColor(R.drawable.buttonlayout_valid);
	// } catch (SelectionException s) {
	// if (s.isPathNotConnected() || s.isWordNotFound()) {
	// this.setBackgroundColor(R.drawable.buttonlayout_invalid);
	// } else if (s.isWordAlreadyFound()) {
	// this.setBackgroundColor(R.drawable.buttonlayout_already);
	// }
	// }
	// }
	//
	// @SuppressWarnings("deprecation")
	// private void setBackgroundColor(int layout) {
	//
	// for (int i = 0; i < walked.size(); i++) {
	// this.walked.get(i).setBackgroundDrawable(
	// getResources().getDrawable(layout));
	// }
	// Thread mythread = new Thread(runnable);
	// mythread.start();
	// }
	//
	// @SuppressLint("HandlerLeak")
	// Runnable runnable = new Runnable() {
	//
	// public void run() {
	// synchronized (this) {
	// try {
	// wait(500);
	// } catch (Exception e) {
	// }
	// }
	// handler.sendEmptyMessage(0);
	// }
	// };
	//
	// Handler handler = new Handler() {
	//
	// @SuppressWarnings("deprecation")
	// @Override
	// public void handleMessage(Message msg) {
	// for (int i = 0; i < walked.size(); i++) {
	// walked.get(i).setBackgroundDrawable(
	// getResources().getDrawable(R.drawable.buttonlayout));
	// }
	// }
	// };
}
