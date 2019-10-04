package creo.com.driver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.driver.R;
import creo.com.driver.utils.Global;
import creo.com.driver.utils.SessionManager;


public class PaymentActivity extends Activity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    CardView cardView;
    private String URLline = Global.BASE_URL+"user/get_user_details/";
    SessionManager sessionManager;
    private ProgressDialog dialog ;
    Context context=this;
    String emails,phoneno=null;
    String destination,mode_of_payment,imagamounte,razorid=null;
    private String URLlin = "http://creocabs.herokuapp.com/user/make_payment/";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        sessionManager = new SessionManager(this);
        dialog=new ProgressDialog(PaymentActivity.this,R.style.MyAlertDialogStyle);
        dialog.setMessage("Loading..");
        dialog.show();

        Bundle bundle=getIntent().getExtras();
        destination=bundle.getString("destination");
        mode_of_payment=bundle.getString("mode_of_payment");
        imagamounte=bundle.getString("imagamounte");

        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
        //cardView=findViewById(R.id.cardee);

       // userdetails();

        startPayment();



    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "CABS");
            //  options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            //   options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email",emails );
            preFill.put("contact",phoneno);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            razorid=razorpayPaymentID;
        //    boouser();

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
    /*private void userdetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(PaymentActivity.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String data=jsonObject.optString("data");
                            JSONArray dataArray  = new JSONArray(data);
                            JSONObject jsonObject1=dataArray.optJSONObject(0);
                            emails=jsonObject1.optString("email");
                            //   JSONObject email=jsonObject1.getJSONObject("email");
                            Log.d("email","mm"+emails);

                            // JSONObject phone=jsonObject1.getJSONObject("phone");
                            phoneno=jsonObject1.optString("phone");
                            Log.d("phone","mm"+phoneno);




                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                Toast.makeText(PaymentActivity.this, ot, Toast.LENGTH_LONG).show();
                               *//* startActivity(new Intent(searchplace.this,scheduledetails.class));
                                startActivity(i);*//*
                            }
                            else{
                                Toast.makeText(PaymentActivity.this, "Invalid Password."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    private void boouser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  dialog.dismiss();
                        Toast.makeText(PaymentActivity.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("status");
                            Log.d("otp","mm"+ot);
                            Toast.makeText(PaymentActivity.this, ot, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("driver", sessionManager.getID());
                Log.d("driver","mm"+sessionManager.getID());
                params.put("source",sessionManager.getSourceadd());
                Log.d("source","mm"+sessionManager.getSourceadd());
                params.put("destination",destination);
                Log.d("des","mm"+destination);
                params.put("mode_of_payment",mode_of_payment);
                Log.d("lat","mm"+mode_of_payment);
                params.put("amount",imagamounte);
                Log.d("lat","mm"+imagamounte);
                params.put("payment_id",razorid);
                Log.d("paymentid",razorid);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                return params;
            }



        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);



    }*/

}