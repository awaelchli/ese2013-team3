package ch.unibe.scg.team3.wordfinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import ch.unibe.scg.team3.localDatabase.MySQLiteHelper;
/**
 * 
 * @author nils
 * 
 */
public class PreferencesActivity extends Activity {
	MySQLiteHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		db = new MySQLiteHelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
		return true;
	}

}