package ch.unibe.scg.team3.wordfinder;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.unibe.scg.team3.localDatabase.RequestHandler;
import ch.unibe.scg.team3.parseQueryAdapter.ReceivedRequestsAdapter;
import ch.unibe.scg.team3.user.User;

import com.parse.ParseUser;

/**
 * This activity displays all the friend requests received from other users.
 * Accepted requests do not show up here.
 * 
 * @author adrian
 */
public class ReceivedRequestsActivity extends Activity {
	private ArrayList<User> requests;
	private ListView requestListView;
	private ReceivedRequestsAdapter requestAdapter;
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
		requestAdapter = new ReceivedRequestsAdapter(this, R.id.received_requests_list, requests);

		requestListView = (ListView) findViewById(R.id.received_requests_list);
		requestListView.setAdapter(requestAdapter);
	}

	private void getFriendRequests() {
		ParseUser me = ParseUser.getCurrentUser();
		if (me != null) {
			String id = me.getObjectId();
			requests = requestHandler.getRequestsForMe(id);
		} else {
			requests.clear();
		}
	}

	@Override
	public void onBackPressed() {
		onBack(null);
	}

	public void onBack(View view) {
		Intent intent = new Intent(getApplicationContext(), FriendsActivity.class);
		startActivity(intent);
		finish();
	}
}
