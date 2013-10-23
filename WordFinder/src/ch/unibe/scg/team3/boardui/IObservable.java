package ch.unibe.scg.team3.boardui;

public interface IObservable {

	public void notifyObserver();
	public void addObserver(IBoardObserver o);
}
