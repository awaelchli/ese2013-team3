package ch.unibe.scg.team3.wordlist;

import java.util.ArrayList;

/**
 * 
 * @author nils
 * @author adrian
 * 
 */
public class Wordlist {

	public static final char WORD_SEPARATOR = ';';

	private final String name;
	private ArrayList<String> content;

	public Wordlist(String name) {
		this.name = name;
		this.content = new ArrayList<String>();
	}

	public Wordlist(String name, ArrayList<String> content) {
		this(name);
		this.content = content;
	}

	public void addWord(String word) {
		content.add(word);
	}

	public boolean contains(String word) {
		return content.contains(word);
	}

	public void removeWord(String word) {
		content.remove(word);
	}

	@Override
	public String toString() {
		return this.name;
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

	public ArrayList<String> getContent() {
		return content;
	}
}
