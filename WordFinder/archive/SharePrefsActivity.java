package ch.unibe.scg.team3.wordfinder;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import ch.unibe.scg.team3.localDatabase.MainUserHandler;
import ch.unibe.scg.team3.user.User;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SharePrefsActivity extends Activity {
	protected MainUserHandler muh;
	protected MainUser mainuser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_prefs);
		
		muh = new MainUserHandler(this);
		mainuser = muh.getMainUser();
		
		if(mainuser.getUserID() != null){
			Button login_ok = (Button) findViewById(R.id.login_ok_button);
			Button login_cancel = (Button) findViewById(R.id.login_cancel_button);
			login_ok.setVisibility(View.VISIBLE);
			login_cancel.setVisibility(View.VISIBLE);
		}
		else{
			TextView header = (TextView) findViewById(R.id.sharePrefs_title);
			Button signup_ok = (Button) findViewById(R.id.signup_ok_button);
			Button signup_cancel = (Button) findViewById(R.id.signup_cancel_button);
			TextView username_field = (TextView) findViewById(R.id.username_field);
			EditText username_edit = (EditText) findViewById(R.id.username_edit);
			TextView email_field = (TextView) findViewById(R.id.email_field);
			EditText email_edit = (EditText) findViewById(R.id.email_edit);
			header.setText("Sign Up");
			signup_ok.setVisibility(View.VISIBLE);
			signup_cancel.setVisibility(View.VISIBLE);
			username_field.setVisibility(View.VISIBLE);
			username_edit.setVisibility(View.VISIBLE);
			email_field.setVisibility(View.VISIBLE);
			email_edit.setVisibility(View.VISIBLE);
		}
	}

	public void onCancel(View view){
		finish();
	}
	public void onSignupOk(View view){
		EditText username_edit = (EditText) findViewById(R.id.username_edit);
		EditText password_edit = (EditText) findViewById(R.id.password_edit);
		EditText email_edit = (EditText) findViewById(R.id.email_edit);
		final String username = username_edit.getText().toString();
		final String password = password_edit.getText().toString();
		final String email = email_edit.getText().toString();
		final ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.signUpInBackground(new SignUpCallback() {
		  public void done(ParseException e) {
		    if (e == null) {
		     muh.setName(username);
		     muh.setEmail(email);
		     String tmp = user.getObjectId().toString();
		     muh.setUserId(user.getObjectId().toString());
		     
		    } else {
		      
		    }
		  }
		});
		finish();
	}
	public void onLoginOk(View view){
		muh = new MainUserHandler(this);
		mainuser = muh.getMainUser();
		EditText password_edit = (EditText) findViewById(R.id.password_edit);
		ParseUser.logInInBackground(mainuser.getUserName(),password_edit.getText().toString() , new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      finish();
			    } else {
			      
			    }
			  }
			});
		
	}

}
