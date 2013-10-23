package ch.unibe.scg.team3.boardui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;

public class BoardRow extends TableRow {

	public BoardRow(Context context) {
		super(context);
	}

	public BoardRow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int pWidth = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(pWidth, pWidth / 6);
	}

}
