package ch.unibe.scg.team3.gameui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import ch.unibe.scg.team3.board.Point;

public class BoardButton extends RelativeLayout {
	
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
		
		int parentWidth= MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(parentWidth, parentWidth);
		
		setLayoutParams(new TableRow.LayoutParams(width,width));
	}

	public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

	public void setValue(int value) {
		TextView valueView = (TextView) getChildAt(1);
		valueView.setText(Integer.toString(value));
	}
	
	public void setLetter(String letter){
		TextView letterView = (TextView) getChildAt(0);
		letterView.setText(letter);
	}
}
