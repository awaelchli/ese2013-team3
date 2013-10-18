package ch.unibe.scg.team3.wordlist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ch.unibe.scg.team3.wordfinder.R;

import android.app.Activity;

/**
 * 
 * @author adrian
 *
 */
public class WordlistBuilder extends Activity{
	
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
	
	//not working
	public void initialize(){
		File file= new File(getResources().getResourceEntryName(R.raw.english));
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(null != scan && scan.hasNextLine()){
			wordlist.addWord(scan.nextLine());
		}
	}

	public Wordlist getWordlist() {
		return this.wordlist;
	}
}
