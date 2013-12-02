package ch.unibe.scg.team3.localDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.user.AbstractUser;
import ch.unibe.scg.team3.user.User;
import android.content.Context;
/**
 * 
 * @author nils
 *
 */
public class FriendsHandler extends DataHandler{
	FriendshipHandler friendshipHandler;
	UserHandler userHandler;
	public FriendsHandler(Context context) {
		super(context);
		friendshipHandler= new FriendshipHandler(context);
		userHandler = new UserHandler(context);
	}
	
	public ArrayList<User> getFriends(String userid){
		ArrayList<User> friends = new ArrayList<User>();
		List<String> list = friendshipHandler.getFriendshipsOfUser(userid);
		for(String listitem:list){
			friends.add(userHandler.getUser(listitem));
		}
		
		return friends;
	}
	
	
	

}
