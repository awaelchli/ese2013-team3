package ch.unibe.scg.team3.board;

/**
 * @author adrian
 */
public abstract class AbstractBoardGenerator {

	protected final Board board;
	protected final AbstractLetterMeter meter;

	public AbstractBoardGenerator(int size) {
		this.board = new Board(size);
		meter = new DefaultLetterMeter();
	}

	protected abstract void generate();

	public Board getBoard() {
		generate();
		return board;
	}
	
	protected int valueOf(char c){
		return meter.getValue(c);
	}
}
