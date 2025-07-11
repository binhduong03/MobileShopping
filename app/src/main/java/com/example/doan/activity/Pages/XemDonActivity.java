package com.example.doan.activity.Pages;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.activity.BaseActivity;
import com.example.doan.adapter.DonHangAdapter;
import com.example.doan.retrofit.ApiBanHang;
import com.example.doan.retrofit.RetrofitClient;
import com.example.doan.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonActivity extends BaseActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView reDonhang;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentLayout(R.layout.activity_xem_don);
        initView();
        initToolbar();
        getOrder();
    }

    private void getOrder() {
        if (Utils.user_current == null) {
            Log.e("XemDon", "user_current is null!");
            return;
        }

        compositeDisposable.add(apiBanHang.xemDonHang(Utils.user_current.getUser_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            if (donHangModel != null && donHangModel.isSuccess() && donHangModel.getResult() != null) {
                                Log.d("XemDon", "Số đơn hàng: " + donHangModel.getResult().size());
                                DonHangAdapter adapter = new DonHangAdapter(getApplicationContext(), donHangModel.getResult()); // đổi getApplicationContext()
                                reDonhang.setAdapter(adapter);
                            } else {
                                Log.e("XemDon", "Dữ liệu đơn hàng không hợp lệ hoặc rỗng");
                            }
                        }, throwable -> {
                            Log.e("XemDon", "Lỗi gọi API: " + throwable.getMessage());
                            throwable.printStackTrace();
                        }
                ));
    }


    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        reDonhang = findViewById(R.id.recycleview_Donhang);
        toolbar = findViewById(R.id.toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reDonhang.setLayoutManager(layoutManager);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}