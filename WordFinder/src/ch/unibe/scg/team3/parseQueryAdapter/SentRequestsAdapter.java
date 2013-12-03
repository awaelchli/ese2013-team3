package ch.unibe.scg.team3.parseQueryAdapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import ch.unibe.scg.team3.user.User;
import ch.unibe.scg.team3.wordfinder.R;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class SentRequestsAdapter extends ArrayAdapter<User> {

	public SentRequestsAdapter(Context context, int resource, List<User> friends) {
		super(context, resource, friends);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(getContext(),
					R.layout.sent_request_list_item, null);
		}

		TextView name = (TextView) convertView
				.findViewById(R.id.friend_username);
		ImageButton reject = (ImageButton) convertView
				.findViewById(R.id.reject_button);

		final User friend = getItem(position);
		name.setText(friend.getUserName());

		reject.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				revokeRequest(friend);
				remove(friend);
			}

		});
		return convertView;
	}

	private void revokeRequest(User friend) {

		ParseUser me = ParseUser.getCurrentUser();
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
		query.whereEqualTo("subject_id", me.getObjectId());

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> sentRequest, ParseException ex) {

				removeRequestsFromFriend(sentRequest);
			}
		});
	}

	private void removeRequestsFromFriend(List<ParseObject> sentRequests) {
		for (ParseObject request : sentRequests) {
			request.deleteEventually();
		}
	}

}
