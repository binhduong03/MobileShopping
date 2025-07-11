package com.example.doan.activity.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.doan.R;
import com.example.doan.activity.BaseActivity;
import com.example.doan.activity.MainActivity;
import com.example.doan.retrofit.ApiBanHang;
import com.example.doan.retrofit.RetrofitClient;
import com.example.doan.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends BaseActivity {
    Toolbar toolbar;
    TextView txtTongtien, txtPhone, txtEmail;
    EditText edtdiachi;

    AppCompatButton btnDathang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;

    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Thanh toán đơn hàng";
    private String merchantCode = "SCB01";
    private String merchantNameLabel = "BanHang";
    private String description = "mua hàng online";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_thanh_toan);
        initView();
        countItem();
        initControl();

    }

    private void countItem(){
        totalItem = 0;
        for(int i = 0; i<Utils.mangmuahang.size(); i++){
            totalItem = totalItem + Utils.mangmuahang.get(i).getQuantity();
        }
    }

    private void initControl(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);
        txtTongtien.setText(decimalFormat.format(tongtien) + 'đ');
        txtEmail.setText(Utils.user_current.getEmail());
        txtPhone.setText(Utils.user_current.getPhone());

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    //post data
                    String str_email = Utils.user_current.getEmail();
                    String str_sdt = Utils.user_current.getPhone();
                    int str_user_id = Utils.user_current.getUser_id();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));

                    compositeDisposable.add(apiBanHang.createOder(str_email,str_sdt, String.valueOf(tongtien), str_user_id, str_diachi, totalItem, new Gson().toJson(Utils.mangmuahang)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                            userModel -> {
                                Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                                Utils.mangmuahang.clear();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                startActivity(intent);

                                finish();
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
                }
            }
        });
    }

    private void initView(){
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toolbar);
        txtTongtien = findViewById(R.id.txtTongtien);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        edtdiachi = findViewById(R.id.edtdiachi);
        btnDathang = findViewById(R.id.btnDathang);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}