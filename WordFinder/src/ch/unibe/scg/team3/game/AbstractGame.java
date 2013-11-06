package ch.unibe.scg.team3.game;

import java.util.ArrayList;
import java.util.Timer;

import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.WordSelection;

public abstract class AbstractGame implements IObservable{

	protected Board board;
	protected int score;
	protected int guesses;
	protected int wordlistId;
	
	protected final ArrayList<IGameObserver> observers;
	protected final ArrayList<WordSelection> found;

	protected AbstractGame(){
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

	public ArrayList<WordSelection> getFoundWords() {
		return found;
	}

	public abstract boolean isOver();

	public int getBoardSize() {
		return board.getSize();
	}

}
