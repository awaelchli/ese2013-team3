package ch.unibe.scg.team3.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import ch.unibe.scg.team3.game.Path;
import ch.unibe.scg.team3.localDatabase.MySQLiteHelper;
import ch.unibe.scg.team3.localDatabase.WordlistHandler;
import ch.unibe.scg.team3.token.*;

/**
 * This generator creates a board containing words from the selected wordlist.
 * The words are placed such that they overlap each other. However, the
 * generator puts more short words on the board than long ones.
 * 
 * @author viviane
 * @author adrian
 */
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

		// TODO: make a list containing all placed words and check...

		int placedCount = 0;

		while (placedCount < minWords) {

			IToken tok = getRandomToken();
			if (tok.isEmpty()) {
				char letter = getRandomLetter();
				tok = new Token(letter, valueOf(letter), tok.getCoordinates());
			}

			String letter = "" + tok.getLetter();
			String word = handler.getRandomWordFromDatabaseByLetterAndLength(letter, false);

			Path<IToken> path = new Path<IToken>();

			if (placeWord(word, path, tok.clone())) {

				placedCount++;
				board.setPath(path);

				placedCount += placeSimilarWordsOnPath(path);

			}

		}

		fillEmptyTokens();
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

		// try placing on matching letters first
		Iterator<IToken> iter = candidates.iterator();
		while (iter.hasNext()) {
			IToken candidate = iter.next();

			if (candidate.letterEquals(current)) {
				if (placeWord(word, path, candidate)) {
					return true;
				} else {
					iter.remove();
					candidates.remove(candidate);
				}
			}
		}

		// try the rest
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
				candidates.remove(token);
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

	private int placeSimilarWordsOnPath(Path<IToken> path) {
		int placedCount = 0;

		Iterator<IToken> iterator = path.iterator();

		if (path.isEmpty())
			return 0;

		IToken current = iterator.next();

		while (iterator.hasNext()) {
			IToken next = iterator.next();

			String suffix = current.getLetter() + "" + next.getLetter();
			placedCount += placeSimilarWords(suffix, current.clone());

			current = next;
		}

		return placedCount;
	}

	private int placeSimilarWords(String suffix, IToken start) {
		int placedCount = 0;

		ArrayList<String> similarWords = handler.getWordsStartingWith(suffix);
		Path<IToken> path = new Path<IToken>();

		Iterator<String> iterator = similarWords.iterator();

		while (iterator.hasNext()) {
			String like = iterator.next();

			if (placeWord(like, path, start)) {
				placedCount++;
				board.setPath(path);

				iterator.remove();
			}
			path.clear();
		}
		return placedCount;
	}

	private IToken getRandomToken() {
		Random random = new Random();

		int row = random.nextInt(board.getSize());
		int col = random.nextInt(board.getSize());

		return board.getToken(col, row);
	}

	private void fillEmptyTokens() {

		for (IToken tok : board) {

			if (tok.isEmpty()) {
				char letter = getRandomLetter();
				board.setToken(new Token(letter, meter.getValue(letter), tok.getCoordinates()));
			}
		}

	}

	private char getRandomLetter() {
		String alphabet = MySQLiteHelper.ALPHABET;
		Random rnd = new Random();

		int rndIndex = rnd.nextInt(alphabet.length());
		char letter = alphabet.charAt(rndIndex);
		return letter;
	}

}
