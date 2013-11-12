package ch.unibe.scg.team3.localDatabase;
/**
 * 
 * @author nils
 *
 */
public class WordlistAlreadyInDataBaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6850055872037882539L;

	public WordlistAlreadyInDataBaseException() {
		super("Wordlist already exists");
	}

}
