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
import ch.unibe.scg.team3.localDatabase.DataManager;
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
	private final Wordlist found;
	private final DataManager data;
	private int score;

	public Game(int boardSize, DataManager data) {
		this.data = data;
		RandomBoardGenerator rnd = new RandomBoardGenerator(boardSize);
		rnd.generate();
		board = rnd.getBoard();
		//TODO: remove after testing
//		Testing long word: angiotensin
		board.setToken(new Token('a', 1), 0, 0);
		board.setToken(new Token('n', 1), 0, 1);
		board.setToken(new Token('g', 1), 0, 2);
		board.setToken(new Token('i', 1), 0, 3);
		board.setToken(new Token('o', 1), 0, 4);
		board.setToken(new Token('t', 1), 0, 5);
		board.setToken(new Token('e', 1), 1, 5);
		board.setToken(new Token('n', 1), 2, 5);
		board.setToken(new Token('s', 1), 3, 5);
		board.setToken(new Token('i', 1), 4, 5);
		board.setToken(new Token('n', 1), 5, 5);
		

		found = new Wordlist("Found words");
		score = 0;
	}

	public Board getBoard() {
		return this.board;
	}
	
	public void addFoundListObserver(IWordlistObserver o){
		found.addObserver(o);
	}

	public void submitPath(Path path) {

		assert path != null;

		WordSelection selection = makeSelection(path);

		String word = selection.toString();

		if(!data.isWordInWordlist(word, "English")){
			
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