package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.parseQueryAdapter.FriendRequestsAdapter;
import ch.unibe.scg.team3.parseQueryAdapter.FriendsAdapter;

import com.parse.*;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class FriendsActivity extends Activity {

	private List<ParseUser> friends;
	private List<ParseUser> requests;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		friends = new ArrayList<ParseUser>();
		requests = new ArrayList<ParseUser>();
		
		EditText editText = (EditText) findViewById(R.id.email_edit);
	}

	@Override
	protected void onResume() {
		super.onResume();

		getFriendRequests();
		FriendRequestsAdapter requestAdapter = new FriendRequestsAdapter(this, R.id.request_list, requests);
		
		getFriends();
		FriendsAdapter friendsAdapter = new FriendsAdapter(this, R.id.friends_list, friends);

		ListView friendList = (ListView) findViewById(R.id.friends_list);
		ListView requestList = (ListView) findViewById(R.id.request_list);
		friendList.setAdapter(friendsAdapter);
		requestList.setAdapter(requestAdapter);
	}

	private void getFriends() {
		ParseUser me = ParseUser.getCurrentUser();
		if (me != null) {
			String id = me.getObjectId();

			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"Friendship");
			query.whereEqualTo("user_id", id);
			
			query.findInBackground(new FindCallback<ParseObject>(){

				@Override
				public void done(List<ParseObject> friendships, ParseException arg1) {
					for(ParseObject friendship : friendships){
						
						String friend_id = friendship.getString("friend_id");
						ParseQuery<ParseUser> query = ParseUser.getQuery();
						
						try {
							query.get(friend_id);
							friends.add(query.getFirst());
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		
	}

	private void getFriendRequests() {
		ParseUser me = ParseUser.getCurrentUser();
		if (me != null) {
			String id = me.getObjectId();

			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
					"Request");
			query.whereEqualTo("subject_id", id);
			query.findInBackground(new FindCallback<ParseObject>(){

				@Override
				public void done(List<ParseObject> requestList, ParseException arg1) {
					for(ParseObject request : requestList){
						
						String initiatorId = request.getString("initiator_id");
						ParseQuery<ParseUser> query = ParseUser.getQuery();
						
						try {
							query.get(initiatorId);
							requests.add(query.getFirst());
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}

	public void addFriend(View view) {
		final EditText email_view = (EditText) findViewById(R.id.emailToAdd);
		String email = email_view.getText().toString();

		ParseUser me = ParseUser.getCurrentUser();

		final String myId = me.getObjectId();

		final ParseQuery<ParseUser> user = ParseQuery.getUserQuery();
		user.whereEqualTo("email", email);

		user.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> requests, ParseException e) {
				if (e == null) {
					String userId = null;
					try {
						userId = (String) user.getFirst().getObjectId();
					} catch (ParseException e1) {
						
						int code = e1.getCode();
				    	String message="someting is wrong";
				    	if(code==ParseException.OBJECT_NOT_FOUND){message="could not find user";}
				    	if(code==ParseException.CONNECTION_FAILED){message="Unable to connect to the internet";}
				    	
				    	System.out.println(code);
				    	
				    	AlertDialog.Builder alert = new AlertDialog.Builder(FriendsActivity.this);

						alert.setTitle("Error");
						alert.setMessage(message);

						alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
							}
						});

						alert.show();

					}
					if (userId == null) {
						Toast.makeText(getApplicationContext(),
								"Could not send the request", Toast.LENGTH_LONG)
								.show();
						return;
					}
					ParseObject request = new ParseObject("Request");
					request.put("initiator_id", myId);
					request.put("subject_id", userId);
					request.saveInBackground();
					email_view.setText("");
					email_view.clearFocus();
					Toast.makeText(getApplicationContext(), "Request sent",
							Toast.LENGTH_LONG).show();

				} else {
					Toast.makeText(getApplicationContext(),
							"Could not send the request", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		});
	}

}
