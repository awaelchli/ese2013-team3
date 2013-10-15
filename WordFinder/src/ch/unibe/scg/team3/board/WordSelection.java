package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.List;

import token.IToken;

/**
 * 
 * @author adrian
 *
 */
public class WordSelection {

	private List<IToken> selection;
	
	public WordSelection(){
		this.selection = new ArrayList<IToken>();
	}
	
	public void addToken(IToken tok){
		this.selection.add(tok);
	}
	
	public int length(){
		assert selection.size() >= 0;
		return selection.size();
	}
	
	public int getScore(){
		
		int sum = 0;
		
		for(IToken tok : this.selection){
			sum += tok.getValue();
		}
		
		return sum;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for(IToken tok : this.selection){
			sb.append(tok.toString());
		}
		
		return sb.toString();
	}
	
	
}
