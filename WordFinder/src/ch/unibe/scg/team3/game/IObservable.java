package ch.unibe.scg.team3.game;

/**
 * @author adrian
 */
public interface IObservable {
	
	public void addObserver(IGameObserver observer);
	
	public void removeObserver(IGameObserver observer);
	
	public void notifyObservers();
}
