package ch.unibe.scg.team3.gameui;

public interface IObservable {

	public void notifyObserver();
	public void addObserver(IBoardObserver o);
}
