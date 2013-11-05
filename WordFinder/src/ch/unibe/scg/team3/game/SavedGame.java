package ch.unibe.scg.team3.game;

import ch.unibe.scg.team3.board.Board;

public class SavedGame {
	
	private int id;
	private String name;
	private Board board;
	private int foundWords;
	private String time;
	private String date;
	private int wordlistId;
	private int score;
	private boolean isPrivate;
	private int timesPlayed;
	private int guesses;

	public SavedGame() {

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

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getFoundWords() {
		return foundWords;
	}

	public void setFoundWords(int foundWords) {
		this.foundWords = foundWords;
	}

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

	public int getWordlistId() {
		return wordlistId;
	}

	public void setWordlistId(int wordlistId) {
		this.wordlistId = wordlistId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

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

	public int getGuesses() {
		return guesses;
	}

	public void setGuesses(int guesses) {
		this.guesses = guesses;
	}

}
