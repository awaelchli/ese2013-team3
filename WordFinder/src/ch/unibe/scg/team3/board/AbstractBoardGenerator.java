package ch.unibe.scg.team3.board;

/**
 * A board generator generates a board based on the implemented
 * {@link #generate()} method. It is also responsible for measuring the letters
 * on the generated board.
 * 
 * @see AbstractLetterMeter
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

	/**
	 * Returns the board that was generated.
	 * 
	 * @see #generate()
	 * @return The generated board
	 */
	public Board getBoard() {
		generate();
		return board;
	}

	/**
	 * Gives a character a value based on the frequency in language
	 * 
	 * @param c
	 *            The letter to be measured
	 * @return The measured value
	 */
	protected int valueOf(char c) {
		return meter.getValue(c);
	}
}
