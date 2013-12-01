package ch.unibe.scg.team3.sharingService;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import ch.unibe.scg.team3.localDatabase.FriendshipHandler;
import ch.unibe.scg.team3.localDatabase.RequestHandler;
import ch.unibe.scg.team3.localDatabase.UserHandler;
import ch.unibe.scg.team3.user.User;

public class SyncDatabase {
	private UserHandler userHandler;
	private FriendshipHandler friendshipHandler;
	private RequestHandler requestHandler;
	public SyncDatabase(Context context) {
	userHandler = new UserHandler(context);
	friendshipHandler = new FriendshipHandler(context);
	requestHandler = new RequestHandler(context);
	}
	public void sync(){
		ParseQuery<ParseUser> query = new ParseQuery<ParseUser>(
				"_User");
		
		query.findInBackground(new FindCallback<ParseUser>(){

			@Override
			public void done(List<ParseUser> users, ParseException arg1) {
				List<User> usersonparse = new ArrayList<User>();
				for(ParseUser user : users){
					
					String user_id = user.getString("objectId");
					String username = user.getString("username");
					String email = user.getString("email");
					
					User dbuser = new User(username,email,user_id);
					usersonparse.add(dbuser);
					if(! userHandler.isUserinDb(user_id)){
					userHandler.setUser(dbuser);
					
					}
				}
				List<User> usersindb = userHandler.getUsers();
				usersindb.removeAll(usersonparse);
				
				for(User deleteduser : usersindb){
					userHandler.remove(deleteduser);
				}
			}
		});
	}
	public void syncRequests(){
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
				"Request");
		query.findInBackground(new FindCallback<ParseObject>(){

			@Override
			public void done(List<ParseObject> requestList, ParseException arg1) {
				List<Request> requestsonparse = new ArrayList<Request>();
				for(ParseObject request : requestList){
					String initiatorId = request.getString("initiator_id");
					String subjectId = request.getString("subject_id");
					String objectId = request.getObjectId();
					Request dbrequest = new Request(objectId, initiatorId, subjectId);
					requestsonparse.add(dbrequest);
					if(! requestHandler.isRequestinDb(objectId)){
						requestHandler.setRequest(dbrequest);
						
						}
					}
					List<Request> requestsindb = requestHandler.getRequests();
					requestsindb.removeAll(requestsonparse);
					
					for(Request deletedrequest : requestsindb){
						requestHandler.remove(deletedrequest);
					
				}
			}
		});
		
	}
	
	public void syncFriendships(){
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
				"Friendship");
		query.findInBackground(new FindCallback<ParseObject>(){

			@Override
			public void done(List<ParseObject> friendshipList, ParseException arg1) {
				List<Friendship> friendshipsonparse = new ArrayList<Friendship>();
				for(ParseObject friendship : friendshipList){
					String userId = friendship.getString("user_id");
					String friendId = friendship.getString("friend_id");
					String objectId = friendship.getObjectId();
					Friendship dbfriendship = new Friendship(objectId, userId, friendId);
					friendshipsonparse.add(dbfriendship);
					if(! friendshipHandler.isFriendshipinDb(objectId)){
						friendshipHandler.setFriendship(dbfriendship);
						
						}
					}
					List<Friendship> friendshipsindb = friendshipHandler.getFriendships();
					friendshipsindb.removeAll(friendshipsonparse);
					
					for(Friendship deletedfriendship : friendshipsindb){
						friendshipHandler.remove(deletedfriendship);
					
				}
			}
		});
		
	}

}