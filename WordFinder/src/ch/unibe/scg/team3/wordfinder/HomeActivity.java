package ch.unibe.scg.team3.wordfinder;


import ch.unibe.scg.team3.localDatabase.MySQLiteHelper;
import ch.unibe.scg.team3.localDatabase.Wordlist;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * 
 * @author nils
 * 
 */
public class HomeActivity extends Activity {
	MySQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);   
        db = new MySQLiteHelper(this);
        resetdb();
        
    }
    
    public void startGame(View view){
    	
         Wordlist test = new Wordlist("test", null);
         test.setWordlistFromString("bla,bla,bla");
         db.addWordlist(test);
         
    	Intent intent = new Intent(this, GameActivity.class);
    	intent.putExtra("wordlist", db.getAllWordlists().get(0));
    	startActivity(intent);
    }
    public void startPreferences(View view){
    	Intent intent = new Intent(this, PreferencesActivity.class);
    	startActivity(intent);
    }
    public void resetdb(){
    	db.reset();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
}
