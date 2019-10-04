package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class vehicledetails extends AppCompatActivity {

    EditText brand,model,year,plate,color;
    TextView button;
    Context context=this;
    String phone_no = null;
    private String URLline = Global.BASE_URL+"driver/add_cab/";
    private ProgressDialog dialog ;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicledetails);
        sessionManager = new SessionManager(this);


        brand=findViewById(R.id.brand);
        model=findViewById(R.id.model);
        year=findViewById(R.id.year);
        plate=findViewById(R.id.plate);
        color=findViewById(R.id.color);
        dialog=new ProgressDialog(vehicledetails.this,R.style.MyAlertDialogStyle);

        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Loading..");
                dialog.show();
                uploaddetails();
            }
        });
    }
    private void uploaddetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(vehicledetails.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                if(sessionManager.getRC().equals("")||sessionManager.getPermit().equals("")||sessionManager.getInsurance().equals("")||sessionManager.getTourist().equals("")||sessionManager.getFitness().equals("")||sessionManager.getNOC().equals("")||sessionManager.getPho().equals("")) {
                                    Toast.makeText(vehicledetails.this, ot, Toast.LENGTH_LONG).show();
                                    sessionManager.setMake(brand.getText().toString());
                                    sessionManager.setModel(model.getText().toString());
                                    sessionManager.setYear(year.getText().toString());
                                    sessionManager.setPlate(plate.getText().toString());
                                    sessionManager.setColor(color.getText().toString());
                                    Intent intent = new Intent(vehicledetails.this, vehicledocument.class);
                                    startActivity(intent);
                                }
                                if(!(sessionManager.getRC().equals("")||sessionManager.getPermit().equals("")||sessionManager.getInsurance().equals("")||sessionManager.getTourist().equals("")||sessionManager.getFitness().equals("")||sessionManager.getNOC().equals("")||sessionManager.getPho().equals(""))) {

                                    Intent intent = new Intent(vehicledetails.this, Approval.class);
                                    startActivity(intent);
                                }

                            }
                            else{
                                Toast.makeText(vehicledetails.this, "Failed to add."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(vehicledetails.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("make",brand.getText().toString());
                params.put("model",model.getText().toString());
                params.put("year",year.getText().toString());
                params.put("color",plate.getText().toString());
                params.put("number_plate",color.getText().toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token "+Global.user_token);
                return headers;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    }

