package ch.unibe.scg.team3.wordlist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author adrian
 *
 */
public class WordlistBuilder {
	
	private final Wordlist wordlist;

	public WordlistBuilder(String name){
		this.wordlist = new Wordlist(name);
	}
	
	public void addWords(String words){
		Scanner scan = new Scanner(words);
		scan.useDelimiter("" + Wordlist.WORD_SEPARATOR);
		
		while(scan.hasNext()){
			wordlist.addWord(scan.next());
		}
	}
	
	public void addWords(File file){
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			//TODO: implement
		}
		
		while(null != scan && scan.hasNextLine()){
			wordlist.addWord(scan.nextLine());
		}
	}

	public Wordlist getWordlist() {
		return this.wordlist;
	}
}
