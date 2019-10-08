package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import creo.com.driver.utils.SessionManager;

public class Documents extends AppCompatActivity {
    CardView cardView,cardv,cardi;
    SessionManager sessionManager;
    String pcc=null;
    String license=null;
    String address_proof=null;
    boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        sessionManager = new SessionManager(this);



        cardView=findViewById(R.id.pcc);
        cardv=findViewById(R.id.dril);
        cardi=findViewById(R.id.cid);
        cardView.setVisibility(View.VISIBLE);
        cardv.setVisibility(View.VISIBLE);
        cardi.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        pcc = sessionManager.getUser_pcc();
        Log.d("phone","mm"+pcc);
        Bundle bundlen = getIntent().getExtras();
        license = sessionManager.getLicense();
        Log.d("phone","mm"+license);
        Bundle bundleP = getIntent().getExtras();
        address_proof = sessionManager.getId();
        Log.d("phone","mm"+address_proof);


        if(pcc.equals("")){
            cardView.setVisibility(View.VISIBLE);
        }
        else{
            cardView.setVisibility(View.GONE);
        }

        if(license.equals("")){
            cardv.setVisibility(View.VISIBLE);
        }
        else{
            cardv.setVisibility(View.GONE);
        }
        if(sessionManager.getId().equals("")){
            cardi.setVisibility(View.VISIBLE);
        }
        else{
            cardi.setVisibility(View.GONE);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Documents.this,policeclear.class));
            }
        });

        cardv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Documents.this,Uploadlicense.class));
            }
        });
        cardi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Documents.this,photoid.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
