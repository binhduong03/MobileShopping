package com.example.doan.activity.Admin;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.R;
import com.example.doan.databinding.ActivityThemspBinding;
import com.example.doan.model.MessageModel;
import com.example.doan.model.SanPhamMoi;
import com.example.doan.retrofit.ApiBanHang;
import com.example.doan.retrofit.RetrofitClient;
import com.example.doan.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSPActivity extends AppCompatActivity {
    Spinner spinner;
    int loai=0;
    ActivityThemspBinding binding;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String mediaPath;
    SanPhamMoi sanPhamSua;
    boolean flag =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemspBinding.inflate(getLayoutInflater());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        setContentView(binding.getRoot());
        initView();
        initData();

        Intent intent = getIntent();
        sanPhamSua = (SanPhamMoi) intent.getSerializableExtra("sua");

        if(sanPhamSua == null){
            //them moi
            flag = false;
        } else {
            flag = true;
            binding.bntthem.setText("Sửa sản phẩm");
            //hiện thị dữ liệu
            binding.tensp.setText(sanPhamSua.getName());
            binding.giasp.setText(sanPhamSua.getPrice());
            binding.mota.setText(sanPhamSua.getDescription());
            binding.hinhanh.setText(sanPhamSua.getImage());
            binding.spinnerLoai.setSelection(sanPhamSua.getType());
        }


    }

    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọ loại");
        stringList.add("Loại 1");
        stringList.add("Loại 2");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.bntthem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (flag == false) {
                   themsanpham();
               } else {
                   suaSanPham();
               }
           }
        });

        binding.imgcamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ImagePicker.with(ThemSPActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
    }

    private void suaSanPham() {
        String str_tensp = binding.tensp.getText().toString();
        String str_giasp = binding.giasp.getText().toString();
        String str_hinhanh = binding.hinhanh.getText().toString();
        String str_mota = binding.mota.getText().toString();

        if (TextUtils.isEmpty(str_tensp) || TextUtils.isEmpty(str_giasp) || TextUtils.isEmpty(str_hinhanh) || TextUtils.isEmpty(str_mota) || loai==0){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
        } else {
            compositeDisposable.add(apiBanHang.updateSp(sanPhamSua.getProduct_id(),str_tensp, str_giasp, str_hinhanh, str_mota, loai)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage() , Toast.LENGTH_LONG).show();
                            }
                    ));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFiles();
        Log.d("log", "onActivityResult: "+mediaPath);
    }

    private void themsanpham() {
        String str_tensp = binding.tensp.getText().toString();
        String str_giasp = binding.giasp.getText().toString();
        String str_hinhanh = binding.hinhanh.getText().toString();
        String str_mota = binding.mota.getText().toString();
        if (TextUtils.isEmpty(str_tensp) || TextUtils.isEmpty(str_giasp) || TextUtils.isEmpty(str_hinhanh) || TextUtils.isEmpty(str_mota) || loai==0){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
        } else {
            compositeDisposable.add(apiBanHang.insertSp(str_tensp, str_giasp, str_hinhanh, str_mota, (loai))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    messageModel -> {
                        if (messageModel.isSuccess()){
                            Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    },
                    throwable -> {
                        Toast.makeText(getApplicationContext(), throwable.getMessage() , Toast.LENGTH_LONG).show();
                    }
            ));

        }
    }

    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null,null);
        if (cursor == null){
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    private void uploadMultipleFiles() {

        Uri uri = Uri.parse(mediaPath);
        File file = new File(getPath(uri));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
        Call<MessageModel> call = apiBanHang.uploadFile(fileToUpload1);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call < MessageModel > call, Response< MessageModel > response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        binding.hinhanh.setText(serverResponse.getName());
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("Response", serverResponse.toString());
                }

            }
            @Override
            public void onFailure(Call < MessageModel > call, Throwable t) {
                Log.d("log", t.getMessage());
            }
        });
    }

    private void initView() {
        spinner = findViewById(R.id.spinner_loai);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}

