package ch.unibe.scg.team3.game;

import java.util.ArrayList;

import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.WordSelection;

/**
 * This class provides a rudimental structure of a game. A game can be observed
 * by several observers and thus the responsibility of this class is to add,
 * remove and notify observers.
 * 
 * @author adrian
 * 
 */
public abstract class AbstractGame implements IObservable {

	protected Board board;
	protected int score;
	protected int guesses;
	protected int wordlistId;

	protected final ArrayList<IGameObserver> observers;
	protected final ArrayList<WordSelection> found;

	protected AbstractGame() {
		observers = new ArrayList<IGameObserver>();
		found = new ArrayList<WordSelection>();
		score = 0;
		guesses = 0;
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

	/**
	 * @return A clone of the board
	 */
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

	/**
	 * @return The total guesses the player made, positive
	 */
	public int getNumberOfGuesses() {
		return guesses;
	}

	/**
	 * @return The total words the player found, positive
	 */
	public int getNumberOfFoundWords() {
		return found.size();
	}

	/**
	 * @return A list of all the words the player found
	 */
	public ArrayList<WordSelection> getFoundWords() {
		return found;
	}

	/**
	 * @return The size of the board on which the game operates
	 */
	public int getBoardSize() {
		return board.getSize();
	}

	/**
	 * Returns the id of the wordlist the game uses. It is corresponding to the
	 * id in the database
	 * 
	 * @return The id of the wordlist
	 */
	public int getWordlistId() {
		return wordlistId;
	}

	public abstract String getTime();

	public abstract boolean isOver();

}
