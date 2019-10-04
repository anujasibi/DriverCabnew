package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class EditProfile extends AppCompatActivity {

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name = findViewById(R.id.name);

        name.setFocusable(true);
        name.setEnabled(true);
        name.setClickable(true);
        name.setFocusableInTouchMode(true);
    }
}
