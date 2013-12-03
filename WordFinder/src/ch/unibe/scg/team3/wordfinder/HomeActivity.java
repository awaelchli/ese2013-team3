package ch.unibe.scg.team3.wordfinder;

import java.io.File;
import java.io.IOException;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import ch.unibe.scg.team3.localDatabase.DataHandler;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;

/**
 * @author nils
 * @author adrian
 */
public class HomeActivity extends Activity {
	DataHandler dataHandler;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Parse.initialize(this, "ORYli0X0QqbH3Oefe0wvI2TsYa4d4Kw7sYKZFYuK",
				"FYUWqwq1E0VlFkVUXs6Fus1OZUN6CfqJo81EPbTJ");
		ParseAnalytics.trackAppOpened(getIntent());
	
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		
		File database = new File(
				"/data/data/ch.unibe.scg.team3.wordfinder/databases/localDatabase.db");

		if (!database.exists()) {
			try {
				dataHandler = new DataHandler(this);
				dataHandler.copyDB();
				

				PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
			dataHandler = new DataHandler(this);
		}
		
		ParseUser me = ParseUser.getCurrentUser();
		if(me != null){
			TextView welcomeText = (TextView) findViewById(R.id.welcome_user);
			welcomeText.setText("Welcome " + me.getUsername());
		}

	}

	public void startGame(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);

	}

	public void startPreferences(View view) {
		Intent intent = new Intent(this, PreferencesActivity.class);
		startActivity(intent);

	}

	public void startSavedGames(View view) {
		Intent intent = new Intent(this, SavedGamesActivity.class);
		startActivity(intent);
	}
	
	public void openFriends(View view){
		ParseUser me = ParseUser.getCurrentUser();
		
		if(me != null){
			Intent intent = new Intent(this, FriendsActivity.class);
			startActivity(intent);
		} else {
			Toast toast = Toast.makeText(this, "You are not logged in. Please sign up or log in.", Toast.LENGTH_LONG);
			toast.show();
		}
	}

	public void quitApp(View view) {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onStop();
		//dataHandler.closeDB();
	}
	
	/**
	 * BugFixing : Deactivates Button.
	 */
	@Override
	public void onBackPressed() {
	}
	
}
