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
    @GET("all-menu.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getDienThoai(
            @Field("page") int page,
            @Field("type") int type
    );


    //POST DATA
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getLapTop(
            @Field("page") int page,
            @Field("type") int type
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("phone") String phone,
            @Field("uid") String uid,
            @Field("role") int role

    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );

    @POST("donhang.php")
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

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("user_id") int user_id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );

    //Quản lý sản phẩm
    @POST("Admin/SanPham/delete_sp.php")
    @FormUrlEncoded
    Observable<MessageModel> deleteSp(
            @Field("product_id") int product_id
    );

    @POST("Admin/SanPham/insert_sp.php")
    @FormUrlEncoded
    Observable<MessageModel> insertSp(
            @Field("tensp") String tensp,
            @Field("giasp") String giasp,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai
    );

    @POST("Admin/SanPham/update_sp.php")
    @FormUrlEncoded
    Observable<MessageModel> updateSp(
            @Field("sanphammoi_id") int sanphammoi_id,
            @Field("tensp") String tensp,
            @Field("giasp") String giasp,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai
    );

    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("user_id") int user_id,
            @Field("token") String token

    );

    @Multipart
    @POST("Admin/SanPham/upload.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);


    @POST("LienHe/insert_lh.php")
    @FormUrlEncoded
    Observable<MessageModel> insertLH(
            @Field("user_id") int user_id,
            @Field("message") String message,
            @Field("is_read") int is_read
    );

}
