package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import creo.com.driver.utils.Global;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class registernew extends AppCompatActivity {


    ImageView immm;
    private int PGALLERY=1,PCAMERA=2;
    private static final String IMAGE_DIRECTORY = "/driver";
    EditText name1,phone1,email1,password1,city1,vechre1,licno1,rcno1,stp1,uname;
    private String URLl = Global.BASE_URL+"driver/check_username/";
    private String URLli = Global.BASE_URL+"driver/check_email/";
    private String URLlin = Global.BASE_URL+"driver/check_phoneno/";
    TextView button;
    private Uri uri,ut,up;
    Context context=this;
    String filePath;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registernew);
        requestMultiplePermissions();


        immm=findViewById(R.id.photo);
        name1=findViewById(R.id.name);
        phone1=findViewById(R.id.phone_no);
        email1=findViewById(R.id.email);
        password1=findViewById(R.id.password);
        city1=findViewById(R.id.city);
        vechre1=findViewById(R.id.vechreg);
        licno1=findViewById(R.id.Lino);
        rcno1=findViewById(R.id.rcno);
        stp1=findViewById(R.id.dtk);
        uname=findViewById(R.id.uname);
        button=findViewById(R.id.button);







        /*final String file = getRealPathFromURIPath(ut, MainActivity.this);
        final String filep = getRealPathFromURIPath(up, MainActivity.this);
*/

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(name1.getText().toString().equals("")||phone1.getText().toString().equals("")||email1.getText().toString().equals("")||password1.getText().toString().equals("")||stp1.getText().toString().equals("")){
                    Toast.makeText(registernew.this,"All fields are required",Toast.LENGTH_LONG).show();
                }
                else {
                    uploadToServer(filePath);

                }



            }
        });
        uname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkuname();
             //   Toast.makeText(registernew.this, "Focused", Toast.LENGTH_SHORT).show();
            }
        });
        email1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkemail();
            }
        });
        phone1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkphone();
            }
        });




        immm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialogprof();
            }
        });


    }
    private void checkphone(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlin,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(registernew.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);
                            if(status.equals("2OO")){
                                //Toast.makeText(signup.this, ot, Toast.LENGTH_LONG).show();
                            }
                            else if(status.equals("201")){
                                //   Toast.makeText(registernew.this,"PhoneNumber Field should not be null",Toast.LENGTH_LONG).show();
                                phone1.setError("PhoneNumber already exist");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registernew.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",phone1.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void checkemail(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLli,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(registernew.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);
                            if(status.equals("2OO")){
                                //Toast.makeText(signup.this, ot, Toast.LENGTH_LONG).show();
                            }
                            else if(status.equals("201")){
                                //   Toast.makeText(registernew.this,"PhoneNumber Field should not be null",Toast.LENGTH_LONG).show();
                                email1.setError("Email already exist");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registernew.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email1.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
    private void checkuname(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(registernew.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            Log.d("otp","mm"+ot);
                            if(status.equals("2OO")){
                                //Toast.makeText(signup.this, ot, Toast.LENGTH_LONG).show();
                            }
                            else if(status.equals("201")){
                             //   Toast.makeText(registernew.this,"PhoneNumber Field should not be null",Toast.LENGTH_LONG).show();
                                uname.setError("Username already exist");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registernew.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",uname.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void showPictureDialogprof(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallaryprof();
                                break;
                            case 1:
                                takePhotoFromCameraprof();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallaryprof() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, PGALLERY);
    }

    private void takePhotoFromCameraprof() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PCAMERA);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }


        if(requestCode==PGALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                uri=data.getData();
                filePath = getRealPathFromURIPath(uri, registernew.this);
               try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(registernew.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    immm.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(registernew.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        if(requestCode == PCAMERA){
            Toast.makeText(registernew.this,"elbjuugv",Toast.LENGTH_SHORT).show();
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            immm.setImageBitmap(thumbnail);
            //  saveImage(thumbnail);
            Toast.makeText(registernew.this, "Image Saved!", Toast.LENGTH_SHORT).show();

        }



    }









    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }



                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    private void uploadToServer(String filePath) {
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        UploadAPI uploadAPI = retrofit.create(UploadAPI.class);

//        Log.d("url","mmm"+filePath);
        //Create a file object using file path
        if (filePath == null){
            Toast.makeText(registernew.this,"Please Upload Image",Toast.LENGTH_SHORT).show();
        }
        if (filePath != null) {
            File immm = new File(filePath);
            Log.d("mmmmmmm", "mmm" + immm.length());
            // Create a request body with file and image media type


            RequestBody photob = RequestBody.create(MediaType.parse("image/*"), immm);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part1 = MultipartBody.Part.createFormData("photo", immm.getName(), photob);


            //Create request body with text description and text media type
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), name1.getText().toString());
            RequestBody phone_no = RequestBody.create(MediaType.parse("text/plain"), phone1.getText().toString());
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), email1.getText().toString());
            RequestBody username = RequestBody.create(MediaType.parse("text/plain"), uname.getText().toString());
            RequestBody password = RequestBody.create(MediaType.parse("text/plain"), password1.getText().toString());
            RequestBody stockpoint = RequestBody.create(MediaType.parse("text/plain"), stp1.getText().toString());
            //
            Call<Result> call = uploadAPI.uploadImage(part1, name, phone_no, email,username, password, stockpoint);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    String result = response.body().getSuccess();
                    Toast.makeText(registernew.this, result, Toast.LENGTH_LONG).show();
                    if (result.equals("success")) {
                        Intent intent = new Intent(registernew.this, Login.class);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(registernew.this);

                    } else {
                        Toast.makeText(registernew.this, result, Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(registernew.this, "Failed registered" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

}
