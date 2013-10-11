package ch.unibe.scg.team3.wordfinder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
<<<<<<< HEAD
        System.out.print("hallo");
        System.out.println("hello");
=======
>>>>>>> c7dad01f1bd00caf3cd364e19c797a50146e3b8c
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
}
