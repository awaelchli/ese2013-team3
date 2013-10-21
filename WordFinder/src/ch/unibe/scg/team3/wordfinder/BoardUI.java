package ch.unibe.scg.team3.wordfinder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;

public class BoardUI extends TableLayout {

	public static final int DEFAULT_SIZE = 7;

	private BoardButton[][] buttons;
	private SquareRow[] rows;

	public BoardUI(Context context) {
		super(context);
		init(context);
	}
	

	public BoardUI(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}



	private void init(Context context) {

		this.buttons = new BoardButton[DEFAULT_SIZE][DEFAULT_SIZE];
		this.rows = new SquareRow[DEFAULT_SIZE];
		
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[0].length; j++) {
				buttons[i][j] = new BoardButton(context);
				// btn.setOnTouchListener(new BoardOnTouchListener())
			}
		}

		for (int i = 0; i < rows.length; i++) {
			SquareRow row = rows[i];
			row = new SquareRow(context);

			for (int j = 0; j < buttons.length; j++) {
				row.addView(buttons[i][j]);
			}
			
			this.addView(row);
		}
	}

}
