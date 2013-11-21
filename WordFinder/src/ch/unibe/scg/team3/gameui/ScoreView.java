package ch.unibe.scg.team3.gameui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import ch.unibe.scg.team3.game.AbstractGame;
import ch.unibe.scg.team3.game.Event;
import ch.unibe.scg.team3.game.IGameObserver;

/**
 * This view is a custom {@link TextView} for displaying the score of the game.
 * 
 * @author adrian
 */
public class ScoreView extends TextView implements IGameObserver {

	public ScoreView(Context context) {
		super(context);
		init();
	}

	public ScoreView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setText("0");
	}

	@Override
	public void update(AbstractGame game, Event event) {
		if (event.getAction() == Event.WORD_FOUND) {
			setText(Integer.toString(game.getScore()));
		}
	}
}
