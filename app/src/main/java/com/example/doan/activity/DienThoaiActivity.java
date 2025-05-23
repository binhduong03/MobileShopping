package com.example.doan.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.adapter.DienThoaiAdapter;
import com.example.doan.model.SanPhamMoi;
import com.example.doan.retrofit.ApiBanHang;
import com.example.doan.retrofit.RetrofitClient;
import com.example.doan.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;
    DienThoaiAdapter adapterDt;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;
    boolean isLastPage = false; // ✅ Thêm biến này để ngăn load khi hết dữ liệu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        loai = getIntent().getIntExtra("loai", 1);
        AnhXa();
        ActionToolBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && !isLastPage &&
                        linearLayoutManager.findLastVisibleItemPosition() == sanPhamMoiList.size() - 1) {
                    loadMore();
                    isLoading = true;
                }
            }
        });
    }

    private void loadMore() {
        handler.post(() -> {
            sanPhamMoiList.add(null);
            adapterDt.notifyItemInserted(sanPhamMoiList.size() - 1);
        });

        handler.postDelayed(() -> {
            sanPhamMoiList.remove(sanPhamMoiList.size() - 1);
            adapterDt.notifyItemRemoved(sanPhamMoiList.size());
            page++;
            getData(page);
        }, 1500);
    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getDienThoai(page, loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                List<SanPhamMoi> result = sanPhamMoiModel.getResult();

                                // Nếu không có thêm dữ liệu => đánh dấu là trang cuối
                                if (result.size() == 0) {
                                    isLastPage = true;
                                    return;
                                }

                                if (adapterDt == null) {
                                    sanPhamMoiList = result;
                                    adapterDt = new DienThoaiAdapter(getApplicationContext(), sanPhamMoiList);
                                    recyclerView.setAdapter(adapterDt);
                                } else {
                                    int vitri = sanPhamMoiList.size();
                                    sanPhamMoiList.addAll(result);
                                    adapterDt.notifyItemRangeInserted(vitri, result.size());
                                }

                                isLoading = false;
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Không kết nối được server", Toast.LENGTH_SHORT).show();
                            isLoading = false;
                        }
                ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recyclerview_dt);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
