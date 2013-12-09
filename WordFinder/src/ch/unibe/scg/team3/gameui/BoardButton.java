package ch.unibe.scg.team3.gameui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import ch.unibe.scg.team3.board.Point;
import ch.unibe.scg.team3.game.IElement;

/**
 * This class models a custom button for the board user interface. It has two
 * textviews, one for displaying the letter and one for the value of the letter.
 * 
 * @author adrian
 */
public class BoardButton extends RelativeLayout implements IElement {

	private Point coordinates;

	public BoardButton(Context context) {
		super(context);
	}

	public BoardButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BoardButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*
	 * Makes the buttons squared
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = getMeasuredWidth();

		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(parentWidth, parentWidth);

		setLayoutParams(new TableRow.LayoutParams(width, width));
	}

	@Override
	public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Set a value to appear next to the letter on the Button. This value is
	 * intended to be the value of the letter according to the token in the
	 * board.
	 * 
	 * @param value
	 *            The value to be set
	 */
	public void setValue(int value) {
		TextView valueView = (TextView) getChildAt(1);
		valueView.setText(Integer.toString(value));
	}

	public void setLetter(String letter) {
		TextView letterView = (TextView) getChildAt(0);
		letterView.setText(letter);
	}
}
