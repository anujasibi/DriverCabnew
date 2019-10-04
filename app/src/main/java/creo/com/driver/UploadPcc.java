package creo.com.driver;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadPcc {
    @Multipart
    @POST("update_pcc/")
    Call<Result> uploadImage(@Part MultipartBody.Part photo, @Header("Authorization")String Token);
}
