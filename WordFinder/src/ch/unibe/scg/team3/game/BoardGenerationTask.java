package ch.unibe.scg.team3.game;

import android.os.AsyncTask;
import ch.unibe.scg.team3.board.AbstractBoardGenerator;
import ch.unibe.scg.team3.board.Board;

public class BoardGenerationTask extends AsyncTask<AbstractBoardGenerator, Void, Board> {

	/**
	 * The board for the game gets generated in the background
	 */
	protected Board doInBackground(AbstractBoardGenerator... args) {

		AbstractBoardGenerator gen = args[0];
		return gen.getBoard();
	}
}
