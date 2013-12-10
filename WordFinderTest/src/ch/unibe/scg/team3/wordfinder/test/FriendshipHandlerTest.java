package ch.unibe.scg.team3.wordfinder.test;

import java.io.IOException;
import java.util.List;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.test.AndroidTestCase;

import ch.unibe.scg.team3.localDatabase.DataHandler;
import ch.unibe.scg.team3.localDatabase.FriendshipHandler;
import ch.unibe.scg.team3.localDatabase.UserHandler;
import ch.unibe.scg.team3.sharingService.Friendship;
import ch.unibe.scg.team3.user.User;
import ch.unibe.scg.team3.wordfinder.LoginActivity;
import ch.unibe.scg.team3.wordfinder.R;

public class FriendshipHandlerTest extends AndroidTestCase implements
		IDataHandlerTest {


	private FriendshipHandler friendshipHandler;
	private UserHandler userHandler;

	public void testTestAddRemoveFriend() {
		String userId="uRUAvXdqpb";
		String friendId="dF3mG5wK9k";
		String objectId="YPl0S2DWmz";
		Friendship friendship = new Friendship(objectId, userId, friendId);
		friendshipHandler.setFriendship(friendship);
		
		List<Friendship> friendships = friendshipHandler.getFriendships();
		
		
		assertTrue(friendshipHandler.isFriendshipinDb(objectId));
		
//		List<Friendship> friendships = friendshipHandler.getFriendships();
		assertEquals(friendships.size(), 1);
		assertEquals(friendships.get(0).getFriendId(), friendId);
		assertEquals(friendships.get(0).getUserId(), userId);
		
		friendshipHandler.remove(friendship);
		friendships = friendshipHandler.getFriendships();
		assertFalse(friendshipHandler.isFriendshipinDb(objectId));
		assertEquals(friendships.size(), 0);
		
	}

	
	public void setUp() {
		friendshipHandler = new FriendshipHandler(mContext.getApplicationContext());
		userHandler=new UserHandler(mContext.getApplicationContext());
		try {
			friendshipHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		android.preference.PreferenceManager.setDefaultValues(
				mContext.getApplicationContext(), R.xml.preferences, false);
		Parse.initialize(mContext, "ORYli0X0QqbH3Oefe0wvI2TsYa4d4Kw7sYKZFYuK",
						"FYUWqwq1E0VlFkVUXs6Fus1OZUN6CfqJo81EPbTJ");
		
		initUsers();
		
	
	}


	private void initUsers() {
		User friend2 = new User("uRUAvXdqpb", "friend2", "friend2@test.com");
		userHandler.insertUser(friend2);
		User friend1 = new User("dF3mG5wK9k", "friend1", "friend1@test.com");
		userHandler.insertUser(friend1);
	}


	
}
