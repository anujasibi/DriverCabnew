package creo.com.driver;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadRC {
    @Multipart
    @POST("update_rc/")
    Call<Result> uploadImage(@Part MultipartBody.Part photo, @Header("Authorization")String Token);

    @Multipart
    @POST("update_taxi_permit/")
    Call<Result> uploadImages(@Part MultipartBody.Part photo, @Header("Authorization")String Token);
    @Multipart
    @POST("update_vehicle_insurance/")
    Call<Result> uploadImagess(@Part MultipartBody.Part photo, @Header("Authorization")String Token);
    @Multipart
    @POST("update_tourist_permit/")
    Call<Result> uploadImagesss(@Part MultipartBody.Part photo, @Header("Authorization")String Token);
    @Multipart
    @POST("update_fitness/")
    Call<Result> uploadIma(@Part MultipartBody.Part photo, @Header("Authorization")String Token);
    @Multipart
    @POST("update_noc/")
    Call<Result> uploadNoc(@Part MultipartBody.Part photo, @Header("Authorization")String Token);
    @Multipart
    @POST("update_pic/")
    Call<Result> uploadPhoto(@Part MultipartBody.Part photo, @Header("Authorization")String Token);
}
