package ch.unibe.scg.team3.game;

import java.io.File;

import ch.unibe.scg.team3.board.*;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.wordlist.*;

public class GameManager {
	
	private final Board board;
	private final Wordlist wordlist;
	private final Wordlist found;
	
	public GameManager(int boardSize){
		board = new Board(boardSize);
		wordlist = loadDefaultWordlist();
		found = new Wordlist("Found words");
	}

	private Wordlist loadDefaultWordlist() {
		WordlistBuilder builder = new WordlistBuilder("English");
		builder.addWords(new File("res/raw/english.txt"));
		return builder.getWordlist();
	}
	
	public void submitWord(Point[] path) throws SelectionException {
		
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
