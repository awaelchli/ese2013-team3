package ch.unibe.scg.team3.sharingService;

public class Friendship {
	private String objectId;
	private String userId;
	private String friendId;

	public Friendship(String objectId, String userId, String friendId) {
		this.objectId = objectId;
		this.userId = userId;
		this.friendId = friendId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
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

}
