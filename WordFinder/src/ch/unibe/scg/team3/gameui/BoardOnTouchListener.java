package ch.unibe.scg.team3.gameui;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TableRow;
import ch.unibe.scg.team3.game.Game;
import ch.unibe.scg.team3.path.ColoredPath;
import ch.unibe.scg.team3.wordfinder.R;

/**
 * The instance of this class is responsible for monitoring the user input on
 * the board user interface.
 * 
 * @author adrian
 * @author faerber
 */

public class BoardOnTouchListener implements OnTouchListener {

	public static final int FINGER_PADDING = 20;

	private final ColoredPath<BoardButton> path;
	private final Context context;
	private final Game game;

	public BoardOnTouchListener(Context context, Game game) {
		this.context = context;
		this.game = game;

		path = new ColoredPath<BoardButton>();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.clear();
		case MotionEvent.ACTION_UP:
			if (!path.isEmpty())
				submitPath();
			return true;
		case MotionEvent.ACTION_MOVE:
			break;
		default:
			return false;
		}

		BoardUI board = (BoardUI) v;

		for (int i = 0; i < board.getChildCount(); i++) {

			TableRow row = (TableRow) board.getChildAt(i);
			Rect rowRect = makeRectangle(row, 0, 0, 0, 0);

			if (rowRect.contains((int) event.getX(), (int) event.getY())) {

				for (int j = 0; j < row.getChildCount(); j++) {

					BoardButton button = (BoardButton) row.getChildAt(j);

					int pad = FINGER_PADDING;
					Rect rect;
					if (i == 0) {
						rect = makeRectangle(button, pad, pad, -pad, -pad);
					} else {
						rect = makeRectangle(button, pad, pad, -pad,
								-pad + row.getBottom());
					}

					if (rect.contains((int) event.getX(), (int) event.getY())) {

						if (path.add(button)) {

							Drawable walk = context.getResources().getDrawable(
									R.drawable.buttonlayout_walk);
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

	private Rect makeRectangle(View view, int pad_left, int pad_top,
			int pad_right, int pad_bottom) {

		Rect viewRect = new Rect(view.getLeft() + pad_left, view.getTop()
				+ pad_top, view.getRight() + pad_right, view.getBottom()
				+ pad_bottom);

		return viewRect;
	}

	private void submitPath() {
		game.submitPath(path);
		makePathAnimation(path);
	}

	private void makePathAnimation(ColoredPath<BoardButton> path) {

		for (BoardButton button : path) {

			button.setBackgroundDrawable(context.getResources().getDrawable(
					path.getColor()));

			AnimationDrawable anim = (AnimationDrawable) button.getBackground();
			anim.start();
		}
	}

}
