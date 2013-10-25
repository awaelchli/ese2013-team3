package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.List;


import ch.unibe.scg.team3.gameui.IBoardObserver;
import ch.unibe.scg.team3.gameui.IObservable;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.NullToken;

/**
 * This class has the responsibility to model a matrix filled with token.
 * The size of the board can be any number greater than zero.
 * 
 * @author adrian
 */
public class Board implements IObservable{

	public static final int DEFAULT_SIZE = 6;
	
	private IToken[][] tokens;
	private final int size;
		
	private final List<IBoardObserver> observers;

	public Board(){
		this(DEFAULT_SIZE);
	}
	
	/**
	 * @param size The size of the board must be greater than zero
	 */
	public Board(int size) {
		this.size = size;
		this.observers = new ArrayList<IBoardObserver>();
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

	private boolean inRange(int row, int col) {
		return 0 <= row && 0 <= col && row < size && col < size;
	}

	/**
	 * @param tok The token to place
	 * @param row A non-negative integer within the range of the board
	 * @param col A non-negative integer within the range of the board
	 */
	public void setToken(IToken tok, int row, int col) {
		assert tok != null;
		assert inRange(row, col);
		tokens[row][col] = tok;
	}

	/**
	 * 
	 * @param row A non-negative integer within the range of the board
	 * @param col A non-negative integer within the range of the board
	 * @return A copy of the token at the given coordinate
	 */
	public IToken getToken(int row, int col) {
		assert inRange(row, col);

		return tokens[row][col].clone();
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

		for (IToken[] col : tokens) {
			for (IToken tok : col) {
				sb.append(tok.toString());
			}
		}

		return sb.toString();
	}

	@Override
	public void notifyObserver() {
		for(IBoardObserver o : observers){
			o.update(this);
		}
	}

	@Override
	public void addObserver(IBoardObserver o) {
		if(!observers.contains(o)){
			observers.add(o);
		}
	}
}
