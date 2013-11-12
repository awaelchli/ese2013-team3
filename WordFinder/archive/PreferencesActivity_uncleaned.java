package ch.unibe.scg.team3.wordfinder;

import java.io.IOException;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
/**
 * 
 * @author nils
 * 
 */
public class PreferencesActivity extends PreferenceActivity {
	WordlistHandler wm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		final ListPreference wordlistPref = (ListPreference) findPreference("choose_wordlist");
		//setContentView(R.layout.activity_preferences);
		wm = new WordlistHandler(this);
		
		//final Preference resetPref = (Preference) findPreference("reset");
		//final Preference copyDB = (Preference) findPreference("copyDB"); 
		
		
		setListPreferenceData(wordlistPref);
		
//		resetPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//
//		    public boolean onPreferenceClick(Preference preference) {
//		       resetDB();
//		        return true; 
//		    }
//		});
		
//		copyDB.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//
//		    public boolean onPreferenceClick(Preference preference) {
//		       copyDB();
//		        return true; 
//		    }
//		});
		
		wordlistPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

		    public boolean onPreferenceClick(Preference preference) {
		    	setListPreferenceData(wordlistPref);
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
        lp.setEntryValues(entryValues);
}

	public void resetDB(){
		wm.reset(this);
		finish();
	}
	public void copyDB(){
		try {
			wm.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finish();
	}
	

}
