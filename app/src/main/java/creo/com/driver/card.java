package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

public class card extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
    }
}
