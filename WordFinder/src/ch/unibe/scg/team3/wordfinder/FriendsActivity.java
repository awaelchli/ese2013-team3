package ch.unibe.scg.team3.wordfinder;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		
//		ParseUser me = ParseUser.getCurrentUser();
//		String id = me.getObjectId();
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("username", "nils");
		
		query.findInBackground(new FindCallback<ParseUser>() {
		  public void done(List<ParseUser> objects, ParseException e) {
		    if (e == null) {
		        setupFriendsList(objects);
		    } else {
		    	//TODO: handle this, prompt user
		       	e.printStackTrace();
		    }
		  }
		});
	}
	
	private void setupFriendsList(List<ParseUser> objects) {
		
		ArrayAdapter<ParseUser> adapter = new ArrayAdapter<ParseUser>(this,
				R.layout.friend_list_item, objects) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				View element = inflater.inflate(R.layout.friend_list_item, parent, false);
				TextView name = (TextView) element.findViewById(R.id.friend_username);
				
				ParseUser friend = getItem(position);

				name.setText(friend.getUsername());
				
//				registerForContextMenu(element);
				element.setId(position);
				return element;
			}

		};

		ListView list = (ListView) findViewById(R.id.friends_list);
		list.setAdapter(adapter);
		
	}

}
