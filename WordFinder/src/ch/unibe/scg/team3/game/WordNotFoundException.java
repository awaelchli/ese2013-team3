package ch.unibe.scg.team3.game;

public class WordNotFoundException extends SelectionException {

	public WordNotFoundException() {
		super();
	}

	@Override
	public boolean isPathNotConnected() {
		return false;
	}

	@Override
	public boolean isWordAlreadyFound() {
		return false;
	}

	@Override
	public boolean isWordNotFound() {
		return true;
	}
}
