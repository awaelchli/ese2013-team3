package ch.unibe.scg.team3.gameui;

import ch.unibe.scg.team3.game.IGame;
import ch.unibe.scg.team3.game.IGameObserver;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * This view is a custom {@link TextView} for displaying the score of the game.
 * 
 * @author adrian
 */
public class ScoreView extends TextView implements IGameObserver {

	public ScoreView(Context context) {
		super(context);
	}

	public ScoreView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void update(IGame game) {
		setText(Integer.toString(game.getScore()));
	}
}
