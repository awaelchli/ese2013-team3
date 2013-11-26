package ch.unibe.scg.team3.user;

import com.parse.ParseObject;


/** 
* 
* @author viviane
*/
public abstract class AbstractUser {
	
	private String email;
	private String userName;
	private String userID;

	public AbstractUser(String email, String userName) {
		//TODO set the user ID
		this.email=email;
		this.userName=userName;
	}

	public AbstractUser() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		//TODO assert email is an mail address
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserID() {
		return userID;
	}
	
	public void setUserId(String userId){
		this.userID = userId;
	}

	public ParseObject getAsParseObject(){
		ParseObject parse= new ParseObject("User");
		parse.put("ID", userID);
		parse.put("userName", userName);
		parse.put("email", email);
		parse.saveInBackground();
		return parse;
		
	}
}
