package ch.unibe.scg.team3.game;

import java.util.ArrayList;

import ch.unibe.scg.team3.board.*;
import ch.unibe.scg.team3.gameui.*;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.token.*;
import ch.unibe.scg.team3.wordfinder.R;

/**
 * The Game class is responsible for the proper execution of the game. The Game
 * can be observed by the components of the user interface.
 * 
 * @author adrian
 * @author faerber
 */

public class Game implements IGame {

	public static final int DEFAULT_MIN_WORDS_TO_FIND = 30;
	public static final int MAX_WORDS_TO_FIND = 10;
	public static final long TIME_LIMIT = 2 * 60000;

	private final WordlistHandler wordlistHandler;

	private Timer timer;
	private boolean timeOver;
	private Board board;
	protected int score;
	protected int guesses;
	protected int wordlistId;
	protected final ArrayList<IGameObserver> observers;
	protected final ArrayList<WordSelection> found;

	/**
	 * @param boardSize
	 *            The size of the board must be greater than zero
	 * @param wordlistHandler
	 *            A DataHandler to access the database, not null
	 */
	public Game(int boardSize, WordlistHandler handler, int wordlistId) {
		observers = new ArrayList<IGameObserver>();
		found = new ArrayList<WordSelection>();
		score = 0;
		guesses = 0;

		wordlistHandler = handler;
		this.wordlistId = wordlistId;
		timeOver = false;

		generateBoard(boardSize);
		initTimer();
	}

	public Game(WordlistHandler data, int wordlistId) {
		this(Board.DEFAULT_SIZE, data, wordlistId);
	}

	private void generateBoard(int boardSize) {
//		 SimpleDBBoardGenerator gen = new SimpleDBBoardGenerator(boardSize,
//		 wordlistHandler, DEFAULT_MIN_WORDS_TO_FIND);
//		IterativeDBBoardGenerator gen = new IterativeDBBoardGenerator(boardSize,
//			 wordlistHandler, DEFAULT_MIN_WORDS_TO_FIND);
//		PrimitiveDBBoardGenerator gen = new PrimitiveDBBoardGenerator(boardSize,
//				 wordlistHandler, DEFAULT_MIN_WORDS_TO_FIND);
		PrimitiveRecursiveDBBoardGenerator gen = new PrimitiveRecursiveDBBoardGenerator(boardSize,
				 wordlistHandler, DEFAULT_MIN_WORDS_TO_FIND);
		
		 board = gen.getBoard();
//		RandomBoardGenerator rnd = new RandomBoardGenerator(boardSize);
//		board = rnd.getBoard();

	}

	/**
	 * The method reads the path and selects the tokens on the board. It creates
	 * the word according to the path and validates this word.
	 * 
	 * @param path
	 *            The path to be checked, not null.
	 */
	public void submitPath(Path<BoardButton> path) {
		assert path != null;
		guesses++;
		WordSelection selection = makeSelection(path);

		String word = selection.toString();

		if (!wordlistHandler.isWordInWordlist(word, wordlistId)) {

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

	private void updateScore(WordSelection selection) {
		score += selection.getScore();
	}

	/**
	 * Creates a selection based on the paths coordinate points.
	 * 
	 * @param path
	 *            A path which is not null
	 * @return The word selection with, with token from the board according to
	 *         the path.
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

	private void initTimer() {
		timer = new Timer(TIME_LIMIT) {
			@Override
			public void onFinish() {
				timeOver = true;
				notifyObservers();
			}

			@Override
			public void onTick(long millisUntilFinished) {
				super.onTick(millisUntilFinished);
				notifyObservers();
			}
		};
	}

	public void startTime() {
		timer.start();
	}

	public long stopTime() {
		timer.cancel();
		timeOver = true;
		return timer.getRemainingTime();
	}

	@Override
	public String getTime() {
		return timer.toString();
	}

	public Timer getTimer() {
		return timer;
	}

	@Override
	public boolean isOver() {
		return found.size() == MAX_WORDS_TO_FIND || timeOver;
	}

	@Override
	public Board getBoard() {
		return board.clone();
	}

	@Override
	public int getBoardSize() {
		return board.getSize();
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

	@Override
	public int getScore() {
		assert score >= 0;
		return score;
	}

	@Override
	public int getNumberOfGuesses() {
		return guesses;
	}

	@Override
	public int getNumberOfFoundWords() {
		return found.size();
	}

	@Override
	public ArrayList<WordSelection> getFoundWords() {
		return found;
	}

	@Override
	public int getWordlistId() {
		return wordlistId;
	}

}