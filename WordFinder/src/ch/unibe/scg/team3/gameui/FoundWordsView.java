package ch.unibe.scg.team3.gameui;

import ch.unibe.scg.team3.wordlist.Wordlist;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FoundWordsView extends TextView implements IWordlistObserver {

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
	public void update(Wordlist wordlist) {
		setText(wordlist.getFormattedList("\n").toUpperCase());
	}

}
