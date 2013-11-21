package ch.unibe.scg.team3.gameui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.Point;
import ch.unibe.scg.team3.game.AbstractGame;
import ch.unibe.scg.team3.game.Event;
import ch.unibe.scg.team3.game.IGameObserver;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.wordfinder.R;

/**
 * The BoardUI is the user interface for the board. It is responsible to update
 * its buttons when the observed board changes.
 * 
 * @author adrian
 * @see Board
 */
public class BoardUI extends TableLayout implements IGameObserver {

	private BoardButton[][] buttons;

	public BoardUI(Context context) {
		super(context);
	}

	public BoardUI(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void init(int size) {

		buttons = new BoardButton[size][size];
		TableRow[] rows = new TableRow[size];

		for (int i = 0; i < size; i++) {
			TableRow row = (TableRow) View.inflate(getContext(), R.layout.board_row, null);

			rows[i] = row;

			for (int j = 0; j < size; j++) {
				BoardButton btn = (BoardButton) View.inflate(getContext(), R.layout.board_button,
						null);

				btn.setCoordinates(new Point(j, i));
				buttons[j][i] = btn;
				row.addView(btn);
			}

			this.addView(row);
		}
	}

	@Override
	public void update(AbstractGame game, Event event) {
		if (event.getAction() == Event.BOARD_CREATED || event.getAction() == Event.BOARD_UPDATED) {
			
			int size = game.getBoardSize();

			if (buttons == null) {
				init(size);
			}

			Board board = game.getBoard();

			assert board.getSize() == size;

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {

					BoardButton button = buttons[i][j];
					IToken tok = board.getToken(i, j);
					String letter = "" + tok.getLetter();
					int value = tok.getValue();
					button.setLetter(letter.toUpperCase());
					button.setValue(value);
				}
			}
		}
	}
}
