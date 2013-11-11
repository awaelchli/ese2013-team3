package ch.unibe.scg.team3.game;

import java.io.Serializable;
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
public interface IGame extends IObservable{

	



	/**
	 * @return A clone of the board
	 */
	public abstract Board getBoard();

	/**
	 * @return The score of the current game, a positive integer
	 */
	public int getScore();

	/**
	 * @return The total guesses the player made, positive
	 */
	public int getNumberOfGuesses();

	/**
	 * @return The total words the player found, positive
	 */
	public int getNumberOfFoundWords();

	/**
	 * @return A list of all the words the player found
	 */
	public ArrayList<WordSelection> getFoundWords();

	/**
	 * @return The size of the board on which the game operates
	 */
	public abstract int getBoardSize();

	/**
	 * Returns the id of the wordlist the game uses. It is corresponding to the
	 * id in the database
	 * 
	 * @return The id of the wordlist
	 */
	public int getWordlistId();

	public abstract String getTime();

	public abstract boolean isOver();

}
