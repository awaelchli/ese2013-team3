package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.Random;

import ch.unibe.scg.team3.token.Token;

/**
 * @author faerber
 * 
 * This BoardGenerator randomly picks from 4 different boards that all contain at least 10
 * words from the /res/raw/englishreduced.txt text file.
 * The only purpose of this generator is to present an easily playable board to the customer.
 * 
 */
public class CustomBoardGenerator extends AbstractBoardGenerator {

	ArrayList<char[]> customBoards;
	
	public CustomBoardGenerator(int size) {
		super(size);
		this.customBoards = new ArrayList<char[]>();
		char[] board1 = new char[] {'o','t','g','r','l','e',
				'w','f','i','n','e','a','e','k','a','m','d','k',
				'o','n','o','s','l','c','t','w','y','u','b','a',
				'p','a','o','r','d','x'};
		this.customBoards.add(board1);
		char[] board2 = new char[] {'c','y','o','m','a','e',
				'h','o','n','g','m','n','w','p','m','a','o','l',
				'e','r','e','p','a','y','g','i','l','e','t','l',
				't','a','k','r','s','u'};
		this.customBoards.add(board2);
		char[] board3 = new char[] {'e','q','r','t','h','e',
				'n','g','m','o','w','r','o','f','n','e','y','n',
				'u','i','r','e','t','u','g','f','o','n','c','h',
				'h','x','f','f','d','o'};
		this.customBoards.add(board3);
		char[] board4 = new char[] {'i','h','s','y','w','e',
				'p','l','a','n','g','s','i','g','u','a','e','t',
				'p','m','r','e','s','a','b','o','i','t','t','e',
				'r','w','n','f','i','x'};
		this.customBoards.add(board4);
	}

	@Override
	public void generate() {
		
		Random r = new Random();
		char[] customBoard = this.customBoards.get((r.nextInt(1000))%4);

		for (int i = 0; i < this.board.getSize(); i++) {
			for (int j = 0; j < this.board.getSize(); j++) {

				char letter = (char) customBoard[i*6+j];
				Token tok = new Token(letter, valueOf(letter));
				this.board.setToken(tok, j, i);
			}

		}
	}

}