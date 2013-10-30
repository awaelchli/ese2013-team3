package ch.unibe.scg.team3.wordfinder;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import ch.unibe.scg.team3.localDatabase.MySQLiteHelper;
import ch.unibe.scg.team3.localDatabase.WordlistManager;

/**
 * 
 * @author nils
 * 
 */
public class HomeActivity extends Activity {
	WordlistManager wordlistmanager;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);   
        wordlistmanager = new WordlistManager(this);
        wordlistmanager.getWordlistids();
        //TODO prüfen ob schon vorhanden
        if(!(new File("/data/data/ch.unibe.scg.team3.wordfinder/databases/localDatabase.db").exists())){
	        try {
				wordlistmanager.copyDB();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public void startGame(View view){
    	//Intent intent = new Intent(this, GameActivity.class);
    	Intent intent = new Intent(this, GameActivity.class);
    	startActivity(intent);
    }
    
    public void startPreferences(View view){
    	Intent intent = new Intent(this, PreferencesActivity.class);
    	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
}
