package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.List;

import com.parse.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class FriendsActivity extends Activity {

	private List<ParseUser> friends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		friends = new ArrayList<ParseUser>();
	}

	@Override
	protected void onResume() {
		super.onResume();

		ParseUser me = ParseUser.getCurrentUser();
		if (me != null) {
			String id = me.getObjectId();

			ParseQuery<ParseObject> friendship = ParseQuery.getQuery("Friendship");
			friendship.whereEqualTo("user_id", id);

			friendship.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> friendships, ParseException e) {
					if (e == null) {
						setupFriendList(friendships);
					} else {
						// TODO: handle this, prompt user
						e.printStackTrace();
					}
				}
			});
		}
	}

	private void updateAdapter() {

		ArrayAdapter<ParseUser> adapter = new ArrayAdapter<ParseUser>(this,
				R.layout.friend_list_item, friends) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				final View element = inflater.inflate(R.layout.friend_list_item, parent, false);

				ParseUser friend = getItem(position);
				TextView name = (TextView) element.findViewById(R.id.friend_username);
				name.setText(friend.getUsername());

				element.setId(position);
				return element;
			}

		};

		ListView list = (ListView) findViewById(R.id.friends_list);
		list.setAdapter(adapter);

	}

	private void setupFriendList(List<ParseObject> friendships) {

		for (ParseObject friendship : friendships) {
			String friend_id = friendship.getString("friend_id");

			ParseQuery<ParseUser> query = ParseUser.getQuery();
			query.whereEqualTo("objectId", friend_id);

			query.findInBackground(new FindCallback<ParseUser>() {

				@Override
				public void done(List<ParseUser> result, ParseException arg1) {
					friends.addAll(result);
					updateAdapter();
				}

			});
		}
	}

	public void addFriend(View view) {
	}

}
