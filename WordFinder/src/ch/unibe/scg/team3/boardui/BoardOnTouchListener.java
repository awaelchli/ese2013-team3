package ch.unibe.scg.team3.boardui;

import java.util.ArrayList;
import java.util.List;


import ch.unibe.scg.team3.game.*;
import ch.unibe.scg.team3.wordfinder.R;
import ch.unibe.scg.team3.wordfinder.R.drawable;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.view.*;
import android.view.View.OnTouchListener;
/**
 * @author adrian
 * @author faerber
 */

public class BoardOnTouchListener implements OnTouchListener {

	public static final int FINGER_PADDING = 20;

	private List<BoardButton> walked_buttons;
	private List<Point> walked_coordinates;
	private final Context context;
	// private BoardMapper map;
	private final GameManager manager;

	// public BoardOnTouchListener(GameActivity ga, GameManager manager) {
	// this.context = ga;
	// //this.FINGER_PADDING = padding;
	// map = new BoardMapper(6);
	// }

	public BoardOnTouchListener(Context context, GameManager manager) {
		this.context = context;
		this.manager = manager;
		
		walked_buttons = new ArrayList<BoardButton>();
		walked_coordinates = new ArrayList<Point>();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		boolean isInList = false;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			 walked_buttons.clear();
			 walked_coordinates.clear();
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			// this.ga.submitPath(this.walked_coordinates, this.walked_views);
			submitPath();
			return true;
		default:
			return false;
		}

		BoardUI board = (BoardUI) v;

		for (int i = 0; i < board.getChildCount(); i++) {
			View rview = board.getChildAt(i);
			Rect rrect = new Rect(rview.getLeft(), rview.getTop(),
					rview.getRight(), rview.getBottom());
			if (rrect.contains((int) event.getX(), (int) event.getY())) {
				BoardRow srow = (BoardRow) rview;
				for (int j = 0; j < srow.getChildCount(); j++) {
					BoardButton fview = (BoardButton) srow.getChildAt(j);
					Rect frect;
					if (i == 0) {
						frect = new Rect(fview.getLeft() + FINGER_PADDING,
								fview.getTop() + FINGER_PADDING,
								fview.getRight() - FINGER_PADDING,
								fview.getBottom() - FINGER_PADDING);
					} else {
						frect = new Rect(fview.getLeft() + FINGER_PADDING,
								fview.getTop() + FINGER_PADDING,
								fview.getRight() - FINGER_PADDING,
								fview.getBottom() + rview.getBottom()
										- FINGER_PADDING);
					}
					if (frect.contains((int) event.getX(), (int) event.getY())) {

						for (int k = 0; k < walked_buttons.size(); k++) {
							if (walked_buttons.get(k).equals(fview)) {
								isInList = true;
							}
						}

						if (!isInList) {
							walked_buttons.add(fview);
							walked_coordinates.add(fview.getCoordinates());

							fview.setBackgroundDrawable(this.context
									.getResources().getDrawable(
											R.drawable.buttonlayout_walk));
						}
						break;
					}
				}
				break;
			}
		}
		return true;
	}

	private void submitPath() {

		
		
		try {
			manager.submitWord(this.walked_coordinates);
			setPathBackground(R.drawable.buttonlayout_valid);
		} catch (SelectionException e) {
			
			if (e.isPathNotConnected() || e.isWordNotFound()) {
				setPathBackground(R.drawable.buttonlayout_invalid);
				
			} else if (e.isWordAlreadyFound()) {
				setPathBackground(R.drawable.buttonlayout_already);
			} 
		}
	}

	private void setPathBackground(int layout) {

		for (BoardButton b : walked_buttons) {
			b.setBackgroundDrawable(context.getResources().getDrawable(layout));
		}
	}

}
