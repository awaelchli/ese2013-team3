package ch.unibe.scg.team3.wordfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import ch.unibe.scg.team3.localDatabase.SavedGamesHandler;

/**
 * @author lukas
 * @author nils
 */

public class EndGameActivity extends Activity {

	protected SavedGamesHandler handler;
	protected String board;
	protected int score;
	protected String time;
	protected int guesses;
	protected int found;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);
		this.handler = new SavedGamesHandler(this.getApplicationContext());
		Intent intent = getIntent();

		this.score = intent.getIntExtra("score", 0);
		this.found = intent.getIntExtra("words_found", 0);
		this.board = intent.getStringExtra("board");
		this.time = intent.getStringExtra("time");
		this.guesses = intent.getIntExtra("guesses", 0);

		String text = "Your Score: " + score + "\n\n" + "Found Words: " + found
				+ "\n\n" + "Elapsed time: " + time;

		TextView stats = (TextView) findViewById(R.id.display_Stats);
		stats.setText(text);
	}

	public void startGame(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
		finish();
	}

	public void goHome(View view) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	public void enterTitle(View view) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Save Game");
		alert.setMessage("Please enter a title for your game.");

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				handler.saveGame(value, board, found, time, score, true,
						guesses);
				goHome(null);
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}

	public void saveGame() {

	}

}
