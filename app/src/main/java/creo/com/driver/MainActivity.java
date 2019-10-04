package creo.com.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.ncorti.slidetoact.SlideToActView;

public class MainActivity extends AppCompatActivity {

    TextView text,textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // SlideToActView sta = (SlideToActView) findViewById(R.id.example);
        text=findViewById(R.id.textn1);
        textView=findViewById(R.id.text2);


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
                Animatoo.animateSwipeRight(MainActivity.this);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, registernew.class));
                Animatoo.animateSlideLeft(MainActivity.this);

            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        /*
         * Displaying a notification locally
         */
        MyNotificationManager.getInstance(this).displayNotification("Greetings", "Hello how are you?");

    }
}
