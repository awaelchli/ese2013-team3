package ch.unibe.scg.team3.game;

public class WordAlreadyFoundException extends SelectionException {

	public WordAlreadyFoundException() {
		super();
	}

	@Override
	public boolean isPathNotConnected() {
		return false;
	}

	@Override
	public boolean isWordAlreadyFound() {
		return true;
	}

	@Override
	public boolean isWordNotFound() {
		return false;
	}
}
