package creo.com.driver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import creo.com.driver.utils.Global;

public class Profile extends AppCompatActivity {
    private RecyclerView recyclerView;
    Context mContext=this;
    private RelativeLayout rl,rlt;
    ScrollView scrollView;
    TextView edit,namep,emails,phoneno;
    private String URLlin = "https://creocabs.herokuapp.com/driver/driver_profile/";
    private ProgressDialog dialogs ;
    Context context=this;
    ImageView profile;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        driverdetails();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        namep=findViewById(R.id.namer);
        emails=findViewById(R.id.ema);
        phoneno=findViewById(R.id.ph);
        profile=findViewById(R.id.img);
        final ArrayList<NotificationPojo> pojo = new ArrayList<>();
        NotificationPojo notificationPojo = new NotificationPojo("You have recieved 5 new compliments",R.drawable.star);
        NotificationPojo notificationPojo1=new NotificationPojo("You have recieved a new policy update",R.drawable.notification);
        pojo.add(notificationPojo);
        pojo.add(notificationPojo1);



        recyclerView = findViewById(R.id.rer);
        edit=findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, EditProfile.class));
                Animatoo.animateSwipeRight(Profile.this);
            }
        });
        scrollView=findViewById(R.id.scroll);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });
        rl=findViewById(R.id.rl);
        rlt=findViewById(R.id.rlt);
        rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Review.class));
                Animatoo.animateFade(Profile.this);

            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Earning.class));
                Animatoo.animateSlideUp(Profile.this);
            }
        });
        recyclerView.setNestedScrollingEnabled(false);
        NotificationAdapter notificationAdapter = new NotificationAdapter(pojo,mContext);

        recyclerView.setAdapter(notificationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));



    }
    private void driverdetails(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  dialog.dismiss();
                        Toast.makeText(Profile.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("data");
                            JSONArray dataArray  = new JSONArray(status);
                            JSONObject field= dataArray.getJSONObject(0);
                            String name=field.getString("name");
                            namep.setText(name);
                            String email=field.getString("email");
                            emails.setText(email);
                            String num=field.getString("phone");
                            String photo=field.getString("photo");
                            phoneno.setText(num);
                         //   Picasso.get().load(photo).into(profile);
                            Log.d("otp","mm"+status);
                            Log.d("otp","mm"+name);


                            Log.d("otp","mm"+ot);
                            Toast.makeText(Profile.this, ot, Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+ Global.user_token);
                return params;
            }



        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);




    }


}
