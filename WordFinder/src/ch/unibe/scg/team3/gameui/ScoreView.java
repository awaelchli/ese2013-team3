package ch.unibe.scg.team3.gameui;

import ch.unibe.scg.team3.game.Game;
import ch.unibe.scg.team3.game.IGameObserver;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ScoreView extends TextView implements IGameObserver{

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
	public void update(Game game) {
		setText(Integer.toString(game.getScore()));
	}



}
