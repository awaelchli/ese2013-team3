package ch.unibe.scg.team3.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;
import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.RandomBoardGenerator;
import ch.unibe.scg.team3.board.WordSelection;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.Token;
import ch.unibe.scg.team3.wordfinder.R;
import ch.unibe.scg.team3.wordlist.Wordlist;
import ch.unibe.scg.team3.wordlist.WordlistBuilder;

/**
 * @author adrian
 * @author faerber
 */

public class GameManager {

	private final Board board;
	private final Wordlist wordlist;
	private final Wordlist found;
	private Context context;

	public GameManager(int boardSize,Context context) {
		this.context = context;
		RandomBoardGenerator rnd = new RandomBoardGenerator(boardSize);
		rnd.generate();
		board = rnd.getBoard();
	
		//TODO: remove this after testing was successful
		board.setToken(new Token('c', 1), 0, 0);
		board.setToken(new Token('a', 1), 0, 1);
		board.setToken(new Token('r', 1), 0, 2);

		wordlist = loadDefaultWordlist();
		found = new Wordlist("Found words");
	}
	
	public void setContext(Context context) {
		this.context = context;
	}

	private Wordlist loadDefaultWordlist() {
		//not working
		WordlistBuilder builder = new WordlistBuilder("English");
		//builder.addWords(new File("assets/english.txt"));
		InputStream ins = this.context.getResources().openRawResource(R.raw.english);
		InputStreamReader insr = new InputStreamReader(ins);
		BufferedReader reader = new BufferedReader(insr);
		builder.addWords(reader);
		return builder.getWordlist();
	}

	public void submitWord(List<Point> path) throws SelectionException {
		
		assert path != null;
		assert path.size() > 0;
		
		System.out.println(wordlist.getSize());

		if (!PathChecker.check(path)) {
			throw new PathNotConnectedException();
		}

		WordSelection selection = makeSelection(path);

		String word = selection.toString();

		if (!this.wordlist.contains(word)) {
			throw new WordNotFoundException();
		}

		if (this.found.contains(word)) {
			throw new WordAlreadyFoundException();
		} else {
			this.found.addWord(word);
		}
	}

	private WordSelection makeSelection(List<Point> path) {
		
		assert path != null;
		
		WordSelection selection = new WordSelection();
		for (Point p : path) {
			IToken tok = board.getToken(p.getX(), p.getY());
			selection.addToken(tok);
		}
		
		return selection;
	}
}