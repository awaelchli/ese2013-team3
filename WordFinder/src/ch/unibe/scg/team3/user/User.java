package ch.unibe.scg.team3.user;

/**
 * @author viviane
 */
public class User {

	protected String email;
	protected String username;
	protected String userId;

	public User(String userId, String username, String email) {
		this.email = email;
		this.username = username;
		this.userId = userId;
	}

	public User() {
		email = new String();
		username = new String();
		userId = new String();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		assert email.contains("@");
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserID() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) {
			return false;
		}
		User other = (User) o;
		if (!other.getUserID().equals(this.getUserID())) {
			return false;
		}
		return true;
	}
}