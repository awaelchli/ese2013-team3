package ch.unibe.scg.team3.wordlist;

import java.util.ArrayList;
import java.util.Collections;

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
	
	public Wordlist(String name){
		this.name = name;
		this.content = new ArrayList<String>();
	}

	public Wordlist(String name, ArrayList<String> content) {
		this(name);
		this.content = content;
	}

	public void addWord(String word) {
		if(!content.contains(word)){
			content.add(word);
		}
	}

	public void removeWord(String word) {
		content.remove(word);
	}

	private void sort() {
		Collections.sort(content);
	}
	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return this.name;
	}
	
	public int getSize(){
		return this.content.size();
	}

	public String getContent() {
		sort();
		StringBuilder sb = new StringBuilder();
		
		for(String word : content){
			sb.append(word);
			sb.append(WORD_SEPARATOR);
		}
		return sb.toString();
	}	
}
