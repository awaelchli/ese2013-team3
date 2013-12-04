package ch.unibe.scg.team3.sharingService;

import ch.unibe.scg.team3.user.User;

public class Friendship {
	private String friendshipId;
	private String userId;
	private String friendId;

	public Friendship(String objectId, String userId, String friendId) {
		this.friendshipId = objectId;
		this.userId = userId;
		this.friendId = friendId;
	}

    
	public String getFriendshipId() {
		return friendshipId;
	}
	public void setFriendshipId(String objectId) {
		this.friendshipId = objectId;

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Friendship ))
		{
			return false;
		}
		Friendship other = (Friendship) o;
		if(!other.getFriendshipId().equals(getFriendshipId())){
			return false;
		}
		return true;
	}

}
