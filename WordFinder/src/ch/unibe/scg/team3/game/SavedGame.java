package ch.unibe.scg.team3.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.RawBoardBuilder;
import ch.unibe.scg.team3.board.WordSelection;

/**
 * A saved game stores all the data needed to recover the game and replay it.
 * 
 * @author adrian
 * @author nils
 * @see AbstractGame
 */
public class SavedGame extends AbstractGame implements Serializable {

	private static final long serialVersionUID = -269683003596675103L;
	
	private String name;
	private long remainingTime;
	private String date;
	private String board;
	private int foundWords;

	public ArrayList<WordSelection> getFoundWords() {
		return this.found;
	}

	public void setFoundWords(ArrayList<WordSelection> found) {
		this.found = found;
	}

	public void setNumberOfFoundWords(int foundWords) {
		this.foundWords = foundWords;
	}

	@Override
	public int getNumberOfFoundWords() {
		return foundWords;
	}

	public String getStringBoard() {
		return board;
	}

	public void setStringBoard(String stringBoard) {

		try {
			double side = Math.sqrt(stringBoard.length());
			if (side % 1 == 0) {
				board = stringBoard;
			}
		} catch (Exception e) {
			board = "";
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(long remaining) {
		remainingTime = remaining;
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
	public boolean isPersonal() {
		return isPersonal;
	}

	public void setPersonal(boolean isPersonal) {
		this.isPersonal = isPersonal;
	}

	public void setTimesPlayed(int timesPlayed) {
		this.timesPlayed = timesPlayed;
	}

	public void setAttempts(int guesses) {
		this.attempts = guesses;
	}

	@Override
	public boolean isOver() {
		return true;
	}

	@Override
	public Board getBoard() {
		RawBoardBuilder builder = new RawBoardBuilder(board);
		return builder.getBoard();
	}

	@Override
	public int getBoardSize() {
		return (int) Math.sqrt(board.length());
	}

	/**
	 * Custom deserialization is needed for superclass fields.
	 */
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream aStream) throws IOException, ClassNotFoundException {
		aStream.defaultReadObject();
		score = (Integer) aStream.readObject();
		attempts = (Integer) aStream.readObject();
		wordlistId = (Integer) aStream.readObject();
		timesPlayed = (Integer) aStream.readObject();
		observers = (ArrayList<IGameObserver>) aStream.readObject();
		isPersonal = (Boolean) aStream.readObject();
	}

	/**
	 * Custom serialization is needed for superclass fields.
	 */
	private void writeObject(ObjectOutputStream aStream) throws IOException {
		aStream.defaultWriteObject();
		aStream.writeObject(score);
		aStream.writeObject(attempts);
		aStream.writeObject(wordlistId);
		aStream.writeObject(timesPlayed);
		aStream.writeObject(observers);
		aStream.writeObject(isPersonal);
	}
}
