package ch.unibe.scg.team3.game;

import ch.unibe.scg.team3.board.*;
import ch.unibe.scg.team3.gameui.*;
import ch.unibe.scg.team3.localDatabase.DataManager;
import ch.unibe.scg.team3.token.*;
import ch.unibe.scg.team3.wordfinder.R;
import ch.unibe.scg.team3.wordlist.Wordlist;

/**
 * The Game class is responsible for the proper execution of the game. 
 * 
 * @author adrian
 * @author faerber
 */

public class Game {

	private final Board board;
	private final Wordlist found;
	private final DataManager data;
	private int score;

	/**
	 * @param boardSize The size of the board must be greater than zero
	 * @param data A DataManager to access the database, not null
	 */
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
		
		invariant();
	}
	
	private boolean invariant(){
		return board != null && found != null && data != null && score >= 0;
	}
	
	public Game(DataManager data){
		this(Board.DEFAULT_SIZE, data);
	}
	
	/**
	 * @param o This observer will be added to the wordlist of found words, not null.
	 */
	public void assignFoundListObserver(IWordlistObserver o){
		found.addObserver(o);
	}
	
	/**
	 * 
	 * @param o This observer will be added to the board of this game, not null
	 */
	public void assignBoardObserver(IBoardObserver o){
		board.addObserver(o);
		board.notifyObserver();
	}

	/**
	 * @param path A path 
	 */
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