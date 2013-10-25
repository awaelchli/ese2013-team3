package ch.unibe.scg.team3.gameui;

import ch.unibe.scg.team3.board.*;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.wordfinder.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

public class BoardUI extends TableLayout implements IBoardObserver {

	private BoardButton[][] buttons;
	private TableRow[] rows;
	private final int size;

	public BoardUI(Context context) {
		super(context);
		size = Board.DEFAULT_SIZE;
		init(context);
	}

	public BoardUI(Context context, AttributeSet attrs) {
		super(context, attrs);
		size = Board.DEFAULT_SIZE;
		init(context);
	}

	private void init(Context context) {

		this.buttons = new BoardButton[size][size];
		this.rows = new TableRow[size];

		for (int i = 0; i < size; i++) {
			TableRow row = (TableRow) View.inflate(context, R.layout.board_row,
					null);
			
			rows[i] = row;

			for (int j = 0; j < size; j++) {
				BoardButton btn = (BoardButton) View.inflate(context,
						R.layout.board_button, null);
				btn.setCoordinates(new Point(j, i));
				buttons[j][i] = btn;
				row.addView(btn);
			}
			
			this.addView(row);
		}
	}

	@Override
	public void update(Board board) {

		assert board.getSize() == size;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				BoardButton button = buttons[i][j];
				IToken tok = board.getToken(i, j);
				String letter = "" + tok.getLetter();
				button.setText(letter.toUpperCase());
			}
		}
	}
}
