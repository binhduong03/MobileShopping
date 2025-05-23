package com.example.doan.retrofit;



import com.example.doan.model.LoaiSpModel;
import com.example.doan.model.SanPhamMoiModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getDienThoai(
            @Field("page") int page,
            @Field("loai") int loai
    );

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getLapTop(
            @Field("page") int page,
            @Field("loai") int loai
    );
}
