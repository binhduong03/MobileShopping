package com.example.doan.retrofit;



import com.example.doan.model.DonHangModel;
import com.example.doan.model.LoaiSpModel;
import com.example.doan.model.MessageModel;
import com.example.doan.model.SanPhamMoiModel;
import com.example.doan.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiBanHang {
    //GET DATA
    @GET("menu")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("all-product")
    Observable<SanPhamMoiModel> getSpMoi();

    @POST("products-by-type")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getDienThoai(
            @Field("page") int page,
            @Field("type") int type
    );


    //POST DATA
    @POST("products-by-type")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getLapTop(
            @Field("page") int page,
            @Field("type") int type
    );

    @POST("register")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("phone") String phone,
            @Field("uid") String uid,
            @Field("role") int role

    );

    @POST("login-user")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("send-reset-mail")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );

    @POST("place-order")
    @FormUrlEncoded
    Observable<UserModel> createOder(
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("total_amount") String total_amount,
            @Field("user_id") int user_id,
            @Field("address") String address,
            @Field("quantity") int quantity,
            @Field("chitiet") String chitiet
    );

    @POST("history")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("user_id") int user_id
    );

    @POST("search")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );


    @POST("update-token")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("user_id") int user_id,
            @Field("token") String token

    );

    @POST("contact")
    @FormUrlEncoded
    Observable<MessageModel> insertLH(
            @Field("user_id") int user_id,
            @Field("message") String message,
            @Field("is_read") int is_read
    );


    @POST("update-user")
    @FormUrlEncoded
    Observable<MessageModel> updateUser(
            @Field("user_id") int userId,
            @Field("username") String username,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("address") String address
    );


}
