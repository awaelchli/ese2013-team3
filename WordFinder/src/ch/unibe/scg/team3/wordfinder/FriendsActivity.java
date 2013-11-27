package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.parseQueryAdapter.FriendRequestsAdapter;
import ch.unibe.scg.team3.parseQueryAdapter.FriendsAdapter;

import com.parse.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
	}

	@Override
	protected void onResume() {
		super.onResume();
		ParseQueryAdapter<ParseObject> friendAdapter = new FriendsAdapter(this,
				new ParseQueryAdapter.QueryFactory<ParseObject>() {

					@Override
					public ParseQuery<ParseObject> create() {
						ParseUser me = ParseUser.getCurrentUser();
						if (me != null) {
							String id = me.getObjectId();

							ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
									"Friendship");
							query.whereEqualTo("user_id", id);
							return query;
						}
						return null;
					}

				});

		ParseQueryAdapter<ParseObject> requestAdapter = new FriendRequestsAdapter(
				this, new ParseQueryAdapter.QueryFactory<ParseObject>() {

					@Override
					public ParseQuery<ParseObject> create() {
						ParseUser me = ParseUser.getCurrentUser();
						if (me != null) {
							String id = me.getObjectId();

							ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
									"Request");
							query.whereEqualTo("subject_id", id);
							return query;
						}
						return null;
					}

				});

		ListView friendList = (ListView) findViewById(R.id.friends_list);
		ListView requestList = (ListView) findViewById(R.id.request_list);
		friendList.setAdapter(friendAdapter);
		requestList.setAdapter(requestAdapter);
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
//						Toast.makeText(getApplicationContext(),
//								"Couldn't send Request", Toast.LENGTH_LONG)
//								.show();
						return;
					}
					ParseObject request = new ParseObject("Request");
					request.put("initiator_id", myId);
					request.put("subject_id", userId);
					request.saveInBackground();
					email_view.setText("");
					email_view.clearFocus();
					Toast.makeText(getApplicationContext(), "Request has been sent",
							Toast.LENGTH_LONG).show();

				} else {
					int code = e.getCode();
			    	String message="someting is wrong";
			    	if(code==ParseException.OBJECT_NOT_FOUND){message="Could not find user";}
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
					
					
//					Toast.makeText(getApplicationContext(),
//							"Couldn't send Request", Toast.LENGTH_LONG).show();
//					e.printStackTrace();
				}
			}
		});
	}

}
