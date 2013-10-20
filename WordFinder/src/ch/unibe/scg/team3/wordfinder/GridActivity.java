package ch.unibe.scg.team3.wordfinder;

import java.util.HashMap;
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
import ch.unibe.scg.team3.game.GameManager;
import ch.unibe.scg.team3.game.Point;
import ch.unibe.scg.team3.game.SelectionException;

/**
 * @author faerber
 * @author adrian
 */

@SuppressLint("NewApi")
public class GridActivity extends Activity {
	
	List<View> walked;
	/**
	 * The following list walked_coordinates is interesting for the manager!!
	 * Use GridActivity.getCoordinates() to retrieve the coordinates. It
	 * contains the coordinates of the walked fields. Each element in the list
	 * is an Integer array with two elements: {row,column} The top left field
	 * has coordinates {0,0}, the bottom right field has coordinates {5,5}! The
	 * list elements order is the order in that the corresponding fields were
	 * walked.
	 **/
	List<Point> walked_coordinates;
	/**
	 * Adapt this value in the onCreate() method if your fingers are too fat!
	 */
	int finger_padding;
	HashMap<String, Point> hmap;
	GridActivity ga;
	
	GameManager manager;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ViewGroup board = (ViewGroup)findViewById(R.id.tableBoard);
        manager = new GameManager(6,this);
		hmap = new HashMap<String, Point>();
        createHashMap();
        this.finger_padding = 20;
        board.setOnTouchListener(new BoardOnTouchListener(this, this.finger_padding, this.hmap));
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void submitPath(List<Point> walked_coordinates, List<View> walked) {
		this.walked = walked;
		this.walked_coordinates = walked_coordinates;
		try {
			manager.submitWord(walked_coordinates);
			for (int i = 0; i < walked.size(); i++) {
				walked.get(i).setBackgroundDrawable(
						getResources().getDrawable(
								R.drawable.buttonlayout_valid));
			}
			Thread mythread = new Thread(runnable);
			mythread.start();
		} catch (SelectionException s) {
			if (s.isPathNotConnected() || s.isWordNotFound()) {
				for (int i = 0; i < walked.size(); i++) {
					walked.get(i).setBackgroundDrawable(
							getResources().getDrawable(
									R.drawable.buttonlayout_invalid));
				}
				Thread mythread = new Thread(runnable);
				mythread.start();
			} else if (s.isWordAlreadyFound()) {
				for (int i = 0; i < walked.size(); i++) {
					walked.get(i).setBackgroundDrawable(
							getResources().getDrawable(
									R.drawable.buttonlayout_already));
				}
				Thread mythread = new Thread(runnable);
				mythread.start();
			}
		}
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
	
	public void createHashMap() {
		// Maps view's id to view's location on board
		// int[] = {row,column} whereas {0,0} is the top left corner and {5,5}
		// the bottom right corner
		hmap = new HashMap<String, Point>();
		hmap.put("button1", new Point(0, 0));
		hmap.put("button2", new Point(1, 0));
		hmap.put("button3", new Point(2, 0));
		hmap.put("button4", new Point(3, 0));
		hmap.put("button5", new Point(4, 0));
		hmap.put("button6", new Point(5, 0));
		hmap.put("button7", new Point(0, 1));
		hmap.put("button8", new Point(1, 1));
		hmap.put("button9", new Point(2, 1));
		hmap.put("button10", new Point(3, 1));
		hmap.put("button11", new Point(4, 1));
		hmap.put("button12", new Point(5, 1));
		hmap.put("button13", new Point(0, 2));
		hmap.put("button14", new Point(1, 2));
		hmap.put("button15", new Point(2, 2));
		hmap.put("button16", new Point(3, 2));
		hmap.put("button17", new Point(4, 2));
		hmap.put("button18", new Point(5, 2));
		hmap.put("button19", new Point(0, 3));
		hmap.put("button20", new Point(1, 3));
		hmap.put("button21", new Point(2, 3));
		hmap.put("button22", new Point(3, 3));
		hmap.put("button23", new Point(4, 3));
		hmap.put("button24", new Point(5, 3));
		hmap.put("button25", new Point(0, 4));
		hmap.put("button26", new Point(1, 4));
		hmap.put("button27", new Point(2, 4));
		hmap.put("button28", new Point(3, 4));
		hmap.put("button29", new Point(4, 4));
		hmap.put("button30", new Point(5, 4));
		hmap.put("button31", new Point(0, 5));
		hmap.put("button32", new Point(1, 5));
		hmap.put("button33", new Point(2, 5));
		hmap.put("button34", new Point(3, 5));
		hmap.put("button35", new Point(4, 5));
		hmap.put("button36", new Point(5, 5));
	}
	
	public void quit(View view) {
		Intent intent = new Intent(this, EndGameActivity.class);
		startActivity(intent);
		finish();
	}
}
