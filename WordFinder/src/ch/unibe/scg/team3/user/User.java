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
public class User extends AbstractUser {

	public User(String userid, String userName,String email) {
		super(userid, userName, email);
		
	}
	
	public User() {
		super();
	}



}
