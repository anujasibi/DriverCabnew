package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.driver.utils.Global;
import creo.com.driver.utils.SessionManager;

public class Tracking extends AppCompatActivity {
    Button track;
    SessionManager sessionManager;
    public String url="http://creocabs.herokuapp.com/driver/scheduled_trip_complete/";
    Context context=this;
    public String amount,balance;
    private ProgressDialog dialogs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        dialogs=new ProgressDialog(Tracking.this,R.style.MyAlertDialogStyle);


        track=findViewById(R.id.button2);
        sessionManager = new SessionManager(this);

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs.setMessage("Loading");
                dialogs.show();
                endtrip();
            }
        });
    }

    private void endtrip(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialogs.dismiss();
                        Toast.makeText(Tracking.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            amount=jsonObject.optString("earning");
                            sessionManager.setEarning("earnings");
                            balance=jsonObject.optString("balance");
                            sessionManager.setBalance("balance");

                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                Toast.makeText(Tracking.this, ot, Toast.LENGTH_LONG).show();
                                showDialog(Tracking.this,"Your Total Earning For This Trip is ₹ "+amount);

                               /* startActivity(new Intent(searchplace.this,scheduledetails.class));
                                startActivity(i);*/
                            }
                            else{
                                Toast.makeText(Tracking.this, "Something went wrong.Server Error"+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(Tracking.this,error.toString(),Toast.LENGTH_LONG).show();
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
    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton1 = (Button) dialog.findViewById(R.id.btn1);
        dialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(Tracking.this,Earning.class);
                intent.putExtra("earning",amount);
                intent.putExtra("balance",balance);
                startActivity(intent);
            }
        });

        dialog.show();

    }

}
