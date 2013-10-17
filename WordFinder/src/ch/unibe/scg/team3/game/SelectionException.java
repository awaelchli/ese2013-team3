package ch.unibe.scg.team3.game;

public abstract class SelectionException extends Exception {

	public SelectionException() {
		super();
	}
	
	public abstract boolean isPathNotConnected();
	
	public abstract boolean isWordAlreadyFound();
	
	public abstract boolean isWordNotFound();
}
