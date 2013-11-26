package ch.unibe.scg.team3.user;

public class Friend extends AbstractUser {

	public Friend(String email, String userName) {
		super(email, userName);
	}

	public Friend() {
		super();
	}


	public void setFriendId(String friendid) {
		setUserId(friendid);
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		
	}

}
