package ch.unibe.scg.team3.wordfinder;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import ch.unibe.scg.team3.localDatabase.MainUserHandler;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;

/**
 * 
 * @author nils
 * 
 */
public class PreferencesActivity extends PreferenceActivity {
	WordlistHandler wm;
	MainUserHandler muh;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
		final ListPreference wordlistPref = (ListPreference) findPreference("choose_wordlist");
		final Preference signUp = (Preference) findPreference("signUp");
		final Preference login = (Preference) findPreference("login");
		wm = new WordlistHandler(this);
		muh = new MainUserHandler(this);
		setListPreferenceData(wordlistPref);

		wordlistPref
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					public boolean onPreferenceClick(Preference preference) {
						setListPreferenceData(wordlistPref);
						return true;
					}
				});
		signUp.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			public boolean onPreferenceClick(Preference preference) {
				shareActivity();
				return true;
			}
		});
		
		login.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			public boolean onPreferenceClick(Preference preference) {
				loginActivity();
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
	protected void shareActivity(){
		Intent intent = new Intent(this,SignUpActivity.class);
		startActivity(intent);
		finish();
	}
	protected void loginActivity(){
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
