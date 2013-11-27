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

public class FriendRequestsAdapter extends ParseQueryAdapter<ParseObject>{
	public FriendRequestsAdapter(Context context,
			com.parse.ParseQueryAdapter.QueryFactory<ParseObject> queryFactory) {
		super(context, queryFactory);
	}

	@Override
	public View getItemView(ParseObject request, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.friend_list_item, null);
		}
		super.getItemView(request, v, parent);

		TextView name = (TextView) v.findViewById(R.id.friend_username);

		String initiatorId = request.getString("initiator_id");
		ParseQuery<ParseUser> query = ParseUser.getQuery();

		try {
			ParseUser initiator = query.get(initiatorId);

			name.setText(initiator.getUsername());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return v;
	}

}
