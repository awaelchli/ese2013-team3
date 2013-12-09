package ch.unibe.scg.team3.gameui;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import ch.unibe.scg.team3.board.WordSelection;
import ch.unibe.scg.team3.game.*;

/**
 * This view is a custom {@link TextView}. It is responsible for displaying the
 * words the user found on the board.
 * 
 * @author adrian
 */
public class FoundWordsView extends TextView implements IGameObserver {

	public static final String RIGHT_SCORE_BRACKET = ")";
	public static final String LEFT_SCORE_BRACKET = "(";

	public FoundWordsView(Context context) {
		super(context);
	}

	public FoundWordsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FoundWordsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void update(AbstractGame game, Event event) {

		if (event.getAction() == Event.WORD_FOUND) {
			
			ArrayList<WordSelection> list = game.getFoundWords();

			StringBuilder builder = new StringBuilder();

			for (WordSelection word : list) {
				builder.append(word);
				builder.append(" ");
				builder.append(LEFT_SCORE_BRACKET);
				builder.append(word.getScore());
				builder.append(RIGHT_SCORE_BRACKET);
				builder.append("\n");
			}

			String text = builder.toString();
			setText(text.toUpperCase());
		}
	}

}
