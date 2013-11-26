package ch.unibe.scg.team3.user;

import java.util.ArrayList;

import android.net.ParseException;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * The User registered on this app.
 * 
 * @author viviane
 *
 */
public class MainUser extends AbstractUser {
	
	private ArrayList<Friend> friends;
	private String passWord;

	public MainUser(String email, String userName, String passWord) {
		super(email, userName);
		friends = new ArrayList<Friend>();
		this.passWord=passWord;
	}
	
	public MainUser() {
		super();
	}

	@Override
	public void register(){//not yet usable
		if(registered==false){
			ParseUser user = new ParseUser();
			user.setUsername(super.userName);
			user.setPassword(passWord);
			user.setEmail(super.email);
			 
			user.signUpInBackground(new SignUpCallback() {

				@Override
				public void done(com.parse.ParseException e) {
					if (e == null) {
						registered=true;
					} else {
						//TODO signup went wrong
					}
					
				}
			});
		}
	}

}
