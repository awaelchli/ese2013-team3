package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.LinkedList;

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

		placeWord(firstWord, new Path<IToken>(), board.getToken(0, 0));

		int placedCount = 0;
		// while(placedCount < minWords){

		for (IToken tok : board) {
			if (!tok.isEmpty()) {

				String letter = "" + tok.getLetter();
				String random = handler.getRandomWordFromDatabaseByLetterAndLength(letter, true);

				Path<IToken> path = new Path<IToken>();

				if (placeWord(random, path, tok))
					placedCount++;

				if (placedCount == minWords)
					break;
			}
		}
		// }
	}

	private boolean placeWord(String word, Path<IToken> path, IToken start) {

		char letter = word.charAt(path.length());

		if (!start.isEmpty() && start.getLetter() != letter) {
			return false;
		}
		path.add(new Token(letter, valueOf(letter), start.getCoordinates()));

		ArrayList<IToken> candidates = getNextPossibleTokens(start, path);

		if (candidates.isEmpty())
			return false;

		for (IToken next : candidates) {

			if (placeWord(word, path, next))
				return true;
		}
		return false;
	}

	private ArrayList<IToken> getNextPossibleTokens(IToken start, Path<IToken> path) {

		ArrayList<IToken> candidates = getNeighbors(start);

		for (IToken token : candidates) {
			if (path.contains(token)) {
				candidates.remove(token);
			}
		}
		return candidates;
	}

	private IToken getRandomNonEmptyNeighbor(IToken start) {
		return null;
	}

	private boolean allNeighborsEmpty(IToken start) {
		return false;
	}

	private IToken getRandomNeighbor(IToken token) {
		return null;
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

}
