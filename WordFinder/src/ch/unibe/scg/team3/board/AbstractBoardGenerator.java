package ch.unibe.scg.team3.board;

/**
 * 
 * @author adrian
 *
 */
public abstract class AbstractBoardGenerator {

	protected final Board board;
	protected final LetterMeter meter;

	public AbstractBoardGenerator(int size) {
		this.board = new Board(size);
		meter = new DefaultLetterMeter();
	}

	public abstract void generate();

	public Board getBoard() {
		return board;
	}
	
	protected int valueOf(char c){
		return meter.getValue(c);
	}
}
