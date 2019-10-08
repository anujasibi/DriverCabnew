package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.driver.utils.Global;
import creo.com.driver.utils.SessionManager;

public class CustomerDetails extends AppCompatActivity {
    Context context=this;
    TextView na,ph,sou,de,track;
    private String nam,pho,sour,des;
    private String URLli="http://creocabs.herokuapp.com/driver/accept_scheduled_trip/trip_id";
    SessionManager sessionManager;
    private ProgressDialog dialog ;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        dialog=new ProgressDialog(CustomerDetails.this,R.style.MyAlertDialogStyle);
        dialog.setMessage("Loading");
        dialog.show();
        na=findViewById(R.id.te);
        ph=findViewById(R.id.email);
        sou=findViewById(R.id.phone);
        de=findViewById(R.id.location);
        track=findViewById(R.id.textv);
        sessionManager = new SessionManager(this);
        userdetails();
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerDetails.this,Tracking.class));
            }
        });



    }

    private void userdetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLli,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(CustomerDetails.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String data=jsonObject.optString("data");
                            JSONArray dataArray  = new JSONArray(data);
                            JSONObject jsonObject1=dataArray.optJSONObject(0);
                            nam=jsonObject1.optString("user");
                            //   JSONObject email=jsonObject1.getJSONObject("email");
                           na.setText(nam);
                            // JSONObject phone=jsonObject1.getJSONObject("phone");
                            pho=jsonObject1.optString("phonr");
                            Log.d("phone","mm"+pho);
                            ph.setText(pho);

                            des=jsonObject1.optString("to");
                            Log.d("phone","mm"+des);

                            de.setText(des);

                            sour=jsonObject1.optString("from");

                            sou.setText(sour);
                            Log.d("phone","mm"+sour);





                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                Toast.makeText(CustomerDetails.this, ot, Toast.LENGTH_LONG).show();
                               /* startActivity(new Intent(searchplace.this,scheduledetails.class));
                                startActivity(i);*/
                            }
                            else{
                                Toast.makeText(CustomerDetails.this, "Invalid Password."+ot, Toast.LENGTH_LONG).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CustomerDetails.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+ Global.user_token);
                return params;
            }
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("trip_id",sessionManager.getUserid());
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


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
