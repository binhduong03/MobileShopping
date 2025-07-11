package com.example.doan.activity.Pages;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.activity.BaseActivity;
import com.example.doan.model.GioHang;
import com.example.doan.model.SanPhamMoi;
import com.example.doan.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChiTietActivity extends BaseActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (Utils.manggiohang.size() > 0) {
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                if (Utils.manggiohang.get(i).getProduct_id() == sanPhamMoi.getProduct_id()) {
                    Utils.manggiohang.get(i).setQuantity(soluong + Utils.manggiohang.get(i).getQuantity());

                    flag = true;
                }
            }
            if (flag == false) {
                long gia = Long.parseLong(sanPhamMoi.getPrice());
                GioHang giohang = new GioHang();
                giohang.setPrice(gia);
                giohang.setQuantity(soluong);
                giohang.setProduct_id(sanPhamMoi.getProduct_id());
                giohang.setName(sanPhamMoi.getName());
                giohang.setImage(sanPhamMoi.getImage());
                Utils.manggiohang.add(giohang);
            }
        } else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getPrice());
            GioHang giohang = new GioHang();
            giohang.setPrice(gia);
            giohang.setQuantity(soluong);
            giohang.setProduct_id(sanPhamMoi.getProduct_id());
            giohang.setName(sanPhamMoi.getName());
            giohang.setImage(sanPhamMoi.getImage());
            Utils.manggiohang.add(giohang);
        }
        int totalItem = 0;
        for(int i = 0; i<Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getQuantity();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getName());
        mota.setText(sanPhamMoi.getDescription());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getImage()).into(imghinhanh);
        if (sanPhamMoi.getImage().contains("http")){
            Glide.with(getApplicationContext()).load(sanPhamMoi.getImage()).into(imghinhanh);
        } else {
            String hinh = Utils.BASE_URL_HINHANH+"public/backend/assets/img/product/"+sanPhamMoi.getImage();
            Glide.with(getApplicationContext()).load(hinh).into(imghinhanh);

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPhamMoi.getPrice())) + "đ");
        Integer[] so = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapterSpinner = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterSpinner);
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        imghinhanh = findViewById(R.id.imgchitiet);
        toolbar = findViewById(R.id.toolbar);
        badge = findViewById(R.id.menu_sl);

        FrameLayout frameLayoutgiohang = findViewById(R.id.frameitemGiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i = 0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getQuantity();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i = 0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getQuantity();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
}