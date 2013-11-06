package ch.unibe.scg.team3.board;

import ch.unibe.scg.team3.game.Path;
import ch.unibe.scg.team3.token.*;

/**
 * This class has the responsibility to model a matrix filled with token.
 * The size of the board can be any number greater than zero.
 * 
 * @author adrian
 */
public class Board {

	public static final int DEFAULT_SIZE = 6;
	
	private IToken[][] tokens;
	private final int size;

	public Board(){
		this(DEFAULT_SIZE);
	}
	
	/**
	 * @param size The size of the board must be greater than zero
	 */
	public Board(int size) {
		this.size = size;
		assert invariant();
		init();
	}

	private void init() {
		tokens = new IToken[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				setToken(NullToken.getInstance(), i, j);
			}
		}
	}

	private boolean invariant() {
		return size > 0;
	}

	private boolean inRange(int col, int row) {
		return 0 <= col && 0 <= row && col < size && row < size;
	}

	/**
	 * @param tok The token to place
	 * @param col A non-negative integer within the range of the board
	 * @param row A non-negative integer within the range of the board
	 */
	public void setToken(IToken tok, int col, int row) {
		assert tok != null;
		assert inRange(col, row);
		tokens[col][row] = tok;
	}
	
	//TODO: use this instead of the method above
	/**
	 * Places the token on the board according to its coordinates.
	 * @param tok The token to be placed
	 */
	public void setToken(IToken tok) {
		Point point = tok.getCoordinates();
		setToken(tok, point.getX(), point.getY());
	}

	/**
	 * @param col A non-negative integer within the range of the board
	 * @param row A non-negative integer within the range of the board
	 * @return A copy of the token at the given coordinate
	 */
	public IToken getToken(int col, int row) {
		assert inRange(col, row);

		return tokens[col][row].clone();
	}
	
	/**
	 * Places the tokens of a path on the board according to the coordinates of the tokens.
	 * 
	 * @param path The path containing the tokens to place
	 * @return true if the path fits on the board and false otherwise.
	 */
	public boolean setPath(Path<IToken> path){
		
		for(IToken tok : path){
			Point point = tok.getCoordinates();
			if(!inRange(point.getX(), point.getY())){
				return false;
			}
		}
		
		for(IToken tok : path){
			setToken(tok);
		}
		
		return true;
	}

	/**
	 * @return An integer greater than zero
	 */
	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (IToken[] row : tokens) {
			for (IToken tok : row) {
				sb.append(tok.toString());
			}
		}

		return sb.toString();
	}
	
	@Override
	public Board clone() {
		
		Board clone = new Board(size);
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				IToken tok = getToken(i, j);
				clone.setToken(tok.clone(), i, j);
			}
		}
		return clone;
	}
}
