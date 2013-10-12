package ch.unibe.scg.team3.board;

/**
 * 
 * @author adrian
 *
 */
public class Board {

	private IToken[][] tokens;
	private final int size;
	
	public Board(int size){
		this.size = size;
		assert invariant();
		init();
	}
	
	private void init() {
		tokens = new IToken[size][size];
		
		for(IToken[] col : tokens){
			for(IToken tok : col){
				tok = NullToken.getInstance();
			}
		}
	}

	private boolean invariant(){
		return size > 0;
	}
	
	private boolean inRange(int row, int col){
		return 0 <= row && 0 <= col && row < size && col < size;
	}
	
	public void setToken(IToken tok, int row, int col){
		assert tok != null;
		assert inRange(row, col);
		tokens[row][col] = tok;
	}
	
	public IToken getToken(int row, int col){
		assert inRange(row, col);
		
		return tokens[row][col];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(IToken[] col : tokens){
			for(IToken tok : col){
				sb.append(tok.toString());
			}
		}
		
		return sb.toString();
	}
}
