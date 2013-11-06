package ch.unibe.scg.team3.localDatabase;
/**
 * 
 * @author nils
 *
 */
public class WordlistAlreadyInDataBaseException extends Exception {

	public WordlistAlreadyInDataBaseException() {
		super("Wordlist already exists");
	}

}
