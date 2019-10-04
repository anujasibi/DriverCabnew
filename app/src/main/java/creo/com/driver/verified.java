package creo.com.driver;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import cdflynn.android.library.checkview.CheckView;

public class verified extends AppCompatActivity {
    CheckView checkView;
    String phone_no = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);

        checkView = findViewById(R.id.check);
        Bundle bundle = getIntent().getExtras();
        phone_no = bundle.getString("phone_no");
        Log.d("phone","mm"+phone_no);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                checkView.check();
                gotoNext();
            }
        }, 600);

    }

    public void gotoNext() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(verified.this, resetpassword.class);
                intent.putExtra("phone_no", phone_no);
                Log.d("pppppp","mm"+phone_no);
                startActivity(intent);
                finish();
            }
        },500);
    }

}

