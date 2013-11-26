package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.List;

import com.parse.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

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

		ParseQueryAdapter<ParseObject> adapter = new CustomParseQueryAdapter(this,
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

		ListView list = (ListView) findViewById(R.id.friends_list);
		list.setAdapter(adapter);
	}

	
	public void addFriend(View view) {
		EditText email_view = (EditText) findViewById(R.id.emailToAdd);
		String email = email_view.getText().toString();

		ParseUser me = ParseUser.getCurrentUser();

		final String myId = me.getObjectId();

		final ParseQuery<ParseUser> user = ParseQuery.getUserQuery();
		user.whereEqualTo("email", email);

		user.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> requests, ParseException e) {
				if (e == null) {
					String userId = "";
						try {
							userId = (String) user.getFirst().getObjectId();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					
					ParseObject request = new ParseObject("Request");
					request.put("initiator_id", myId);
					request.put("subject_id", userId);
					request.saveInBackground();

				} else {
					// TODO: handle this, prompt user
					e.printStackTrace();
				}
			}
		});
	}

}
class CustomParseQueryAdapter extends ParseQueryAdapter<ParseObject> {

	public CustomParseQueryAdapter(Context context,
			com.parse.ParseQueryAdapter.QueryFactory<ParseObject> queryFactory) {
		super(context, queryFactory);
	}

	@Override
	public View getItemView(ParseObject friendship, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.friend_list_item, null);
		}
		super.getItemView(friendship, v, parent);

		TextView name = (TextView) v.findViewById(R.id.friend_username);

		String friendId = friendship.getString("friend_id");
		ParseQuery<ParseUser> query = ParseUser.getQuery();

		try {
			ParseUser friend = query.get(friendId);

			name.setText(friend.getUsername());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return v;
	}

}

