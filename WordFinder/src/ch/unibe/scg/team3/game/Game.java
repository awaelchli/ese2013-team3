package ch.unibe.scg.team3.game;

import java.util.ArrayList;

import ch.unibe.scg.team3.board.*;
import ch.unibe.scg.team3.gameui.*;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.token.*;
import ch.unibe.scg.team3.wordfinder.R;

/**
 * The Game class is responsible for the proper execution of the game. 
 * The Game can be observed by the components of the user interface.
 * 
 * @author adrian
 * @author faerber
 */

public class Game implements IObservable {

	public static final int MAX_WORDS_TO_FIND = 10;
	
	private Board board;
	private int score;
	private String wordlist;
	
	private final ArrayList<IGameObserver> observers;
	private final ArrayList<WordSelection> found;
	
	private final WordlistHandler data;
	private int guesses = 0;
	
	

	/**
	 * @param boardSize
	 *            The size of the board must be greater than zero
	 * @param data
	 *            A DataHandler to access the database, not null
	 */
	public Game(int boardSize, WordlistHandler data, String wordlistName) {
		this.data = data;
		observers = new ArrayList<IGameObserver>();

		generateBoard(boardSize);

		found = new ArrayList<WordSelection>();
		
		wordlist = wordlistName;
		score = 0;

	}
	
	public Game(WordlistHandler data, String wordlistName) {
		this(Board.DEFAULT_SIZE, data, wordlistName);
	}

	private void generateBoard(int boardSize) {
		//SimpleDBBoardGenerator gen = new SimpleDBBoardGenerator(boardSize, data, 30);
		RandomBoardGenerator rnd = new RandomBoardGenerator(Board.DEFAULT_SIZE);
		board = rnd.getBoard();
	}

	/**
	 * The method reads the path and selects the tokens on the board. It creates
	 * the word according to the path and validates this word. 
	 * 
	 * @param path The path to be checked, not null.
	 */
	public void submitPath(Path<BoardButton> path) {
		assert path != null;
		guesses++;
		WordSelection selection = makeSelection(path);

		String word = selection.toString();

		if (!data.isWordInWordlist(word, wordlist)) {

			path.setColor(R.drawable.not_valid_button_animation);

		} else if (found.contains(selection)) {
			path.setColor(R.drawable.already_button_animation);

		} else {
			found.add(selection);
			path.setColor(R.drawable.valid_button_animation);
			updateScore(selection);
		}
		notifyObservers();
	}

	public int getGuesses() {
		return guesses;
	}

	private void updateScore(WordSelection selection) {
		score += selection.getScore();
	}

	/**
	 * Creates a selection based on the paths coordinate points.
	 * 
	 * @param path A path which is not null
	 * @return The word selection with, with token from the board according to the path.
	 */
	private WordSelection makeSelection(Path<BoardButton> path) {

		assert path != null;

		WordSelection selection = new WordSelection();

		for (BoardButton b : path) {
			Point p = b.getCoordinates();
			IToken tok = board.getToken(p.getX(), p.getY());
			selection.addToken(tok);
		}

		return selection;
	}

	@Override
	public void addObserver(IGameObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(IGameObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (IGameObserver observer : observers) {
			observer.update(this);
		}
	}

	public Board getBoard() {
		return board.clone();
	}

	/**
	 * @return The score of the current game, a positive integer
	 */
	public int getScore() {
		assert score >= 0;
		return score;
	}

	public ArrayList<WordSelection> getFoundWords() {
		return found;
	}

	public boolean isOver() {
		return found.size() == MAX_WORDS_TO_FIND;
	}
}