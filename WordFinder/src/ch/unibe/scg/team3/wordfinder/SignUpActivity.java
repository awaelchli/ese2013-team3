package ch.unibe.scg.team3.wordfinder;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils.Permissions.User;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import ch.unibe.scg.team3.localDatabase.MainUserHandler;
import ch.unibe.scg.team3.localDatabase.UserHandler;
import ch.unibe.scg.team3.user.MainUser;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends Activity {
	protected UserHandler uh;
	protected MainUser muser = new MainUser();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
	}
	
	public void onCancel(View view){
		finish();
	}
	public void onSignupOk(View view){
		uh = new UserHandler(this);
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
		    
		     muser.setUserName(username);
		     muser.setEmail(email);
		     muser.setUserId(user.getObjectId().toString());
		     uh.setUser(muser);
		     finish();
		    } else {
		    	//TODO error handling
		    	int code = e.getCode();
		    	String message="someting is wrong";
		    	if(code==ParseException.CONNECTION_FAILED){message="You need internet connection to sign up";}
		    	if(code==ParseException.USERNAME_MISSING){message="You need to provide a username";}
		    	if(code==ParseException.USERNAME_TAKEN){message="This username is allready taken";}
		    	if(code==ParseException.EMAIL_MISSING || code==ParseException.EMAIL_TAKEN || code==ParseException.INVALID_EMAIL_ADDRESS){
		    		message="This email address is either allready assigned to an account or is not a valid address";
		    	}
		    	
		    	AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);

				alert.setTitle("Error");
				alert.setMessage(message);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

				alert.show();
		      
		    }
		  }
		});
	}
	

}
