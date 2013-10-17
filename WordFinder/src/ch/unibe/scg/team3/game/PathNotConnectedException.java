package ch.unibe.scg.team3.game;

public class PathNotConnectedException extends SelectionException {

	public PathNotConnectedException() {
		super();
	}

	@Override
	public boolean isPathNotConnected() {
		return true;
	}

	@Override
	public boolean isWordAlreadyFound() {
		return false;
	}

	@Override
	public boolean isWordNotFound() {
		return false;
	}
}
