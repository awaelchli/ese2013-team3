package ch.unibe.scg.team3.wordfinder;

import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;
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
	private Preference login;
	private Preference logout;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
		final ListPreference wordlistPref = (ListPreference) findPreference("choose_wordlist");
		final Preference signUp = (Preference) findPreference("signUp");
		login = (Preference) findPreference("login");
		logout = (Preference) findPreference("logout");
		wm = new WordlistHandler(this);
		muh = new MainUserHandler(this);
		setListPreferenceData(wordlistPref);
		if(ParseUser.getCurrentUser() == null){
			login.setEnabled(true);
		}else{
			logout.setEnabled(true);
		}

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
		
		logout.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			public boolean onPreferenceClick(Preference preference) {
				try {
					ParseUser.logOut();
					login.setEnabled(true);
					logout.setEnabled(false);
					Toast toast = Toast.makeText(getApplicationContext(), "You are logged out now ", Toast.LENGTH_LONG);
					toast.show();
				} catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(), "Logout not successful ", Toast.LENGTH_LONG);
					toast.show();
					e.printStackTrace();
					return false;
				}
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
		//finish();
	}
	protected void loginActivity(){
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
		//finish();
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		if(ParseUser.getCurrentUser() == null){
			login.setEnabled(true);
			logout.setEnabled(false);
		}else{
			logout.setEnabled(true);
			login.setEnabled(false);
		}
	}

	
	

}
