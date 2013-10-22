package ch.unibe.scg.team3.wordfinder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomField extends TextView {

	public CustomField(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int pWidth = MeasureSpec.getSize(widthMeasureSpec);
		final int height = getMeasuredHeight();
		setMeasuredDimension(pWidth/2, height);
	}
} 