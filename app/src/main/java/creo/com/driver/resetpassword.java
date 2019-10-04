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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import creo.com.driver.utils.Global;

public class resetpassword extends AppCompatActivity {
    EditText password;
    TextView reset;
    Context context=this;
    String phone_no = null;
    private ProgressDialog dialog ;

    private String URLline = Global.BASE_URL+"driver/driver_change_password/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        password=findViewById(R.id.description);
        reset=findViewById(R.id.tt);
        Bundle bundle = getIntent().getExtras();
        phone_no = bundle.getString("phone_no");
        Log.d("phone","mm"+phone_no);
        dialog=new ProgressDialog(resetpassword.this,R.style.MyAlertDialogStyle);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals("")){
                    password.setError("Please enter new password to reset");
                }
                else if(password.getText().toString().length()<6 &&!isValidPassword(password.getText().toString())){
                    password.setError("Password should contain uppercase,lowercase,number,special character");
                }else{
                    dialog.setMessage("Loading..");
                    dialog.show();
                    resetuser();
                }

            }
        });
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void resetuser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(resetpassword.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            Log.d("otp","mm"+ot);
                            Toast.makeText(resetpassword.this, ot, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(resetpassword.this, Login.class);
                            startActivity(intent);
                            Animatoo.animateSlideLeft(resetpassword.this);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(resetpassword.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",phone_no);
                params.put("password",password.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
}
