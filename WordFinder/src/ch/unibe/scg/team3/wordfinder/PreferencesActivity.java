package ch.unibe.scg.team3.wordfinder;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import ch.unibe.scg.team3.localDatabase.DataManager;
import ch.unibe.scg.team3.localDatabase.WordlistManager;
/**
 * 
 * @author nils
 * 
 */
public class PreferencesActivity extends PreferenceActivity {
	WordlistManager wm;
	String options[] = {};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		//setContentView(R.layout.activity_preferences);
		wm = new WordlistManager(this);
		
		final Preference pref = (Preference) findPreference("reset"); 
		final ListPreference listPreference = (ListPreference) findPreference("choose_wordlist");
		setListPreferenceData(listPreference);
		pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

		    public boolean onPreferenceClick(Preference preference) {
		       resetDB();
		        return true; 
		    }
		});
		
		listPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

		    public boolean onPreferenceClick(Preference preference) {
		    	setListPreferenceData(listPreference);
		        return true; 
		    }
		});
		
//		db.getWritableDatabase();
//		db.close();
	}
	protected void setListPreferenceData(ListPreference lp) {
        CharSequence[] entries;
        CharSequence[] entryValues;
        entries = wm.getWordlists();
        entryValues = wm.getWordlistids();
        lp.setEntries(entries);
        lp.setDefaultValue("1");
        lp.setEntryValues(entryValues);
}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
		return true;
	}
	public void resetDB(){
		wm.reset(this);
		finish();
	}

}
