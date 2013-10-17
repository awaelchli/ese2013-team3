package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import ch.unibe.scg.team3.game.GameManager;
import ch.unibe.scg.team3.game.Point;
import ch.unibe.scg.team3.game.SelectionException;

/**
@author faerber
*/

@SuppressLint("NewApi") public class GridActivity extends Activity {

	List<View> walked;
	/** The following list walked_coordinates is interesting for the manager!!
	 	Use GridActivity.getCoordinates() to retrieve the coordinates.
		It contains the coordinates of the walked fields.
		Each element in the list is an Integer array with two elements: {row,column}
		The top left field has coordinates {0,0}, the bottom right field has
		coordinates {5,5}! 
		The list elements order is the order in that the corresponding
		fields were walked.
	**/
	List<Point> walked_coordinates;
	/**
	  	Adapt this value in the onCreate() method if your fingers are too fat!
	 */
	int finger_padding;
	HashMap<String, Point> hmap;
	GameManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ViewGroup board = (ViewGroup)findViewById(R.id.tableBoard);
        board.setOnTouchListener(new View.OnTouchListener() {
        	@Override
            public boolean onTouch(View v, MotionEvent event) {  
            	
            	boolean isInList = false;
            	
            	switch (event.getAction()) {
            		case MotionEvent.ACTION_DOWN:
            			walked = new ArrayList<View>();
            			walked_coordinates = new ArrayList<Point>();
            		case MotionEvent.ACTION_MOVE:
            			break;
            		case MotionEvent.ACTION_UP:
            			
            			try {
            				manager.submitWord(walked_coordinates);
            			} catch(SelectionException s) {
                			for (int i=0;i<walked.size();i++) {
                				walked.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonlayout_valid));
                			}	
            			}
            			return true;
            		default:
            			return false;
            	}
            	
                TableLayout layout = (TableLayout)v;
                for(int i =0; i< layout.getChildCount(); i++)
                {
                    View rview = layout.getChildAt(i);
                    Rect rrect = new Rect(rview.getLeft(), rview.getTop(), rview.getRight(), rview.getBottom());
                    if(rrect.contains((int)event.getX(), (int)event.getY()))
                    {
                        SquareRow srow = (SquareRow)rview;
                        for(int j =0; j< srow.getChildCount(); j++)
                        {
                            View fview = srow.getChildAt(j);
                            Rect frect;
                            if (i==0) {
                            	frect = new Rect(fview.getLeft()+finger_padding, fview.getTop()+finger_padding, fview.getRight()-finger_padding, fview.getBottom()-finger_padding);
                            } else {
                            	frect = new Rect(fview.getLeft()+finger_padding, fview.getTop()+finger_padding, fview.getRight()-finger_padding, fview.getBottom()+rview.getBottom()-finger_padding);
                            }
                            if(frect.contains((int)event.getX(), (int)event.getY()))
                            {
                            	for (int k=0; k<walked.size();k++) {
                            		if (walked.get(k).equals(fview)) {
                            			isInList = true;
                            		}
                            	}
                        		if (!isInList) {
                        			walked.add(fview);
                        			walked_coordinates.add(hmap.get((String)(fview.getTag())));
                        			fview.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonlayout_walk));
                        		}
                        		break;
                            }
                        }
                        break;
                    }
                }
                return true;
            }
		});
        manager = new GameManager(6);
        createHashMap();
        this.finger_padding = 20;
    }
    
    public void createHashMap() {
    	// Maps view's id to view's location on board
    	// int[] = {row,column} whereas {0,0} is the top left corner and {5,5} the bottom right corner
        hmap = new HashMap<String, Point>();
        hmap.put("button1", new Point(0,0)); hmap.put("button2", new Point(1,0));
        hmap.put("button3", new Point(2,0)); hmap.put("button4", new Point(3,0));
        hmap.put("button5", new Point(4,0)); hmap.put("button6", new Point(5,0));
        hmap.put("button7", new Point(0,1)); hmap.put("button8", new Point(1,1));
        hmap.put("button9", new Point(2,1)); hmap.put("button10", new Point(3,1));
        hmap.put("button11", new Point(4,1)); hmap.put("button12", new Point(5,1));
        hmap.put("button13", new Point(0,2)); hmap.put("button14", new Point(1,2));
        hmap.put("button15", new Point(2,2)); hmap.put("button16", new Point(3,2));
        hmap.put("button17", new Point(4,2)); hmap.put("button18", new Point(5,2));
        hmap.put("button19", new Point(0,3)); hmap.put("button20", new Point(1,3));
        hmap.put("button21", new Point(2,3)); hmap.put("button22", new Point(3,3));
        hmap.put("button23", new Point(4,3)); hmap.put("button24", new Point(5,3));
        hmap.put("button25", new Point(0,4)); hmap.put("button26", new Point(1,4));
        hmap.put("button27", new Point(2,4)); hmap.put("button28", new Point(3,4));
        hmap.put("button29", new Point(4,4)); hmap.put("button30", new Point(5,4));
        hmap.put("button31", new Point(0,5)); hmap.put("button32", new Point(1,5));
        hmap.put("button33", new Point(2,5)); hmap.put("button34", new Point(3,5));
        hmap.put("button35", new Point(4,5)); hmap.put("button36", new Point(5,5));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grid, menu);
        return true;
    }
    
    public Rect calculateBoundary(int id) {
    	Rect re = new Rect();
    	findViewById(id).getGlobalVisibleRect(re);
    	return re;
    }
    
    public List getCoordinates() {
    	return this.walked_coordinates;
    }
    
    public void quit(View view){
		Intent intent = new Intent(this, EndGameActivity.class);
    	startActivity(intent);
    	finish();
	}
}
