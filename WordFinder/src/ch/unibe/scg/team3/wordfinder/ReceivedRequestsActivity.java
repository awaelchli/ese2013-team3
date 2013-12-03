package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.team3.localDatabase.RequestHandler;
import ch.unibe.scg.team3.parseQueryAdapter.FriendRequestsAdapter;
import ch.unibe.scg.team3.parseQueryAdapter.FriendsAdapter;
import ch.unibe.scg.team3.sharingService.Request;
import ch.unibe.scg.team3.user.User;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class ReceivedRequestsActivity extends Activity {
	private ArrayList<User> requests;
	private ListView requestListView;
	private FriendRequestsAdapter requestAdapter;
	private RequestHandler requestHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_received_requests);
		requests = new ArrayList<User>();
		requestHandler = new RequestHandler(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		getFriendRequests();
		requestAdapter = new FriendRequestsAdapter(this,R.id.received_requests_list, requests);

		requestListView = (ListView) findViewById(R.id.received_requests_list);
		requestListView.setAdapter(requestAdapter);
	}

	private void getFriendRequests() {
		ParseUser me = ParseUser.getCurrentUser();
		if (me != null) {
			String id = me.getObjectId();
			requests = requestHandler.getRequestsForMe(id);
		}
		else{
			requests.clear();
		}
	}
}
