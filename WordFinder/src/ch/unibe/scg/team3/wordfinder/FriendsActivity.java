package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.localDatabase.FriendsHandler;
import ch.unibe.scg.team3.localDatabase.FriendshipHandler;
import ch.unibe.scg.team3.sharingService.SyncDatabase;
import ch.unibe.scg.team3.user.User;

import com.parse.*;

import android.os.Bundle;
import android.view.ContextMenu;
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
import android.content.DialogInterface;
import android.content.Intent;

public class FriendsActivity extends Activity {

	private ArrayList<User> friends;
	private SyncDatabase sync;
	private FriendsHandler friendsHandler;
	private FriendshipHandler friendshipHandler;
	private ArrayAdapter<User> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);

		sync = new SyncDatabase(this) {

			@Override
			public void finished() {
				if (adapter != null) {
					getFriends();
					onResume();
				}
			}

		};
		friendsHandler = new FriendsHandler(this);
		friendshipHandler = new FriendshipHandler(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		friends = new ArrayList<User>();
		sync.sync();

		adapter = new ArrayAdapter<User>(this, R.layout.friend_list_item, friends) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				if (convertView == null) {
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
		getFriends();
		ListView list = (ListView) findViewById(R.id.friends_list);
		list.setAdapter(adapter);

	}

	@SuppressLint("NewApi")
	private void getFriends() {
		ParseUser me = ParseUser.getCurrentUser();
		if (me != null) {
			String id = me.getObjectId();
			friends = friendsHandler.getFriends(id);
			adapter.clear();
			adapter.addAll(friends);
			adapter.notifyDataSetChanged();
		} else {
			adapter.clear();
			adapter.notifyDataSetChanged();
		}

	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		registerForContextMenu(v);
		menu.setHeaderTitle("Options");
		// menu.add(0, v.getId(), 0, "Share");
		menu.add(0, v.getId(), 0, "Delete");
	}

	@SuppressLint("NewApi")
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Delete") {
			deleteFriend(findViewById(item.getItemId()));
			adapter.remove(adapter.getItem(item.getItemId()));
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

				final ParseQuery<ParseUser> query = ParseQuery.getUserQuery();
				query.whereEqualTo("email", email);

				query.findInBackground(new FindCallback<ParseUser>() {
					public void done(List<ParseUser> requests, ParseException e) {
						if (e == null) {
							String userId = null;
							try {
								userId = query.getFirst().getObjectId();
							} catch (ParseException e1) {

								int code = e1.getCode();
								String message = "someting is wrong";
								if (code == ParseException.OBJECT_NOT_FOUND) {
									message = "could not find user";
								}
								if (code == ParseException.CONNECTION_FAILED) {
									message = "Unable to connect to the internet";
								}

								AlertDialog.Builder alert = new AlertDialog.Builder(
										FriendsActivity.this);

								alert.setTitle("Error");
								alert.setMessage(message);

								alert.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,
													int whichButton) {
											}
										});

								alert.show();

							}
							if (userId == null) {
								Toast.makeText(getApplicationContext(),
										"Could not send the request", Toast.LENGTH_LONG).show();
								return;
							}

							makeRequest(myId, userId);

						} else {
							Toast.makeText(getApplicationContext(), "Could not send the request",
									Toast.LENGTH_LONG).show();
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

	/*
	 * Make a request if there is not already one.
	 */
	private void makeRequest(final String initiator_id, final String user_id) {
		ParseQuery<ParseObject> requests = ParseQuery.getQuery("Request");
		requests.whereEqualTo("initiator_id", initiator_id);
		requests.whereEqualTo("subject_id", user_id);

		requests.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> requests, ParseException ex) {
				if (requests.isEmpty()) {

					ParseObject request = new ParseObject("Request");
					request.put("initiator_id", initiator_id);
					request.put("subject_id", user_id);
					request.saveInBackground();
					Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_LONG)
							.show();
				}
			}

		});
	}

	public void sentRequests(View view) {
		Intent intent = new Intent(this, SentRequestActivity.class);
		startActivity(intent);
	}

	public void receivedRequests(View view) {
		Intent intent = new Intent(this, ReceivedRequestsActivity.class);
		startActivity(intent);
	}

	public void deleteFriend(View view) {
		User user = friends.get(view.getId());

		String me = ParseUser.getCurrentUser().getObjectId();
		String friendshipId = friendshipHandler.getFriendship(me, user.getUserID());

		final ParseQuery<ParseObject> friendships = ParseQuery.getQuery("Friendship");
		friendships.whereEqualTo("objectId", friendshipId);

		friendships.getInBackground(friendshipId, new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					try {
						friendships.getFirst().deleteInBackground();
					} catch (ParseException e1) {

					}
				}

			}
		});

	}

	/**
	 * BugFixing : Deactivates Button.
	 */
	@Override
	public void onBackPressed() {
	}

	public void onBack(View view) {
		Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(intent);
		finish();
	}
}