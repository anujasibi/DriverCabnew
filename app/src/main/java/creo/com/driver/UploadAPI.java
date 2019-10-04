package creo.com.driver;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadAPI {
    @Multipart
    @POST("driver_signup/")
    Call<Result> uploadImage(@Part MultipartBody.Part photo, @Part("name") RequestBody name, @Part("phone_no") RequestBody phone_no, @Part("email") RequestBody email, @Part("username") RequestBody username, @Part("password") RequestBody password, @Part("stockpoint") RequestBody stockpoint);
}
