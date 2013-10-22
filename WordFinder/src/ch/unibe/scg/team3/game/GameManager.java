package ch.unibe.scg.team3.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.R;
import android.content.Context;
import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.CustomBoardGenerator;
import ch.unibe.scg.team3.board.WordSelection;
import ch.unibe.scg.team3.token.IToken;
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
//		RandomBoardGenerator rnd = new RandomBoardGenerator(boardSize);
		CustomBoardGenerator rnd = new CustomBoardGenerator(boardSize);
		rnd.generate();
		board = rnd.getBoard();

		wordlist = loadDefaultWordlist();
		found = new Wordlist("Found words");
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}

	private Wordlist loadDefaultWordlist() {
		//not working
		WordlistBuilder builder = new WordlistBuilder("English");
		//builder.addWords(new File("assets/english.txt"));
		InputStream ins = this.context.getResources().openRawResource(R.raw.englishreduced);
		InputStreamReader insr = new InputStreamReader(ins);
		BufferedReader reader = new BufferedReader(insr);
		builder.addWords(reader);
		return builder.getWordlist();
	}

	public void submitWord(List<Point> path) throws SelectionException {
		
		assert path != null;
		assert path.size() > 0;
		
//		System.out.println(wordlist.getSize());

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