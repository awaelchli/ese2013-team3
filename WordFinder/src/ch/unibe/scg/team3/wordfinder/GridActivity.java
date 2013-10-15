package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TableLayout;

/**
@author nfaerber
*/

@SuppressLint("NewApi") public class GridActivity extends Activity implements OnTouchListener {

	List<View> walked;
	/** The following list walked_coordinates is interesting for the manager!!
		It contains the coordinates of the walked fields.
		Each element in the list is an Integer array with two elements: {row,column}
		The top left field has coordinates {0,0}, the bottom right field has
		coordinates {5,5}! 
		The list elements order is the order in that the corresponding
		fields were walked **/
	List<int[]> walked_coordinates;
	HashMap<String, int[]> hmap;
	GameManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        findViewById(R.id.tableBoard).setOnTouchListener(this);
        createHashMap();
    }
    
    public void createHashMap() {
    	// Maps view's id to view's location on board
    	// int[] = {row,column} whereas {0,0} is the top left corner and {5,5} the bottom right corner
        hmap = new HashMap<String, int[]>();
        hmap.put("button1", new int[] {0,0}); hmap.put("button2", new int[] {0,1});
        hmap.put("button3", new int[] {0,2}); hmap.put("button4", new int[] {0,3});
        hmap.put("button5", new int[] {0,4}); hmap.put("button6", new int[] {0,5});
        hmap.put("button7", new int[] {1,0}); hmap.put("button8", new int[] {1,1});
        hmap.put("button9", new int[] {1,2}); hmap.put("button10", new int[] {1,3});
        hmap.put("button11", new int[] {1,4}); hmap.put("button12", new int[] {1,5});
        hmap.put("button13", new int[] {2,0}); hmap.put("button14", new int[] {2,1});
        hmap.put("button15", new int[] {2,2}); hmap.put("button16", new int[] {2,3});
        hmap.put("button17", new int[] {2,4}); hmap.put("button18", new int[] {2,5});
        hmap.put("button19", new int[] {3,0}); hmap.put("button20", new int[] {3,1});
        hmap.put("button21", new int[] {3,2}); hmap.put("button22", new int[] {3,3});
        hmap.put("button23", new int[] {3,4}); hmap.put("button24", new int[] {3,5});
        hmap.put("button25", new int[] {4,0}); hmap.put("button26", new int[] {4,1});
        hmap.put("button27", new int[] {4,2}); hmap.put("button28", new int[] {4,3});
        hmap.put("button29", new int[] {4,4}); hmap.put("button30", new int[] {4,5});
        hmap.put("button31", new int[] {5,0}); hmap.put("button32", new int[] {5,1});
        hmap.put("button33", new int[] {5,2}); hmap.put("button34", new int[] {5,3});
        hmap.put("button35", new int[] {5,4}); hmap.put("button36", new int[] {5,5});
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
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {  
    	
    	boolean isInList = false;
    	
    	switch (event.getAction()) {
    	case MotionEvent.ACTION_DOWN:
    		this.walked = new ArrayList<View>();
    		this.walked_coordinates = new ArrayList<int[]>();
    	case MotionEvent.ACTION_MOVE:
    		break;
    	case MotionEvent.ACTION_UP:
    		System.out.println(this.walked);
    		for (int i=0;i<this.walked.size();i++) {
    			this.walked.get(i).setBackground(getResources().getDrawable(R.drawable.buttonlayout));
    			int[] test = this.walked_coordinates.get(i);
    			System.out.println("coordinates:");
    			System.out.println(test[0]);
    			System.out.println(test[1]);
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
                    	frect = new Rect(fview.getLeft()+10, fview.getTop()+10, fview.getRight()-10, fview.getBottom()-10);
                    } else {
                    	frect = new Rect(fview.getLeft()+10, fview.getTop()+rview.getTop()+10, fview.getRight()-10, fview.getBottom()+rview.getBottom()-10);
                    }
                    if(frect.contains((int)event.getX(), (int)event.getY()))
                    {
                    	for (int k=0; k<this.walked.size();k++) {
                    		if (this.walked.get(k).equals(fview)) {
                    			isInList = true;
                    		}
                    	}
                		if (!isInList) {
                			this.walked.add(fview);
                			this.walked_coordinates.add(this.hmap.get((String)(fview.getTag())));
                			fview.setBackground(getResources().getDrawable(R.drawable.buttonlayout_walked));
                		}
                		break;
                    }
                }
                break;
            }
        }
        return true;
    }
}

