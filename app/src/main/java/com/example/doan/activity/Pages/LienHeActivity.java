package com.example.doan.activity.Pages;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doan.R;
import com.example.doan.databinding.ActivityLienHeBinding;
import com.example.doan.retrofit.ApiBanHang;
import com.example.doan.retrofit.RetrofitClient;
import com.example.doan.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LienHeActivity extends AppCompatActivity {

    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ActivityLienHeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLienHeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        if (Utils.user_current != null) {
            binding.edtHoTen.setText(Utils.user_current.getUsername());
            binding.edtEmail.setText(Utils.user_current.getEmail());
            binding.edtSdt.setText(Utils.user_current.getPhone());
            binding.edtSdt.setEnabled(false);
            binding.edtHoTen.setEnabled(false);
            binding.edtEmail.setEnabled(false);
        } else {
            binding.edtHoTen.setEnabled(true);
            binding.edtEmail.setEnabled(true);
        }

        binding.btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themlienhe();
            }
        });


    }

    private void themlienhe() {
        int user_id = Utils.user_current.getUser_id();
        int is_read = 0;
        String str_ten = binding.edtHoTen.getText().toString().trim();
        String str_email = binding.edtEmail.getText().toString().trim();
        String str_noidung = binding.edtNoiDung.getText().toString().trim();


        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_noidung)){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        } else {
            compositeDisposable.add(apiBanHang.insertLH(user_id,str_noidung,is_read)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(),messageModel.getMessage(),Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),messageModel.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage() , Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }


}