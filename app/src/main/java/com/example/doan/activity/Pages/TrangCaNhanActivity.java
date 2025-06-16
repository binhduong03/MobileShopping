package com.example.doan.activity.Pages;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.activity.BaseActivity;
import com.example.doan.databinding.ActivityTrangCaNhanBinding;
import com.example.doan.retrofit.ApiBanHang;
import com.example.doan.retrofit.RetrofitClient;
import com.example.doan.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrangCaNhanActivity extends BaseActivity {
    ActivityTrangCaNhanBinding binding;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Toolbar toolbar;
    CircleImageView imgAvatar;
    TextView txtTen, txtEmail, txtSdt, txtDiaChi, btnSua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrangCaNhanBinding.inflate(getLayoutInflater());
        setContentLayoutt(binding.getRoot());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        ActionToolBar();
        getData();
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditInfoDialog();
            }
        });


    }

    private void showEditInfoDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.sua_trangcanhan, null);

        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);
        EditText edtPhone = dialogView.findViewById(R.id.edtPhone);
        EditText edtAddress = dialogView.findViewById(R.id.edtAddress);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmit);

        edtName.setText(txtTen.getText().toString());
        edtEmail.setText(txtEmail.getText().toString());
        edtPhone.setText(txtSdt.getText().toString());
        edtAddress.setText(txtDiaChi.getText().toString());

        AlertDialog dialog = new AlertDialog.Builder(this, R.style.RoundedDialog)
                .setView(dialogView)
                .create();

        btnSubmit.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            // Gọi API cập nhật
            compositeDisposable.add(apiBanHang.updateUser(Utils.user_current.getUser_id(), name, email, phone, address)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        if (messageModel.isSuccess()) {
                                            txtTen.setText(name);
                                            txtEmail.setText(email);
                                            txtSdt.setText(phone);
                                            txtDiaChi.setText(address);

                                            Utils.user_current.setUsername(name);
                                            Utils.user_current.setEmail(email);
                                            Utils.user_current.setPhone(phone);
                                            Utils.user_current.setAddress(address);

                                            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            )
            );
        });

        dialog.show();
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
                String hinhanh = Utils.BASE_URL_HINHANH + "public/backend/images/user/" + avatar;
                Glide.with(getApplicationContext()).load(hinhanh).into(imgAvatar);
            }
        }
    }

    private void Anhxa() {
        txtTen = binding.txtName;
        txtEmail = binding.txtEmail;
        txtSdt = binding.txtPhone;
        txtDiaChi = binding.txtAddress;
        btnSua = binding.btnEdit;
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

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}

