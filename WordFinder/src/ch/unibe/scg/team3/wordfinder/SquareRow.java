package ch.unibe.scg.team3.wordfinder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;

public class SquareRow extends TableRow{
	
	public SquareRow(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    //final int width = getMeasuredWidth();
	    final int pWidth = MeasureSpec.getSize(widthMeasureSpec);
	    setMeasuredDimension(pWidth, pWidth/6);
	}
}
