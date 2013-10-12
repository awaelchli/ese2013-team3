package ch.unibe.scg.team3.board;

public abstract class AbstractBoardGenerator {

	protected final Board board;

	public AbstractBoardGenerator(int size) {
		this.board = new Board(size);
	}

	public abstract void generate();

	public Board getBoard() {
		return board;
	}
}
