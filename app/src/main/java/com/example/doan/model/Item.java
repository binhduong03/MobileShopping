package com.example.doan.model;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("sanphammoi_id")
    private int sanpham_id;

    @SerializedName("tensp")
    private String tensanpham;

    @SerializedName("soluong")
    private int soluong;

    @SerializedName("gia")
    private String gia;

    String hinhanh;

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSanpham_id() {
        return sanpham_id;
    }

    public void setSanpham_id(int sanpham_id) {
        this.sanpham_id = sanpham_id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
