package ch.unibe.scg.team3.user;

import com.parse.ParseObject;


/** 
* 
* @author viviane
*/
public abstract class AbstractUser {
	
	protected String email;
	protected String userName;
	protected int UserID;
	protected boolean registered;

	public AbstractUser(String email, String userName) {
		//TODO set the user ID
		this.email=email;
		this.userName=userName;
		this.registered=false;
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

	public int getUserID() {
		return UserID;
	}

	public abstract void register();
}
