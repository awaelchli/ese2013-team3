package ch.unibe.scg.team3.wordfinder;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.game.GameManager;
import ch.unibe.scg.team3.game.Point;
import ch.unibe.scg.team3.game.SelectionException;

/**
 * @author faerber
 * @author adrian
 * @author nils
 */

@SuppressLint("NewApi")
public class GridActivity extends Activity {
	
	private List<View> walked;
	/**
	 * Adapt this value in the onCreate() method if your fingers are too fat!
	 */
	private int finger_padding;
	private GameManager manager;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ViewGroup board = (ViewGroup)findViewById(R.id.tableBoard);
        this.manager = new GameManager(6,this);
        this.finger_padding = 20;
        board.setOnTouchListener(new BoardOnTouchListener(this, this.finger_padding));
        this.setLetters();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}
	
	private void setLetters() {
		Board board = manager.getBoard();
		for (int i = 0;i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				String button = "button".concat(Integer.toString((i)*6+j+1));
				int id = getResources().getIdentifier(button, "id", this.getPackageName());
				SquareField field = (SquareField)findViewById(id);
				field.setText(("" + board.getToken(j, i).getLetter()).toUpperCase());
			}
		}
	}
	
	public void submitPath(List<Point> walked_coordinates, List<View> walked) {
		
		this.walked = walked;
		try {
			manager.submitWord(walked_coordinates);
			this.setBackgroundColor(R.drawable.buttonlayout_valid);
			CustomField s = (CustomField)findViewById(R.id.score_field);
			int score = Integer.valueOf("" + s.getText());
			int score_new = score + walked_coordinates.size();
			s.setText(String.valueOf(score_new));
		} catch (SelectionException s) {
			if (s.isPathNotConnected() || s.isWordNotFound()) {
				this.setBackgroundColor(R.drawable.buttonlayout_invalid);
			} else if (s.isWordAlreadyFound()) {
				this.setBackgroundColor(R.drawable.buttonlayout_already);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void setBackgroundColor(int layout) {
		
		for (int i = 0; i < walked.size(); i++) {
			this.walked.get(i).setBackgroundDrawable(
					getResources().getDrawable(layout));
		}
		Thread mythread = new Thread(runnable);
		mythread.start();
	}
	
	@SuppressLint("HandlerLeak") Runnable runnable = new Runnable() {
		
        public void run() {     	
        		synchronized (this) {
        		  try {
        			 wait(500);
        		  } catch (Exception e) {}
        		}
        		handler.sendEmptyMessage(0);
        }
	};

	Handler handler  = new Handler() {
		
		  @SuppressWarnings("deprecation")
		@Override
		  public void handleMessage(Message msg) {
			  for (int i=0;i<walked.size();i++) {
				walked.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonlayout));
			  }
		  }
	};
	
	public void quit(View view) {
		
		Intent intent = new Intent(this, EndGameActivity.class);
		startActivity(intent);
		finish();
	}
}
