package ch.unibe.scg.team3.board;

public class DefaultLetterMeter extends AbstractLetterMeter {

	@Override
	protected void init() {
		
		table.put('e', 1);
		table.put('n', 2);
		table.put('i', 2);
		table.put('s', 2);
		table.put('r', 2);
		table.put('a', 2);
		table.put('t', 2);
		table.put('d', 3);
		table.put('h', 3);
		table.put('u', 4);
		table.put('l', 3);
		table.put('c', 4);
		table.put('g', 4);
		table.put('m', 4);
		table.put('o', 3);
		table.put('b', 5);
		table.put('w', 5);
		table.put('f', 5);
		table.put('k', 6);
		table.put('z', 6);
		table.put('p', 6);
		table.put('v', 7);
		table.put('j', 7);
		table.put('y', 6);
		table.put('x', 8);
		table.put('q', 9);
	}

}
