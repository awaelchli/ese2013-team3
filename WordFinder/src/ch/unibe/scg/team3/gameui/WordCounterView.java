package ch.unibe.scg.team3.gameui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import ch.unibe.scg.team3.game.AbstractGame;
import ch.unibe.scg.team3.game.Event;
import ch.unibe.scg.team3.game.Game;
import ch.unibe.scg.team3.game.IGameObserver;

/**
 * This custom {@link Textiew} is responsible for displaying the number of words
 * the user found.
 * 
 * @author adrian
 * 
 */
public class WordCounterView extends TextView implements IGameObserver {

	public static final String INITIAL_WORDS_FOUND = "0";
	public static final String WORD_COUNT_DELIMETER = "/";

	public WordCounterView(Context context) {
		super(context);
		init();
	}

	public WordCounterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WordCounterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		setText(INITIAL_WORDS_FOUND + WORD_COUNT_DELIMETER + Game.DEFAULT_MIN_WORDS_TO_FIND);
	}

	@Override
	public void update(AbstractGame game, Event event) {
		if (event.getAction() == Event.WORD_FOUND) {
			int found = game.getFoundWords().size();
			setText(found + WORD_COUNT_DELIMETER + Game.DEFAULT_MIN_WORDS_TO_FIND);
		}
	}

}
