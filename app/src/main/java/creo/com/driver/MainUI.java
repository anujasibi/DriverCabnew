package creo.com.driver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.driver.utils.Global;

public class MainUI extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    ImageView im;

    TextView textView,sources,date,times,dest;
    private GoogleMap mMap;
    String token = null;
    private String URLline = Global.BASE_URL+"driver/driver_online/";
    private String URLli="http://creocabs.herokuapp.com/driver/driver_assigned_trip/";
    public String URLl="http://creocabs.herokuapp.com/driver/accept_scheduled_trip/trip_id";
    public String url="http://creocabs.herokuapp.com/driver/scheduled_trip_reject/trip_id";
    private static final String TAG = "MainActivity";
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 20000; /* 20 sec */
    private String dates,timez,source,dests,id;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;
    Context context=this;
    private String latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        Log.d("phone","mm"+token);

        sources=findViewById(R.id.source);
        dest=findViewById(R.id.place);
        times=findViewById(R.id.time);
        date=findViewById(R.id.date);


        im=findViewById(R.id.dr);

        SlideToActView sta = (SlideToActView) findViewById(R.id.example);
        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView view) {
                Toast.makeText(MainUI.this,"Accepted the request",Toast.LENGTH_SHORT).show();
                accept();

            }
        });
        SlideToActView stan = (SlideToActView) findViewById(R.id.examplen);
        stan.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView view) {
                Toast.makeText(MainUI.this,"Rejected the request",Toast.LENGTH_SHORT).show();
                reject();
            }
        });
      //  textView=findViewById(R.id.text);

        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainUI.this, Profile.class));
                Animatoo.animateDiagonal(MainUI.this);
            }
        });

        Switch sw = (Switch) findViewById(R.id.simpleSwitch2);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainUI.this,"You are now online",Toast.LENGTH_LONG).show();
                    onlineuser();

                } else {
                    Toast.makeText(MainUI.this,"You are now offline",Toast.LENGTH_LONG).show();
                }
            }
        });

        if (requestSinglePermission()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            //it was pre written
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

          //  mLatitudeTextView = (TextView) findViewById((R.id.latitude_textview));
       //     mLongitudeTextView = (TextView) findViewById((R.id.longitude_textview));

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            checkLocation(); //check whether location service is enable or not in your  phone
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (latLng != null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
     //   mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
      //  mLongitudeTextView.setText(String.valueOf(location.getLongitude()));
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d("Latlng","mm"+msg);
         latitude= Double.toString(location.getLatitude());
         longitude= Double.toString(location.getLongitude());
        // You can now create a LatLng Object for use with maps
        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //it was pre written
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }





    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //Single Permission is granted
                        Toast.makeText(MainUI.this, "Single permission is granted!", Toast.LENGTH_SHORT).show();
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        return isPermission;

    }

    private void onlineuser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainUI.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            Log.d("otp","mm"+ot);
                            Toast.makeText(MainUI.this, ot, Toast.LENGTH_LONG).show();
                            schedule();
                           /* Intent intent = new Intent(MainUI.this, MainUI.class);
                            intent.putExtra("token", token);
                            Log.d("pppppp","mm"+token);
                            startActivity(intent);
                            Animatoo.animateSlideLeft(MainUI.this);*/



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainUI.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Token "+token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void schedule(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLli,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // dialog.dismiss();
                        Toast.makeText(MainUI.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String data=jsonObject.optString("data");
                            JSONArray dataArray  = new JSONArray(data);
                            JSONObject jsonObject1=dataArray.optJSONObject(0);
                            id=jsonObject1.optString("trip_id");
                            //   JSONObject email=jsonObject1.getJSONObject("email");
                            Log.d("email","mm"+id);

                            // JSONObject phone=jsonObject1.getJSONObject("phone");
                            source=jsonObject1.optString("from");
                            Log.d("phone","mm"+source);
                            sources.setText(source);

                            dests=jsonObject1.optString("to");
                            Log.d("phone","mm"+dests);

                            dest.setText(dests);

                            dates=jsonObject1.optString("date");

                            date.setText(dates);
                            Log.d("phone","mm"+dates);
                            timez=jsonObject1.optString("time");
                            times.setText(timez);
                            Log.d("phone","mm"+timez);





                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                Toast.makeText(MainUI.this, ot, Toast.LENGTH_LONG).show();
                               /* startActivity(new Intent(searchplace.this,scheduledetails.class));
                                startActivity(i);*/
                            }
                            else{
                                Toast.makeText(MainUI.this, "Invalid Password."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(MainUI.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+Global.user_token);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void reject(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // dialog.dismiss();
                        Toast.makeText(MainUI.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                Toast.makeText(MainUI.this, ot, Toast.LENGTH_LONG).show();
                               /* startActivity(new Intent(searchplace.this,scheduledetails.class));
                                startActivity(i);*/
                            }
                            else{
                                Toast.makeText(MainUI.this, "Something went wrong.Server Error"+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(MainUI.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+Global.user_token);
                return params;
            }

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("trip_id",id);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void accept(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // dialog.dismiss();
                        Toast.makeText(MainUI.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);
                            if(status.equals("200")){
                                Toast.makeText(MainUI.this, ot, Toast.LENGTH_LONG).show();
                               /* startActivity(new Intent(searchplace.this,scheduledetails.class));
                                startActivity(i);*/
                                startActivity(new Intent(MainUI.this, CustomerDetails.class));
                                Animatoo.animateFade(MainUI.this);
                            }
                            else{
                                Toast.makeText(MainUI.this, "Something went wrong.Server Error"+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(MainUI.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+Global.user_token);
                return params;
            }

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("trip_id",id);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }




}
