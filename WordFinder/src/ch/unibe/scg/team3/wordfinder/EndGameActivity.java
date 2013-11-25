package ch.unibe.scg.team3.wordfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.game.Timer;
import ch.unibe.scg.team3.localDatabase.SavedGamesHandler;

/**
 * @author lukas
 * @author nils
 */

public class EndGameActivity extends Activity {

	protected SavedGamesHandler handler;
	private SavedGame game;
	protected String board;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);

		handler = new SavedGamesHandler(this.getApplicationContext());

		Intent intent = getIntent();

		long id = intent.getLongExtra("saved_game_id", -1);

		if (id >= 0) {
			game = handler.getSavedGame(id);
			displayGameStats();
			Button replay = (Button) findViewById(R.id.replay_button);
			Button save = (Button) findViewById(R.id.save_button);
			
			replay.setVisibility(View.VISIBLE);
			save.setVisibility(View.VISIBLE);
			
		} else {
			TextView header = (TextView) findViewById(R.id.end_of_game_title);
			Button resume = (Button) findViewById(R.id.resume_button);
			
			header.setText("Pause");
			resume.setVisibility(View.VISIBLE);
		}
	}

	public void newGame(View view) {
		GameActivity.instance.finish();
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
		finish();
	}

	public void replayGame(View view) {
		GameActivity.instance.finish();
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("saved_game", game);
		startActivity(intent);
		finish();
	}

	public void resumeGame(View view) {
		finish();
	}

	public void goHome(View view) {
		GameActivity.instance.finish();
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	public void enterTitle(final View view) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Save Game");
		alert.setMessage("Please enter a title for your game.");

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				game.setName(value);
				if (handler.saveGame(game) >= 0) {
					goHome(null);
				} else
					reenterTitle(view);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();
	}

	public void reenterTitle(final View view) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Game already in Database");
		alert.setMessage("Please choose another Title for your game.");

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				game.setName(value);
				if (handler.saveGame(game)>= 0) {
					goHome(null);
				} else
					enterTitle(view);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();
	}

	private void displayGameStats() {
		String labels = "Your Score: %s\nFound Words: %s\nAttempts Words: %s\nElapsed Time: %s\n";

		String text = String.format(labels, game.getScore(), game.getNumberOfFoundWords(),
				game.getNumberOfAttempts(), Timer.format(game.getRemainingTime()));

		TextView stats = (TextView) findViewById(R.id.display_Stats);
		stats.setText(text);
	}
}
