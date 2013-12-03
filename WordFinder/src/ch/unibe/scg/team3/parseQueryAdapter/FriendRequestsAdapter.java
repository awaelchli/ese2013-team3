package ch.unibe.scg.team3.parseQueryAdapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import ch.unibe.scg.team3.user.User;
import ch.unibe.scg.team3.wordfinder.R;

import com.parse.*;

public class FriendRequestsAdapter extends ArrayAdapter<User> {

	public FriendRequestsAdapter(Context context, int resource, List<User> requests) {
		super(context, resource, requests);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(getContext(), R.layout.request_list_item, null);
		}

		TextView name = (TextView) convertView.findViewById(R.id.friend_username);
		ImageButton accept = (ImageButton) convertView.findViewById(R.id.accept_button);
		ImageButton reject = (ImageButton) convertView.findViewById(R.id.reject_button);

		final User friend = getItem(position);
		name.setText(friend.getUserName());

		accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				acceptFriend(friend);
				remove(friend);
			}

		});

		reject.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				rejectRequest(friend);
				remove(friend);
			}

		});
		return convertView;
	}

	private void acceptFriend(User friend) {

		final ParseUser me = ParseUser.getCurrentUser();

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
		query.whereEqualTo("initiator_id", friend.getUserID());

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> friendrequests, ParseException ex) {

				ParseObject friendship = new ParseObject("Friendship");
				ParseObject request = friendrequests.get(0);
				friendship.put("user_id", me.getObjectId());
				
				friendship.put("friend_id", request.get("initiator_id"));
				friendship.saveEventually();

				removeRequestsFromFriend(friendrequests);

			}

		});

	}

	private void rejectRequest(User friend) {

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
		query.whereEqualTo("initiator_id", friend.getUserID());

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> friendrequests, ParseException ex) {
				
				removeRequestsFromFriend(friendrequests);
			}
		});
	}
	
	private void removeRequestsFromFriend(List<ParseObject> friendrequests) {
		for (ParseObject request : friendrequests) {
			request.deleteEventually();
		}
	}
}
