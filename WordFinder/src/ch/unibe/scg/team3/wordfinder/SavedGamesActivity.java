package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.localDatabase.SavedGamesHandler;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class SavedGamesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_games);

		SavedGamesHandler handler = new SavedGamesHandler(this);
		ArrayList<SavedGame> games = handler.getSavedGames();

		ArrayAdapter<SavedGame> adapter = new ArrayAdapter<SavedGame>(this,
				R.layout.saved_games_list_item, games) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				View element = inflater.inflate(R.layout.saved_games_list_item, parent, false);
				
				TextView name = (TextView) element.findViewById(R.id.saved_game_name);
				TextView score = (TextView) element.findViewById(R.id.saved_game_score);
				TextView date = (TextView) element.findViewById(R.id.saved_game_date);
				
				SavedGame game = getItem(position);
				
				name.setText(game.getName());
				score.setText("Score: " + game.getScore());
				date.setText("Date: " + game.getDate());
				return element;
			}

		};

		ListView list = (ListView) findViewById(R.id.saved_Games_list);
		list.setAdapter(adapter);
	}
	public void viewSavedGame(View view){
		SavedGamesHandler handler = new SavedGamesHandler(this);
		TextView textView = (TextView) view.findViewById(R.id.saved_game_name);
		String gameName = textView.getText().toString();
		SavedGame savedGame = handler.getSavedGameByName(gameName);
		Intent intent = new Intent(this, ViewSavedGameActivity.class);
		
		intent.putExtra("saved_game", savedGame);
		startActivity(intent);
	}
}
