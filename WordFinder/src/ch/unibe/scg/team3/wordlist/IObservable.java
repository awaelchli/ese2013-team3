package ch.unibe.scg.team3.wordlist;

import ch.unibe.scg.team3.gameui.IWordlistObserver;

public interface IObservable {

	public void notifyObserver();
	public void addObserver(IWordlistObserver o);
}
