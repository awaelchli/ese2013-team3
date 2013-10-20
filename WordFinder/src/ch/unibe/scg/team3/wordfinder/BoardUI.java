package ch.unibe.scg.team3.wordfinder;

import android.content.Context;
import android.widget.TableLayout;

public class BoardUI extends TableLayout {

	public static final int DEFAULT_SIZE = 7;

	private final BoardButton[][] buttons;
	private final SquareRow[] rows;

	public BoardUI(Context context) {
		super(context);
		this.buttons = new BoardButton[DEFAULT_SIZE][DEFAULT_SIZE];
		this.rows = new SquareRow[DEFAULT_SIZE];
		init(context);
	}

	private void init(Context context) {

		for (BoardButton[] col : buttons) {
			for (BoardButton btn : col) {
				btn = new BoardButton(context);
				// btn.setOnTouchListener(new BoardOnTouchListener())
			}
		}

		for (int i = 0; i < rows.length; i++) {
			SquareRow row = rows[i];
			row = new SquareRow(context);

			for (int j = 0; j < buttons.length; j++) {
				row.addView(buttons[i][j]);
			}
		}
	}

}
