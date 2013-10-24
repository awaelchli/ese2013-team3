package ch.unibe.scg.team3.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;
import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.Point;
import ch.unibe.scg.team3.board.RandomBoardGenerator;
import ch.unibe.scg.team3.board.WordSelection;
import ch.unibe.scg.team3.gameui.BoardButton;
import ch.unibe.scg.team3.gameui.IWordlistObserver;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.Token;
import ch.unibe.scg.team3.wordfinder.R;
import ch.unibe.scg.team3.wordlist.Wordlist;
import ch.unibe.scg.team3.wordlist.WordlistBuilder;

/**
 * @author adrian
 * @author faerber
 */

public class Game {

	private final Board board;
	private final Wordlist wordlist;
	private final Wordlist found;
	private int score;
	//TODO: better dont use context for game 
	private Context context;

	public Game(int boardSize, Context context) {
		this.context = context;
		RandomBoardGenerator rnd = new RandomBoardGenerator(boardSize);
		rnd.generate();
		board = rnd.getBoard();

		wordlist = loadDefaultWordlist();
		found = new Wordlist("Found words");
		score = 0;
	}

	public Board getBoard() {
		return this.board;
	}
//
//	public void setContext(Context context) {
//		this.context = context;
//	}
	
	public void addFoundListObserver(IWordlistObserver o){
		found.addObserver(o);
	}

	private Wordlist loadDefaultWordlist() {
		// not working
		WordlistBuilder builder = new WordlistBuilder("English");
		// builder.addWords(new File("assets/english.txt"));
		InputStream ins = this.context.getResources().openRawResource(
				R.raw.englishreduced);
		InputStreamReader insr = new InputStreamReader(ins);
		BufferedReader reader = new BufferedReader(insr);
		builder.addWords(reader);
		return builder.getWordlist();
	}

	public void submitPath(Path path) {

		assert path != null;

//		System.out.println(wordlist.getSize());

//		if (!PathChecker.check(path)) {
//			throw new PathNotConnectedException();
//		}

		WordSelection selection = makeSelection(path);

		String word = selection.toString();

		if (!wordlist.contains(word)) {
			path.setColor(R.drawable.not_valid_button_animation);
			
		} else if (found.contains(word)) {
			path.setColor(R.drawable.already_button_animation);
			
		} else {
			found.addWord(word);
			path.setColor(R.drawable.valid_button_animation);
			updateScore(selection);
		}
	}

	private void updateScore(WordSelection selection) {
		score += selection.getScore();
	}

	private WordSelection makeSelection(Path path) {

		assert path != null;

		WordSelection selection = new WordSelection();
		
		for (BoardButton b : path) {
			Point p = b.getCoordinates();
			IToken tok = board.getToken(p.getX(), p.getY());
			selection.addToken(tok);
		}

		return selection;
	}
}