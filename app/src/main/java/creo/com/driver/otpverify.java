package creo.com.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.driver.utils.Global;

public class otpverify extends AppCompatActivity implements
        SMSReceiver.OTPReceiveListener {

    EditText otpe;
    TextView verify;
    private SMSReceiver smsReceiver;
    private String otpc;
    String phone_no = null;
    Context context=this;
    private String URLline = Global.BASE_URL+"driver/driver_phone_verifying/";
    private ProgressDialog dialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);

        otpe=findViewById(R.id.description);
        verify=findViewById(R.id.tt);
        dialog=new ProgressDialog(otpverify.this,R.style.MyAlertDialogStyle);

        Bundle bundle = getIntent().getExtras();
        phone_no = bundle.getString("phone_no");
        Log.d("phone","mm"+phone_no);
        otpc=getIntent().getStringExtra("otp");
        Log.d("otp","mmm"+otpc);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpe.getText().toString().length() < 4) {
                    otpe.setError("Please enter the otp to proceed");
                }
                if (otpe.getText().toString().length() == 4) {

                    dialog.setMessage("Loading..");
                    dialog.show();
                    verifynewuser();
                }

            }
        });
        startSMSListener();
    }
    private void verifynewuser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(otpverify.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String code=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);

                            if(code.equals("200")){
                                Toast.makeText(otpverify.this, ot, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(otpverify.this, verified.class);
                                intent.putExtra("phone_no", phone_no);
                                Log.d("pppppp","mm"+phone_no);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(otpverify.this, ot, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(otpverify.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",phone_no);
                params.put("otp",otpe.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(this);

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // API successfully started
//                    showToast("started" );
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail to start API
//                    showToast("failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
        super.onBackPressed();

    }

    @Override
    public void onOTPReceived(String otp) {
//        showToast("OTP Received: " + otp);
        otpe.setText(otp);

        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
        }
    }

    @Override
    public void onOTPTimeOut() {
        // showToast("OTP Time out");
    }

    @Override
    public void onOTPReceivedError(String error) {
        showToast(error);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (smsReceiver != null) {
//            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver);
//        }
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
