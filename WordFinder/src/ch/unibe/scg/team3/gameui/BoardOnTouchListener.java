package ch.unibe.scg.team3.gameui;

import ch.unibe.scg.team3.game.*;
import ch.unibe.scg.team3.wordfinder.R;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.view.View.OnTouchListener;

/**
 * @author adrian
 * @author faerber
 */

public class BoardOnTouchListener implements OnTouchListener {

	public static final int FINGER_PADDING = 20;

	private final Path path;
	private final Context context;
	private final Game game;

	public BoardOnTouchListener(Context context, Game game) {
		this.context = context;
		this.game = game;
		
		path = new Path();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.clear();
		case MotionEvent.ACTION_UP:
			submitPath();
			return true;
		case MotionEvent.ACTION_MOVE:
			break;
		default:
			return false;
		}

		BoardUI board = (BoardUI) v;

		for (int i = 0; i < board.getChildCount(); i++) {
			
			BoardRow row = (BoardRow) board.getChildAt(i);
			Rect rowRect = makeRectangle(row);
			
			if (rowRect.contains((int) event.getX(), (int) event.getY())) {
				
				for (int j = 0; j < row.getChildCount(); j++) {
					
					BoardButton button = (BoardButton) row.getChildAt(j);
					
					Rect frect;
					if (i == 0) {
						frect = new Rect(button.getLeft() + FINGER_PADDING,
								button.getTop() + FINGER_PADDING,
								button.getRight() - FINGER_PADDING,
								button.getBottom() - FINGER_PADDING);
					} else {
						frect = new Rect(button.getLeft() + FINGER_PADDING,
								button.getTop() + FINGER_PADDING,
								button.getRight() - FINGER_PADDING,
								button.getBottom() + row.getBottom()
										- FINGER_PADDING);
					}
					
					if (frect.contains((int) event.getX(), (int) event.getY())) {
						
						if(path.add(button)){
							
							Drawable walk = context.getResources().getDrawable(R.drawable.buttonlayout_walk);
							button.setBackgroundDrawable(walk);
						}
						break;
					}
				}
				break;
			}
		}
		return true;
	}

	private Rect makeRectangle(View view) {
		Rect viewRect = new Rect(view.getLeft(), view.getTop(),
				view.getRight(), view.getBottom());
		return viewRect;
	}

	private void submitPath() {
		
			game.submitPath(path);
			makePathAnimation(path);
	}

	private void makePathAnimation(Path path) {

		for (BoardButton b : path) {
			
			b.setBackgroundDrawable(context.getResources().getDrawable(path.getColor()));

			AnimationDrawable anim = (AnimationDrawable) b.getBackground();
			anim.start();
		}
	}

}
