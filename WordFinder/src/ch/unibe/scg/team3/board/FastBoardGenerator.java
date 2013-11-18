package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import ch.unibe.scg.team3.game.Path;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.token.IToken;
import ch.unibe.scg.team3.token.Token;

public class FastBoardGenerator extends AbstractBoardGenerator {

	private final int minWords;
	private final WordlistHandler handler;

	public FastBoardGenerator(int size, WordlistHandler handler, int minWords) {
		super(size);
		this.handler = handler;
		this.minWords = minWords;
	}

	@Override
	protected void generate() {

		String firstWord = handler.getRandomWordFromWordlist();
		
		IToken firstToken = getRandomToken();
		Path<IToken> firstPath = new Path<IToken>();

		placeWord(firstWord, firstPath, firstToken);
		board.setPath(firstPath);

		int placedCount = 0;
		int boardloops = 0;
		int loops = 0;

		while (board.hasEmptyTokens()) {
			loops++;
			for (IToken tok : board) {
				boardloops++;
				if (!tok.isEmpty()) {

					String letter = "" + tok.getLetter();
					String random = handler
							.getRandomWordFromDatabaseByLetterAndLength(letter, true);

					Path<IToken> path = new Path<IToken>();

					if (placeWord(random, path, tok)) {
						placedCount++;
						board.setPath(path);;
					}
//
//					if (occupied == minWords)
//						break;
				}
			}
		}
		System.out.println("loops: " + loops + " bloops: " + boardloops + " placed: " + placedCount + " first: " + firstToken.getCoordinates());
	}

	private boolean placeWord(String word, Path<IToken> path, IToken start) {

		if (path.length() == word.length())
			return true;

		char letter = word.charAt(path.length());

		if (!start.isEmpty() && start.getLetter() != letter) {
			return false;
		}

		IToken current = new Token(letter, valueOf(letter), start.getCoordinates());
		path.add(current);

		ArrayList<IToken> candidates = getNextPossibleTokens(start, path);

		if (candidates.isEmpty()) {
			path.removeLast();
			return false;
		}

		for (IToken next : candidates) {

			if (placeWord(word, path, next))
				return true;
		}

		path.removeLast();
		return false;
	}

	private ArrayList<IToken> getNextPossibleTokens(IToken start, Path<IToken> path) {

		ArrayList<IToken> candidates = getNeighbors(start);

		Iterator<IToken> iter = candidates.iterator();

		while (iter.hasNext()) {
			IToken token = iter.next();
			if (path.contains(token)) {
				iter.remove();
			}
		}

		return candidates;
	}

	private ArrayList<IToken> getNeighbors(IToken token) {

		Point center = token.getCoordinates();

		Point topleft = new Point(center.getX() - 1, center.getY() - 1);
		Point top = new Point(center.getX(), center.getY() - 1);
		Point topright = new Point(center.getX() + 1, center.getY() - 1);
		Point right = new Point(center.getX() + 1, center.getY());
		Point bottomright = new Point(center.getX() + 1, center.getY() + 1);
		Point bottom = new Point(center.getX(), center.getY() + 1);
		Point bottomleft = new Point(center.getX() - 1, center.getY() + 1);
		Point left = new Point(center.getX() - 1, center.getY());

		Point[] candidates = { topleft, top, topright, right, bottomright, bottom, bottomleft, left };
		ArrayList<IToken> neighbors = new ArrayList<IToken>();

		for (Point point : candidates) {

			if (board.inRange(point.getX(), point.getY())) {
				IToken tok = board.getToken(point);
				neighbors.add(tok);
			}
		}

		return neighbors;
	}
	
	private IToken getRandomToken() {
		Random random  = new Random();
		
		int row = random.nextInt(board.getSize());
		int col = random.nextInt(board.getSize());
		
		return board.getToken(col, row);
	}

}
