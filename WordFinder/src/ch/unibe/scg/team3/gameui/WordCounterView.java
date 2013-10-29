package ch.unibe.scg.team3.gameui;

import ch.unibe.scg.team3.game.Game;
import ch.unibe.scg.team3.game.IGameObserver;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class WordCounterView extends TextView implements IGameObserver{

	public WordCounterView(Context context) {
		super(context);
	}

	public WordCounterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WordCounterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void update(Game game) {
		int found = game.getFoundWords().size();
		setText(found + "/" + Game.MAX_WORDS_TO_FIND);
	}

}
