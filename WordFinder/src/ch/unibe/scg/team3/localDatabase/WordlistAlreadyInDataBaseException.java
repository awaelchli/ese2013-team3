package ch.unibe.scg.team3.localDatabase;

public class WordlistAlreadyInDataBaseException extends Exception {

	public WordlistAlreadyInDataBaseException() {
		super("Wordlist already exists");
	}

}
