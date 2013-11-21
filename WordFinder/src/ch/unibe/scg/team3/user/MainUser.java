package ch.unibe.scg.team3.user;

import java.util.ArrayList;

import com.parse.ParseObject;

/**
 * The User registered on this app.
 * 
 * @author viviane
 *
 */
public class MainUser extends AbstractUser {
	
	private ArrayList<Friend> friends;

	public MainUser(String email, String userName) {
		super(email, userName);
		friends = new ArrayList<Friend>();
	}
	
	@Override
	public ParseObject getAsParseObject(){//not yet usable
		ParseObject parse=super.getAsParseObject();
		parse.put("friends", friends);
		return parse;
	}

}
