package ch.unibe.scg.team3.token;

/**
 * 
 * @author adrian
 *
 */
public abstract class AbstractToken implements IToken{
	
	@Override
	public abstract int getValue();

	@Override
	public abstract char getLetter();

	@Override
	public String toString() {
		return "" + getLetter();
	}

	@Override
	public boolean equals(Object o) {
		
		if(o == null) return false;
		if(getClass() != o.getClass()) return false;
		
		IToken tok = (IToken) o;
		
		boolean letters = tok.getLetter() == this.getLetter();
		boolean values = tok.getValue() == this.getValue();
		boolean coordinates = tok.getCoordinates() == this.getCoordinates();
		
		return letters && values && coordinates;
	}
	
	@Override
	public abstract IToken clone();
}