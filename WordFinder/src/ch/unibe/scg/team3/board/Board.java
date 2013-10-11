package ch.unibe.scg.team3.board;

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
	
	public void setToken(IToken tok, int row, int col){
		//TODO: implement setToken
	}
	
	public IToken getToken(int row, int col){
		assert row >= 0 && row < size;
		assert col >= 0 && col < size;
		
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
