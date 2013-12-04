package ch.unibe.scg.team3.localDatabase;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseUser;

import ch.unibe.scg.team3.sharingService.Request;
import ch.unibe.scg.team3.user.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class RequestHandler extends DataHandler {
	private UserHandler userHandler;
	public RequestHandler(Context context) {
		super(context);
		userHandler = new UserHandler(context);
	}

	public boolean isRequestinDb(String objectId) {
		Cursor c = helper.rawQuery("SELECT * FROM Request WHERE request_id = '"
				+ objectId+"'", null);
		if (c != null && c.getCount() != 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}

	public void setRequest(Request dbrequest) {
		ContentValues c = new ContentValues();
		c.put("request_id", dbrequest.getRequestid());
		c.put("initiator_id", dbrequest.getInitiator());
		c.put("subject_id", dbrequest.getSubject());
		helper.insert("Request", null, c);

	}

	public List<Request> getRequests() {
		List<Request> requests = new ArrayList<Request>();
		Cursor c = helper.rawQuery("SELECT * FROM Request", null);
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				requests.add(new Request(c.getString(2), c.getString(0), c
						.getString(1)));
				c.moveToNext();
			}
		}
		c.close();
		return requests;
	}

	public void remove(Request deletedrequest) {
		helper.delete("Request", "request_id = ?", new String[] {deletedrequest.getRequestid()});

	}

	public ArrayList<User> getRequestsForMe(String id) {
		ArrayList<User> users = new ArrayList<User>();
		List<String> requests = new ArrayList<String>();
		Cursor c = helper.rawQuery("SELECT initiator_id FROM Request WHERE subject_id = ?", new String[] {id});
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				requests.add(c.getString(0));
				c.moveToNext();
			}
			
		}
		c.close();
		for(String request:requests){
			users.add(userHandler.getUser(request));
		}
		return users;
	}
	
	public ArrayList<User> getRequestsFromMe(String id) {
		ArrayList<User> users = new ArrayList<User>();
		List<String> requests = new ArrayList<String>();
		Cursor c = helper.rawQuery("SELECT subject_id FROM Request WHERE initiator_id = ?", new String[] {id});
		if (c != null && c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				requests.add(c.getString(0));
				c.moveToNext();
			}
			c.close();
		} else {
			c.close();
		}
		for(String request:requests){
			users.add(userHandler.getUser(request));
		}
		return users;
	}
	

}
