package ch.unibe.scg.team3.wordfinder;

import java.io.File;
import java.io.IOException;

import com.parse.Parse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;

/**
 * @author nils
 * @author adrian
 */
public class HomeActivity extends Activity {
	WordlistHandler wordlistHandler;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  
        //Parse.initialize(this, "ORYli0X0QqbH3Oefe0wvI2TsYa4d4Kw7sYKZFYuK", "FYUWqwq1E0VlFkVUXs6Fus1OZUN6CfqJo81EPbTJ");
    }
    
    public void startGame(View view){
    	Intent intent = new Intent(this, GameActivity.class);
    	startActivity(intent);
    }
    
    public void startPreferences(View view){
    	Intent intent = new Intent(this, PreferencesActivity.class);
    	startActivity(intent);
    }
    
    public void startSavedGames(View view){
    	Intent intent = new Intent(this, SavedGamesActivity.class);
    	startActivity(intent);
    }
    public void quitApp(View view){
    	finish();
    }
    

	@Override
    protected void onResume() {
        super.onResume();
       
        wordlistHandler = new WordlistHandler(this);
        File database = new File("/data/data/ch.unibe.scg.team3.wordfinder/databases/localDatabase.db");
        
        if(!database.exists()){
	        try {
	        	
				wordlistHandler.copyDB();
				wordlistHandler = new WordlistHandler(this);

				PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
    }
    
    
}
