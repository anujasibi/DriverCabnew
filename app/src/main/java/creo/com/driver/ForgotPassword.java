package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.driver.utils.Global;

public class ForgotPassword extends AppCompatActivity {
    EditText phone;
    TextView contin;
    Context context=this;
    private String URLline = Global.BASE_URL+"driver/driver_forget_password/";
    private ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        phone=findViewById(R.id.description);
        contin=findViewById(R.id.tt);
        dialog=new ProgressDialog(ForgotPassword.this,R.style.MyAlertDialogStyle);

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.getText().toString().equals("")){
                    phone.setError("Please Enter the Phone Number");
                }
                else {
                    dialog.setMessage("Loading..");
                    dialog.show();
                    forgotuser();
                }

            }
        });
    }
    private void forgotuser(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(ForgotPassword.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("status","mm"+status);
                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                              //  Toast.makeText(ForgotPassword.this, ot, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ForgotPassword.this, otpverify.class);
                                intent.putExtra("phone_no",phone.getText().toString());
                                startActivity(intent);
                                Animatoo.animateSlideLeft(ForgotPassword.this);
                            }
                            else{
                                Toast.makeText(ForgotPassword.this, ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(ForgotPassword.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",phone.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }



}
