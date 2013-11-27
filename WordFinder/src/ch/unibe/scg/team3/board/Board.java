package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.Iterator;


import ch.unibe.scg.team3.path.Path;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.NullToken;

/**
 * This class models a matrix filled with tokens.
 * The size of the board can be any number greater than zero.
 * 
 * @author adrian
 */
public class Board implements Iterable<IToken>{

	public static final int DEFAULT_SIZE = 6;
	
	private IToken[][] tokens;
	private final int size;

	/**
	 * Creates a board with default size
	 */
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

		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				setToken(new NullToken(col, row));
			}
		}
	}

	private boolean invariant() {
		return size > 0;
	}

	public boolean inRange(int col, int row) {
		return 0 <= col && 0 <= row && col < size && row < size;
	}
	
	/**
	 * Places the token on the board according to its coordinates.
	 * @param tok The token to be placed
	 */
	public void setToken(IToken tok) {
		assert tok != null;
		
		Point point = tok.getCoordinates();
		int col = point.getX();
		int row = point.getY();
		
		assert inRange(col, row);
		tokens[col][row] = tok.clone();
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
	
	public IToken getToken(Point coordinate) {
		return getToken(coordinate.getX(), coordinate.getY());
	}
	
	/**
	 * Places the tokens of a {@link Path} on the board according to the coordinates of the tokens.
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
	
	public boolean hasEmptyTokens() {
		for(IToken tok : this){
			if(tok.isEmpty()){
				return true;
			}
		}
		return false;
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
		
		for(int col = 0; col < size; col++){
			for(int row = 0; row < size; row++){
				IToken tok = getToken(col, row);
				clone.setToken(tok);
			}
		}
		return clone;
	}

	@Override
	public Iterator<IToken> iterator() {
		ArrayList<IToken> iterable = new ArrayList<IToken>();
		for(int row = 0; row < size; row++){
			for(int col = 0; col < size; col++){
				iterable.add(getToken(col, row));
			}
		}
		return iterable.iterator();
	}
}
