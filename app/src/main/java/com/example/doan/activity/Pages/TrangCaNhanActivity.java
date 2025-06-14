package com.example.doan.activity.Pages;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.databinding.ActivityTrangCaNhanBinding;
import com.example.doan.retrofit.ApiBanHang;
import com.example.doan.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class TrangCaNhanActivity extends AppCompatActivity {
    ActivityTrangCaNhanBinding binding;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Toolbar toolbar;
    CircleImageView imgAvatar;
    TextView txtTen, txtEmail, txtSdt, txtDiaChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrangCaNhanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Anhxa();
        ActionToolBar();
        getData();


    }

    private void getData() {
        txtTen.setText(Utils.user_current.getUsername());
        txtEmail.setText(Utils.user_current.getEmail());
        txtSdt.setText(Utils.user_current.getPhone());
        txtDiaChi.setText(Utils.user_current.getAddress());
        String avatar = Utils.user_current.getAvatar();

        if (avatar != null && !avatar.isEmpty()) {
            if (avatar.contains("http")) {
                Glide.with(getApplicationContext()).load(avatar).into(imgAvatar);
            } else {
                String hinhanh = Utils.BASE_URL + "Images/" + avatar;
                Glide.with(getApplicationContext()).load(hinhanh).into(imgAvatar);
            }
        }
    }

    private void Anhxa() {
        txtTen = binding.txtName;
        txtEmail = binding.txtEmail;
        txtSdt = binding.txtPhone;
        txtDiaChi = binding.txtAddress;
        toolbar = binding.toolbarLienHe;
        imgAvatar = binding.imgAvatar;
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}