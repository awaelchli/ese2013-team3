package ch.unibe.scg.team3.game;

import java.io.Serializable;
import java.util.ArrayList;

import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.RawBoardBuilder;
import ch.unibe.scg.team3.board.WordSelection;

/**
 * A saved game stores all the data needed to recover the game and replay it.
 * 
 * @author adrian
 * @see IGame
 */
public class SavedGame implements IGame, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443483841551113239L;
	// private static final long serialVersionUID = 5916020210779611232L;

	private int id;
	private String name;
	private String time;
	private String date;
	private boolean isPrivate;
	private int timesPlayed;
	private String stringBoard;

	private int score;

	private int guesses;

	private int wordlistId;

	private final ArrayList<IGameObserver> observers;
	private final ArrayList<WordSelection> found;

	private int foundWords;

	public SavedGame() {
		observers = new ArrayList<IGameObserver>();
		found = new ArrayList<WordSelection>();
	}

	public String getStringBoard() {
		return stringBoard;
	}

	public void setStringBoard(String stringBoard) {
		this.stringBoard = stringBoard;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setWordlistId(int wordlistId) {
		this.wordlistId = wordlistId;
	}

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Returns if the game is private. Private games are games explicitly saved
	 * by the user. Non-private games are saved automatically and not visible to
	 * the user
	 * 
	 * @return True, if the game is private, false otherwise
	 */
	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public int getTimesPlayed() {
		return timesPlayed;
	}

	public void setTimesPlayed(int timesPlayed) {
		this.timesPlayed = timesPlayed;
	}

	public void setGuesses(int guesses) {
		this.guesses = guesses;
	}

	@Override
	public boolean isOver() {
		return true;
	}

	@Override
	public Board getBoard() {
		RawBoardBuilder builder = new RawBoardBuilder(stringBoard);
		return builder.getBoard();
	}

	@Override
	public int getBoardSize() {
		return (int) Math.sqrt(stringBoard.length());
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
		return score;
	}

	@Override
	public int getNumberOfGuesses() {
		return guesses;
	}

	@Override
	public int getNumberOfFoundWords() {
		return foundWords;
	}

	@Override
	public ArrayList<WordSelection> getFoundWords() {
		return found;
	}

	@Override
	public int getWordlistId() {
		return wordlistId;
	}

	public void setFoundWords(int found) {
		foundWords = found;
		
	}

}
