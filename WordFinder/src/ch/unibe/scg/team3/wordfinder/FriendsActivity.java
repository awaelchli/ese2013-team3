package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.game.SavedGame;
import ch.unibe.scg.team3.localDatabase.FriendsHandler;
import ch.unibe.scg.team3.localDatabase.FriendshipHandler;
import ch.unibe.scg.team3.localDatabase.UserHandler;
import ch.unibe.scg.team3.parseQueryAdapter.FriendRequestsAdapter;
import ch.unibe.scg.team3.parseQueryAdapter.FriendsAdapter;
import ch.unibe.scg.team3.sharingService.SyncDatabase;
import ch.unibe.scg.team3.user.User;

import com.parse.*;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class FriendsActivity extends Activity {

	private ArrayList<User> friends;
	private ListView friendList;
	private FriendsAdapter friendsAdapter;
	private SyncDatabase sync;
	private FriendsHandler friendsHandler;
	private FriendshipHandler friendshipHandler;
	private UserHandler userHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		friends = new ArrayList<User>();
		sync = new SyncDatabase(this);
		friendsHandler = new FriendsHandler(this.getApplicationContext());
		friendshipHandler = new FriendshipHandler(this.getApplicationContext());
		userHandler = new UserHandler(this.getApplicationContext());
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		sync.sync();
		getFriends();
//		friendsAdapter = new FriendsAdapter(this, R.id.friends_list, friends);
//		
//		friendList = (ListView) findViewById(R.id.friends_list);
//		friendList.setAdapter(friendsAdapter);
		
		ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
				R.layout.friend_list_item, friends) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				if(convertView == null){
					convertView = View.inflate(getContext(), R.layout.friend_list_item, null);
				}
				
				TextView name = (TextView) convertView.findViewById(R.id.friend_username);
				User friend = getItem(position);
				
				
				convertView.setId(position);
				name.setText(friend.getUserName());
				registerForContextMenu(convertView);
				convertView.setId(position);
				return convertView;
			}

		};

		ListView list = (ListView) findViewById(R.id.friends_list);
		list.setAdapter(adapter);
		
	}

	private void getFriends() {
		ParseUser me = ParseUser.getCurrentUser();
		if (me != null) {
			String id = me.getObjectId();
			friends = friendsHandler.getFriends(id);
		}
		else{
			friends.clear();
		}
		
		
		
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		registerForContextMenu(v);
		menu.setHeaderTitle("Options");
//		menu.add(0, v.getId(), 0, "Share");
		menu.add(0, v.getId(), 0, "Delete");
	}

	@SuppressLint("NewApi")
	public boolean onContextItemSelected(MenuItem item) {
		 if (item.getTitle() == "Delete") {
			deleteFriend(findViewById(item.getItemId()));
		} else {
			return false;
		}
		return true;
	}

	

	public void addFriend(View view) {
			

			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Send a Friendrequest");
			alert.setMessage("Please enter the emailadress of your friend.");

			final EditText input = new EditText(this);
			
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String email = input.getText().toString();
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
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
				}
			});

			alert.show();
		
		
		
	}
	public void sentRequests(View view){
		Intent intent = new Intent(this, SentRequestActivity.class);
		startActivity(intent);
	}
	public void receivedRequests(View view){
		Intent intent = new Intent(this, ReceivedRequestsActivity.class);
		startActivity(intent);
	}
	public void deleteFriend(View view){
		User user = friends.get(view.getId());
		
		String me = ParseUser.getCurrentUser().getObjectId();;
		String friendshipId = friendshipHandler.getFriendship(me,user.getUserID());
		ParseUser pUser = new ParseUser().getParseUser(friendshipId);
		pUser.deleteInBackground();
	}
	

}
