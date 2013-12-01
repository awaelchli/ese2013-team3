package ch.unibe.scg.team3.user;

import com.parse.ParseObject;


/** 
* 
* @author viviane
*/
public abstract class AbstractUser {
	
	protected String email;
	protected String userName;
	protected String userID;

	public AbstractUser(String email, String userName, String userID) {
		this.email=email;
		this.userName=userName;
		this.userID = userID;
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
}