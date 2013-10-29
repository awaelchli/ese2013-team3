package ch.unibe.scg.team3.wordfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
/**
 * @author lukas
*/

public class EndGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);
		
		Intent intent = getIntent();
		
		int score = intent.getIntExtra("score", 0);
		int found = intent.getIntExtra("words_found", 0);
		
		String text = "Your Score: " + score + "\n\n" + "Found Words: " + found;
		
		TextView stats = (TextView) findViewById(R.id.display_Stats);
		stats.setText(text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.end_game, menu);
		return true;
	}
	
	public void startGame(View view){
    	Intent intent = new Intent(this, GameActivity.class);
    	startActivity(intent);
    	finish();
    }
    public void goHome(View view){
    	Intent intent = new Intent(this, HomeActivity.class);
    	startActivity(intent);
    	finish();
    }

}
