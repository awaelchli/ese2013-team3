package ch.unibe.scg.team3.game;

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

public class Game extends AbstractGame {

	public static final int DEFAULT_MIN_WORDS_TO_FIND = 30;
	public static final long TIME_LIMIT = 5 * 60000;

	private final WordlistHandler wordlistHandler;

	private Timer timer;
	private boolean timeOver;
	private Board board;
	

	
	/**
	 * Creates a game for a given board
	 * 
	 * @param board The board to be played on, not null
	 *            The size of the board must be greater than zero
	 * @param wordlistHandler
	 *            A DataHandler to access the database, not null
	 * @param wordlistId
	 *            The Id of the wordlist to be used
	 */
	public Game(Board board, WordlistHandler handler, int wordlistId) {
		super();

		this.board = board;
		wordlistHandler = handler;
		this.wordlistId = wordlistId;
		timeOver = false;

	}
	/**
	 * Creates a new game with a board generated with words from the given wordlist
	 * 
	 * @param boardSize
	 *            The size of the board must be greater than zero
	 * @param wordlistHandler
	 *            A DataHandler to access the database, not null
	 * @param wordlistId
	 *            The Id of the wordlist to be used
	 */
	public Game(int boardSize, WordlistHandler handler, int wordlistId) {
		this(new Board(boardSize), handler, wordlistId);
		generateBoard(boardSize);
	}

	/**
	 * Creates a game with default board size
	 * 
	 * @param wordlistHandler
	 *            A DataHandler to access the database, not null
	 * @param wordlistId
	 *            The Id of the wordlist to be used
	 */
	public Game(WordlistHandler data, int wordlistId) {
		this(Board.DEFAULT_SIZE, data, wordlistId);
	}

	/**
	 * Creates a game based on a saved game so it can be raplayed.
	 * 
	 * @param wordlistHandler
	 *            A DataHandler to access the database, not null
	 */
	public Game(final SavedGame game, WordlistHandler handler) {
		super(game);
		wordlistHandler = handler;
		board = game.getBoard();
		timeOver = false;
	}

	private void generateBoard(int boardSize) {
//		AbstractBoardGenerator gen = new PrimitiveDBBoardGenerator(boardSize, wordlistHandler,
//				DEFAULT_MIN_WORDS_TO_FIND);
		AbstractBoardGenerator gen = new FastBoardGenerator(boardSize, wordlistHandler,
				DEFAULT_MIN_WORDS_TO_FIND);
		board = gen.getBoard();

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
	
	public void setTimer(Timer timer){
		this.timer = timer;
	}

	/**
	 * Starts the countdown timer
	 */
	public void startTime() {
		timer.start();
	}

	/**
	 * Stops the time and makes the game over
	 * 
	 * @return Returns the remaining time of the game
	 */
	public long stopTime() {
		timer.cancel();
		timeOver = true;
		return timer.getRemainingTime();
	}

	@Override
	public String getTime() {
		return timer.toString();
	}

	/**
	 * The game is over when enough words were found or the time runs out.
	 */
	@Override
	public boolean isOver() {
		return found.size() == DEFAULT_MIN_WORDS_TO_FIND || timeOver;
	}

	@Override
	public Board getBoard() {
		return board.clone();
	}

	@Override
	public int getBoardSize() {
		return board.getSize();
	}

	/**
	 * Saves all relevant data such as score, board, found words etc. in a {@link SavedGame}.
	 * 
	 * @return The saved state of the game.
	 */
	public SavedGame save() {
		SavedGame saved = new SavedGame();
		saved.setScore(getScore());
		saved.setStringBoard(board.toString());
		saved.setTime(getTime());
		saved.setAttempts(getNumberOfAttempts());
		saved.setWordlistId(getWordlistId());
		saved.setTimesPlayed(getTimesPlayed());
		saved.setFoundWords(found);
		saved.setNumberOfFoundWords(found.size());
		saved.setTime(timer.getElapsedTime());

		return saved;
	}
}