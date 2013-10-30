package ch.unibe.scg.team3.localDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ch.unibe.scg.team3.wordfinder.R;
import ch.unibe.scg.team3.wordlist.Wordlist;
import ch.unibe.scg.team3.wordlist.WordlistBuilder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WordlistManager extends DataManager {

	public WordlistManager(Context context) {
		super(context);

	}

	public void addEmptyWordlist(String Name) throws WordlistAlreadyInDataBaseException {
		if (!isWordlistInDatabase(Name)){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("INSERT INTO Dictionary VALUES(NULL,'" + Name + "')");
		db.close();
		}
		else throw new WordlistAlreadyInDataBaseException("Wordlist already exists");
	}
	
	public void addWordlistByFileInRaw(String Name, String Filename) throws WordlistAlreadyInDataBaseException {
		addEmptyWordlist(Name);
		int resID = context.getResources().getIdentifier(Filename, "raw", context.getPackageName());
		int wordlistId = getWordlistId(Name);
		InputStream inputStream = context.getResources().openRawResource(resID);
		InputStreamReader inputreader = new InputStreamReader(inputStream);
	    BufferedReader buffreader = new BufferedReader(inputreader);
	    String word;
	    SQLiteDatabase db = helper.getWritableDatabase();
	    db.beginTransaction();
	    try {
			while((word = buffreader.readLine()) != null) {
				addWordToOpenDb(word, wordlistId, db);
			}
			db.setTransactionSuccessful();
		}
	    
	    catch (IOException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}

	public void setWordToWordlist(String word, String wordlistname) {
		int wordlistId = getWordlistId(wordlistname);
		SQLiteDatabase db = helper.getWritableDatabase();
		addWordToOpenDb(word, wordlistId, db);
		db.close();
	}

	private void addWordToOpenDb(String word, int wordlistId, SQLiteDatabase db) {
		if (word.length() < 5 && word.length() > 0) {
			// System.out.println(words[i]);
			db.execSQL("INSERT INTO " + word.substring(0, 1).toLowerCase()
					+ "short VALUES(NULL, '" + wordlistId + "', '" + word
					+ "')");

		} else if (word.length() > 5) {
			// System.out.println(words[i]);
			db.execSQL("INSERT INTO " + word.substring(0, 1).toLowerCase()
					+ "long VALUES(NULL, '" + wordlistId + "', '" + word
					+ "')");

		}
	}

	public void removeWordlist(String name) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE IF EXISTS FROM Dictionary WHERE Name = '" + name
				+ "'");
		db.close();
	}

	public void removeWordFromWordlist(String word, String wordlist) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE IF EXISTS FROM" + word.substring(0, 1).toLowerCase()
				+ "WHERE Name = '" + wordlist + "' AND content = '" + word
				+ "'");
		db.close();
	}

	
	public boolean isWordInWordlist(String word, String wordlist) {

		if (word.length() == 0)
			return false;
		String table = word.substring(0, 1).toLowerCase();
		if (word.length() < 5) {
			table += "short";
		} else {
			table += "long";
		}
		int wordlistId = getWordlistId(wordlist);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT content FROM " + table
				+ " WHERE Dictionary = ? AND content = ?", new String[] {
				Integer.toString(wordlistId), word.toLowerCase() });
		if (c.getCount() != 0) {
			c.close();
			db.close();
			return true;
		}
		db.close();
		return false;

	}
	
	public boolean isWordlistInDatabase(String wordlistname){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT _id FROM Dictionary WHERE Name = ?", new String[] {
				wordlistname});
		if (c.getCount() != 0) {
			c.close();
			db.close();
			return true;
		}
		db.close();
		return false;

	}
	
	public int getWordlistId(String wordlistname){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT _id FROM Dictionary WHERE Name = ?", new String[] {
				wordlistname});
		if (c.getCount() != 0) {
			c.moveToFirst();
			int id = c.getInt(0);
			c.close();
			db.close();
			return id;
		}
		db.close();
		return 0;
		
	}
	public CharSequence[] getWordlists(){
		CharSequence[] lists;
		ArrayList<String> tmp = new ArrayList<String>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT _id , Name FROM Dictionary", null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			while(!c.isAfterLast()){
			tmp.add(c.getString(1));
			c.moveToNext();}
			
			c.close();
			db.close();
			return (CharSequence[]) tmp.toArray(new CharSequence[tmp.size()]);
		}
		db.close();
		return null;
		
	}
	public CharSequence[] getWordlistids(){
		CharSequence[] lists;
		ArrayList<String> tmp = new ArrayList<String>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT _id , Name FROM Dictionary", null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			while(!c.isAfterLast()){
			tmp.add(Integer.toString(c.getInt(0)));
			c.moveToNext();}
			c.close();
			db.close();
			return (CharSequence[]) tmp.toArray(new CharSequence[tmp.size()]);
		}
		db.close();
		return null;
		
	}

	public void copyDB() throws IOException {
		helper.importDatabase();
		SQLiteDatabase db = helper.getWritableDatabase();
		db.close();
	}

}
