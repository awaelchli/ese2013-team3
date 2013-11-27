package ch.unibe.scg.team3.parseQueryAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.unibe.scg.team3.wordfinder.R;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class FriendsAdapter extends ParseQueryAdapter<ParseObject> {

	public FriendsAdapter(Context context,
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

