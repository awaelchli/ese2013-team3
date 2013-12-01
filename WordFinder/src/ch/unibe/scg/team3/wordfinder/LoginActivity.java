package ch.unibe.scg.team3.wordfinder;

import ch.unibe.scg.team3.localDatabase.UserHandler;
import ch.unibe.scg.team3.user.User;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	User mainuser;
	UserHandler uh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		uh = new UserHandler(this);
		//mainuser = uh.getMainUser();
		
		
	}
	
	public void onCancel(View view){
		finish();
	}
	
	public void onLoginOk(View view){
		TextView username_field = (TextView) findViewById(R.id.username_edit); 
		EditText password_edit = (EditText) findViewById(R.id.password_edit);
		String name = username_field.getText().toString();
		String passw = password_edit.getText().toString();
		
		ParseUser.logInInBackground(name, passw, new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      finish();
			    } else {
			    	int code = e.getCode();
			    	String message="someting is wrong";
			    	if(code==ParseException.OBJECT_NOT_FOUND){message="Invalid username, password combination";}
			    	if(code==ParseException.CONNECTION_FAILED){message="Unable to connect to the internet";}
			    	
			    	System.out.println(code);
			    	
			    	AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);

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
