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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import creo.com.driver.utils.Global;
import creo.com.driver.utils.SessionManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class vechiclephoto extends AppCompatActivity {
    ImageView immm;
    TextView button;
    private int PGALLERY=1,PCAMERA=2;
    String filePath;
    private static final String IMAGE_DIRECTORY = "/driver";
    private Uri uri,ut,up;
    SessionManager sessionManager;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vechiclephoto);
        requestMultiplePermissions();
        sessionManager = new SessionManager(this);

        immm=findViewById(R.id.photo);

        button=findViewById(R.id.button);

        immm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialogprof();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                uploadToServer(filePath);





            }
        });
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
                filePath = getRealPathFromURIPath(uri, vechiclephoto.this);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    bitmap = getResizedBitmap(bitmap, 400);
                    String path = saveImage(bitmap);
                    Toast.makeText(vechiclephoto.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    immm.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(vechiclephoto.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        if(requestCode == PCAMERA){
            Toast.makeText(vechiclephoto.this,"elbjuugv",Toast.LENGTH_SHORT).show();
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            immm.setImageBitmap(thumbnail);
            //  saveImage(thumbnail);
            Toast.makeText(vechiclephoto.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
            filePath= (getRealPathFromURI(tempUri));
            Log.d("filepath","mm"+filePath);

        }



    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
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

    private void uploadToServer(String filePath) {
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        UploadRC uploadAPI = retrofit.create(UploadRC.class);

//        Log.d("url","mmm"+filePath);
        //Create a file object using file path
        if (filePath == null){
            Toast.makeText(vechiclephoto.this,"Please Upload Image",Toast.LENGTH_SHORT).show();
        }
        if (filePath != null) {
            File immm = new File(filePath);
            sessionManager.setPho(filePath);
            Log.d("mmmmmmm", "mmm" + immm.length());
            // Create a request body with file and image media type


            RequestBody photob = RequestBody.create(MediaType.parse("image/*"), immm);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part1 = MultipartBody.Part.createFormData("pic", immm.getName(), photob);


            //Create request body with text description and text media type
            //
            Call<Result> call = uploadAPI.uploadPhoto(part1,"Token "+ Global.user_token);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    String result = response.body().getSuccess();
                    Toast.makeText(vechiclephoto.this, result, Toast.LENGTH_LONG).show();
                    if (result.equals("success")) {
                        if(sessionManager.getMake().equals("")||sessionManager.getModel().equals("")||sessionManager.getYear().equals("")||sessionManager.getColor().equals("")||sessionManager.getPlate().equals("")||sessionManager.getPermit().equals("")||sessionManager.getTourist().equals("")||sessionManager.getInsurance().equals("")||sessionManager.getFitness().equals("")||sessionManager.getNOC().equals("")||sessionManager.getRC().equals("")){
                            Intent intent = new Intent(vechiclephoto.this, vehicledocument.class);
                            startActivity(intent);
                            Animatoo.animateSlideLeft(vechiclephoto.this);
                        }
                        if(!(sessionManager.getMake().equals("")||sessionManager.getModel().equals("")||sessionManager.getYear().equals("")||sessionManager.getColor().equals("")||sessionManager.getPlate().equals("")||sessionManager.getPermit().equals("")||sessionManager.getTourist().equals("")||sessionManager.getInsurance().equals("")||sessionManager.getFitness().equals("")||sessionManager.getNOC().equals("")||sessionManager.getRC().equals(""))) {
                            Intent intent = new Intent(vechiclephoto.this, Approval.class);
                            startActivity(intent);
                            Animatoo.animateSlideLeft(vechiclephoto.this);
                        }
                    } else {
                        Toast.makeText(vechiclephoto.this, result, Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(vechiclephoto.this, "Failed Upload" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
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



