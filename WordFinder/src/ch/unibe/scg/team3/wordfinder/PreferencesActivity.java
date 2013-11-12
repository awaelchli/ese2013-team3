package ch.unibe.scg.team3.wordfinder;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;

/**
 * 
 * @author nils
 * 
 */
public class PreferencesActivity extends PreferenceActivity {
	WordlistHandler wm;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
		final ListPreference wordlistPref = (ListPreference) findPreference("choose_wordlist");

		wm = new WordlistHandler(this);
		setListPreferenceData(wordlistPref);

		wordlistPref
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					public boolean onPreferenceClick(Preference preference) {
						setListPreferenceData(wordlistPref);
						return true;
					}
				});

	}

	protected void setListPreferenceData(ListPreference lp) {
		CharSequence[] entries;
		CharSequence[] entryValues;
		entries = wm.getWordlists();
		entryValues = wm.getWordlistIds();
		lp.setEntries(entries);
		lp.setEntryValues(entryValues);
	}

}
