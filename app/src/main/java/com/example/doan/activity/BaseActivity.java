package com.example.doan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.os.Bundle;
import com.example.doan.R;
import com.example.doan.activity.Pages.SearchActivity;
import com.example.doan.activity.Pages.TrangCaNhanActivity;
import com.example.doan.activity.Pages.XemDonActivity;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected ImageView home, imgSearch, history, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        // Gán các nút điều hướng
        home = findViewById(R.id.home);
        imgSearch = findViewById(R.id.imgSearch);
        history = findViewById(R.id.history);
        user = findViewById(R.id.user);

        home.setOnClickListener(v -> {
            setActiveTab(home);
            startActivity(new Intent(this, MainActivity.class));
        });
        imgSearch.setOnClickListener(v -> {
            setActiveTab(imgSearch);
            startActivity(new Intent(this, SearchActivity.class));
        });
        history.setOnClickListener(v -> {
            setActiveTab(history);
            startActivity(new Intent(this, XemDonActivity.class));
        });
        user.setOnClickListener(v -> {
            setActiveTab(user);
            startActivity(new Intent(this, TrangCaNhanActivity.class));
        });
        if (this instanceof MainActivity) {
            setActiveTab(home);
        } else if (this instanceof SearchActivity) {
            setActiveTab(imgSearch);
        } else if (this instanceof XemDonActivity) {
            setActiveTab(history);
        } else if (this instanceof TrangCaNhanActivity) {
            setActiveTab(user);
        }

    }

    protected void setActiveTab(ImageView selectedTab) {
        ImageView[] tabs = {home, imgSearch, history, user};

        for (ImageView tab : tabs) {
            // Reset về trạng thái mặc định
            tab.setColorFilter(Color.parseColor("#80FFFFFF")); // icon xám mờ
            tab.setScaleX(1f);
            tab.setScaleY(1f);
            ((FrameLayout) tab.getParent()).setBackground(null); // xoá nền trắng
        }

        // Đặt tab được chọn
        selectedTab.setColorFilter(Color.BLACK); // icon đen
        selectedTab.setScaleX(1.3f);
        selectedTab.setScaleY(1.3f);

        // Đặt nền tròn trắng
        FrameLayout parent = (FrameLayout) selectedTab.getParent();
        parent.setBackgroundResource(R.drawable.circle_white);
    }


    // Gọi hàm này để đặt nội dung tùy theo từng Activity con
    public void setContentLayout(int layoutResID) {
        FrameLayout baseContent = findViewById(R.id.baseContent);
        LayoutInflater.from(this).inflate(layoutResID, baseContent, true);
    }

    public void setContentLayoutt(View view) {
        FrameLayout baseContent = findViewById(R.id.baseContent);
        baseContent.addView(view);
    }
}

