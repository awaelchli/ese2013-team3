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

	public static final int MAX_WORDS_TO_FIND = 10;

	private final WordlistHandler wordlistHandler;

	/**
	 * @param boardSize
	 *            The size of the board must be greater than zero
	 * @param wordlistHandler
	 *            A DataHandler to access the database, not null
	 */
	public Game(int boardSize, WordlistHandler handler, int wordlistId) {
		super();

		wordlistHandler = handler;
		this.wordlistId = wordlistId;

		generateBoard(boardSize);
	}

	public Game(WordlistHandler data, int wordlistId) {
		this(Board.DEFAULT_SIZE, data, wordlistId);
	}

	private void generateBoard(int boardSize) {
		// SimpleDBBoardGenerator gen = new SimpleDBBoardGenerator(boardSize,
		// wordlistHandler, 30);
		// board = gen.getBoard();
		RandomBoardGenerator rnd = new RandomBoardGenerator(boardSize);
		board = rnd.getBoard();
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

	@Override
	public boolean isOver() {
		return found.size() == MAX_WORDS_TO_FIND;
	}

}