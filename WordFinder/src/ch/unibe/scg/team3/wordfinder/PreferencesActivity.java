package ch.unibe.scg.team3.wordfinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
/**
 * 
 * @author nils
 * 
 */
public class PreferencesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
		return true;
	}

}
