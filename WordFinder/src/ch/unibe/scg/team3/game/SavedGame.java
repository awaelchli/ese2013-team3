package ch.unibe.scg.team3.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import ch.unibe.scg.team3.board.*;

/**
 * A saved game stores all the data needed to recover the game and replay it.
 * 
 * @author adrian
 * @see AbstractGame
 */
public class SavedGame extends AbstractGame implements Serializable {

	private static final long serialVersionUID = -269683003596675103L;

	private int id;
	private String name;
	private String time;
	private String date;
	private boolean isPrivate;
	private String board;

	public boolean isPersonal(){
		return this.isPersonal;
	}
	
	public ArrayList<WordSelection> getFoundWords(){
		return this.found;
	}
	public void setFoundWords(ArrayList<WordSelection> found){
		this.found=found;
	}

	public String getStringBoard() {
		return board;
	}

	public void setStringBoard(String stringBoard) {
		double side = Math.sqrt(stringBoard.length());
		if (side % 1 == 0) {
			board = stringBoard;
		}
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
	private void readObject(ObjectInputStream aStream) throws IOException, ClassNotFoundException {
		aStream.defaultReadObject();
		// manually deserialize and init superclass
		score = (Integer) aStream.readObject();
		guesses = (Integer) aStream.readObject();
		wordlistId = (Integer) aStream.readObject();
		timesPlayed = (Integer) aStream.readObject();
		observers = (ArrayList<IGameObserver>) aStream.readObject();
		found = (ArrayList<WordSelection>) aStream.readObject();
		isPersonal = (Boolean) aStream.readObject();
	}

	/**
	 * Custom serialization is needed for superclass fields.
	 */
	private void writeObject(ObjectOutputStream aStream) throws IOException {
		aStream.defaultWriteObject();
		// manually serialize superclass
		aStream.writeObject(score);
		aStream.writeObject(guesses);
		aStream.writeObject(wordlistId);
		aStream.writeObject(timesPlayed);
		aStream.writeObject(observers);
		aStream.writeObject(found);
		aStream.writeObject(isPersonal);
	}
}
