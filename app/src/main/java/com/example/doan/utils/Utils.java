package com.example.doan.utils;

import com.example.doan.model.GioHang;
import com.example.doan.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //192.168.0.101 ip máy tính.
    public static final String BASE_URL = "http://10.0.2.2:81/MobileShopping/api/";

    // hình ảnh
    public static final String BASE_URL_HINHANH = "http://10.0.2.2:81/MobileShopping/";
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
}

