package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ch.unibe.scg.team3.game.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TableLayout;

/**
 * @author adrian
 * @author faerber
 */

public class BoardOnTouchListener implements OnTouchListener {
	List<View> walked_views;
	List<Point> walked_coordinates;
	GridActivity ga;
	int finger_padding;
	HashMap<String, Point> hmap;

	public BoardOnTouchListener(GridActivity ga, int padding,
			HashMap<String, Point> map) {
		this.ga = ga;
		this.finger_padding = padding;
		this.hmap = map;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		boolean isInList = false;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			walked_views = new ArrayList<View>();
			walked_coordinates = new ArrayList<Point>();
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			this.ga.submitPath(this.walked_coordinates, this.walked_views);
			return true;
		default:
			return false;
		}

		TableLayout layout = (TableLayout) v;
		for (int i = 0; i < layout.getChildCount(); i++) {
			View rview = layout.getChildAt(i);
			Rect rrect = new Rect(rview.getLeft(), rview.getTop(),
					rview.getRight(), rview.getBottom());
			if (rrect.contains((int) event.getX(), (int) event.getY())) {
				SquareRow srow = (SquareRow) rview;
				for (int j = 0; j < srow.getChildCount(); j++) {
					View fview = srow.getChildAt(j);
					Rect frect;
					if (i == 0) {
						frect = new Rect(fview.getLeft() + finger_padding,
								fview.getTop() + finger_padding,
								fview.getRight() - finger_padding,
								fview.getBottom() - finger_padding);
					} else {
						frect = new Rect(fview.getLeft() + finger_padding,
								fview.getTop() + finger_padding,
								fview.getRight() - finger_padding,
								fview.getBottom() + rview.getBottom()
										- finger_padding);
					}
					if (frect.contains((int) event.getX(), (int) event.getY())) {
						for (int k = 0; k < walked_views.size(); k++) {
							if (walked_views.get(k).equals(fview)) {
								isInList = true;
							}
						}
						if (!isInList) {
							walked_views.add(fview);
							walked_coordinates.add(this.hmap
									.get((String) (fview.getTag())));
							fview.setBackgroundDrawable(this.ga.getResources()
									.getDrawable(R.drawable.buttonlayout_walk));
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
