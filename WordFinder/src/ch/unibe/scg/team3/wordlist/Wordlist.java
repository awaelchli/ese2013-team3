package ch.unibe.scg.team3.wordlist;

import java.util.ArrayList;
import java.util.Collections;

import ch.unibe.scg.team3.gameui.IWordlistObserver;

/**
 * 
 * @author nils
 * @author adrian
 * 
 */
public class Wordlist implements IObservable {

	public static final char WORD_SEPARATOR = ';';

	private final String name;
	private ArrayList<String> content;

	private ArrayList<IWordlistObserver> observers;

	public Wordlist(String name) {
		this.name = name;
		this.content = new ArrayList<String>();
		observers = new ArrayList<IWordlistObserver>();
	}

	public Wordlist(String name, ArrayList<String> content) {
		this(name);
		this.content = content;
		//notifyObserver();
	}

	public void addWord(String word) {
		content.add(word);
		notifyObserver();
	}

	public boolean contains(String word) {
		return content.contains(word);
	}

	public void removeWord(String word) {
		content.remove(word);
		notifyObserver();
	}

	private void sort() {
		Collections.sort(content);
		notifyObserver();
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getFormattedList(String delimeter) {
		// sort();
		StringBuilder sb = new StringBuilder();

		for (String word : content) {
			sb.append(word);
			sb.append(delimeter);
		}
		return sb.toString();
	}

	public String getName() {
		return this.name;
	}

	public int getSize() {
		return this.content.size();
	}

	public String getWord(int position) {
		return content.get(position);
	}

	public String getContent() {
		return getFormattedList("" + WORD_SEPARATOR);
	}

	@Override
	public void addObserver(IWordlistObserver o) {
		observers.add(o);
	}

	@Override
	public void notifyObserver() {

		for (IWordlistObserver o : observers) {
			o.update(this);
		}

	}
}
