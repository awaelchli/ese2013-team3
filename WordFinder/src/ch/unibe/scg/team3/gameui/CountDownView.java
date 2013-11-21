package ch.unibe.scg.team3.gameui;

import ch.unibe.scg.team3.game.*;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * This view is a custom {@link TextView}. It is responsible to display the
 * timer of a running game.
 * 
 * @author adrian
 */
public class CountDownView extends TextView implements IGameObserver {

	public CountDownView(Context context) {
		super(context);
		init();
	}

	public CountDownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CountDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setText(Timer.format(Game.TIME_LIMIT));
	}

	@Override
	public void update(AbstractGame game, Event event) {
		if (event.getAction() == Event.TIME_TICK) {
			String time = Timer.format(game.getRemainingTime());
			setText(time);
		}
	}

}
