package ch.unibe.scg.team3.wordfinder.test;

import java.io.IOException;
import java.util.List;

import com.parse.Parse;

import android.test.AndroidTestCase;

import ch.unibe.scg.team3.localDatabase.DataHandler;
import ch.unibe.scg.team3.localDatabase.FriendshipHandler;
import ch.unibe.scg.team3.sharingService.Friendship;
import ch.unibe.scg.team3.wordfinder.R;

public class FriendshipHandlerTest extends AndroidTestCase implements
		IDataHandlerTest {


	protected FriendshipHandler friendshipHandler;

	public void testTestAddRemoveFriend() {
		Friendship friendship = new Friendship("objectId", "userId",
				"friendId");
		friendshipHandler.setFriendship(friendship);
		assertTrue(friendshipHandler.isFriendshipinDb("objectID"));
		List<Friendship> friendships = friendshipHandler.getFriendships();
		assertEquals(friendships.size(), 1);
		assertEquals(friendships.get(0).getObjectId(), "objectId");
		assertEquals(friendships.get(0).getFriendId(), "friendId");
		assertEquals(friendships.get(0).getUserId(), "userId");
		friendshipHandler.remove(friendship);
		friendships = friendshipHandler.getFriendships();
		assertFalse(friendshipHandler.isFriendshipinDb("objectID"));
		assertEquals(friendships.size(), 0);
		
	}

	
	public void setUp() {
		//wordlistHandler = new WordlistHandler(mContext.getApplicationContext());
		friendshipHandler = new FriendshipHandler(
				mContext.getApplicationContext());
		try {
			friendshipHandler.copyDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		android.preference.PreferenceManager.setDefaultValues(
				mContext.getApplicationContext(), R.xml.preferences, false);
		Parse.initialize(mContext, "ORYli0X0QqbH3Oefe0wvI2TsYa4d4Kw7sYKZFYuK",
						"FYUWqwq1E0VlFkVUXs6Fus1OZUN6CfqJo81EPbTJ");
		
		
	
	}


	
}
