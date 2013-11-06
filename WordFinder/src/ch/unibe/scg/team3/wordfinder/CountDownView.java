package ch.unibe.scg.team3.wordfinder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import ch.unibe.scg.team3.game.AbstractGame;
import ch.unibe.scg.team3.game.Game;
import ch.unibe.scg.team3.game.IGameObserver;
import ch.unibe.scg.team3.gameui.Timer;

public class CountDownView extends TextView implements IGameObserver {

	public CountDownView(Context context) {
		super(context);
	}

	public CountDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CountDownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void update(AbstractGame game) {
		Game g = (Game) game;
		Timer timer = g.getTimer();

		setText(timer.toString());
	}

}
