package ch.unibe.scg.team3.gameui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import ch.unibe.scg.team3.game.AbstractGame;
import ch.unibe.scg.team3.game.IGameObserver;

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

		setText(game.getTime());
	}

}
