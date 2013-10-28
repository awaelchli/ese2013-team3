package ch.unibe.scg.team3.gameui;

import java.util.ArrayList;

import ch.unibe.scg.team3.game.Game;
import ch.unibe.scg.team3.game.IGameObserver;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FoundWordsView extends TextView implements IGameObserver {

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
	public void update(Game game) {
		ArrayList<String> list = game.getFoundWords();
		
		StringBuilder builder = new StringBuilder();
		
		for(String word : list){
			builder.append(word);
			builder.append("\n");
		}
		
		String text = builder.toString();
		setText(text.toUpperCase());
	}

}
