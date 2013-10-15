package wordlist;

import java.util.Scanner;

/**
 * 
 * @author adrian
 *
 */
public class WordListBuilder {
	
	private final Wordlist wordlist;

	public WordListBuilder(String name){
		this.wordlist = new Wordlist(name);
	}
	
	public void addWords(String words){
		Scanner scan = new Scanner(words);
		scan.useDelimiter("" + Wordlist.WORD_SEPARATOR);
		
		while(scan.hasNext()){
			wordlist.addWord(scan.next());
		}
	}

	public Wordlist getWordlist() {
		return this.wordlist;
	}
}
