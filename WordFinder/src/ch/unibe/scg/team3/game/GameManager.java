package ch.unibe.scg.team3.game;

import java.io.File;
import java.util.List;

import ch.unibe.scg.team3.board.Board;
import ch.unibe.scg.team3.board.RandomBoardGenerator;
import ch.unibe.scg.team3.board.WordSelection;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.Token;
import ch.unibe.scg.team3.wordlist.Wordlist;
import ch.unibe.scg.team3.wordlist.WordlistBuilder;

public class GameManager {
	
	private final Board board;
	private final Wordlist wordlist;
	private final Wordlist found;
	
	public GameManager(int boardSize){
//		RandomBoardGenerator rnd = new RandomBoardGenerator(boardSize);
//		rnd.generate();
//		board = rnd.getBoard();
		board = new Board(boardSize);
		board.setToken(new Token('c', 1), 0, 0);
		board.setToken(new Token('a', 1), 0, 1);
		board.setToken(new Token('r', 1), 0, 2);
		
		wordlist = loadDefaultWordlist();
		found = new Wordlist("Found words");
	}

	private Wordlist loadDefaultWordlist() {
		WordlistBuilder builder = new WordlistBuilder("English");
		builder.addWords(new File("res/raw/english.txt"));
		return builder.getWordlist();
	}
	
	public void submitWord(List<Point> path) throws SelectionException {
		
		if(!PathChecker.check(path)){
			throw new PathNotConnectedException();
		}
		
		WordSelection selection = new WordSelection();
		
		for(Point p : path){
			IToken tok = board.getToken(p.getX(), p.getY());
			selection.addToken(tok);
		}
		
		String word = selection.toString();
		
		if(!wordlist.contains(word)){
			throw new WordNotFoundException();
		}
		
		if(!found.contains(word)){
			throw new WordAlreadyFoundException();
		}
		
		found.addWord(word);
	}
}
