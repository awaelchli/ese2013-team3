package wordlist;

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
		content.add(word);
	}

	public void removeWord(String word) {
		content.remove(word);
	}

	public void sort() {
		Collections.sort(content);
	}
	@Override
	public String toString() {
		return this.name;
//		String wordlist = this.name;
//		for (int i = 0; i < content.size(); i++) {
//			wordlist = wordlist + ", " + content.get(i);
//		}
//		return wordlist;
	}

	public String getName() {
		return this.name;
	}

	public String getContent() {
		StringBuilder sb = new StringBuilder();
		
		for(String word : content){
			sb.append(word);
			sb.append(WORD_SEPARATOR);
		}
		
//		for (int i = 1; i < content.size(); i++) {
//			wordlist = wordlist + ", " + content.get(i);
//		}
		return sb.toString();
	}

//	public void setName(String name) {
//		this.name = name;
//	}
//	public void setWordlistFromString(String wordlist){
//		content = new ArrayList<String>();
//		String[] tmp = wordlist.split(";");
//		for(int i = 0;i<tmp.length;i++){
//			content.add(tmp[i]);
//		}
//	}

	
}
