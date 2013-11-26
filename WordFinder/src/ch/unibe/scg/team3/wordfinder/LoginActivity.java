package ch.unibe.scg.team3.wordfinder;

import ch.unibe.scg.team3.localDatabase.MainUserHandler;
import ch.unibe.scg.team3.user.MainUser;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	MainUser mainuser;
	MainUserHandler muh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		muh = new MainUserHandler(this);
		mainuser = muh.getMainUser();
		
		
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
			      
			    }
			  }
			});
		
	}

}
