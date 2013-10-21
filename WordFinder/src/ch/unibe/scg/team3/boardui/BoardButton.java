package ch.unibe.scg.team3.boardui;

import ch.unibe.scg.team3.game.Point;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class BoardButton extends Button {
	
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

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = getMeasuredWidth();
		setMeasuredDimension(width, width);
	}

	public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}
}
