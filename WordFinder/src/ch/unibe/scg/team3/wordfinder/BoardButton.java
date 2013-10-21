package ch.unibe.scg.team3.wordfinder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;

public class BoardButton extends Button {

	public BoardButton(Context context) {
		super(context);
//		inflate(context, R.layout.board_button, (ViewGroup) this.getParent());
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = getMeasuredWidth();
		setMeasuredDimension(width, width);
	}

}
