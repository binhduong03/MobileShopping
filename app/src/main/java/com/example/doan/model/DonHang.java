package com.example.doan.model;

import java.util.List;

public class DonHang {
    int donhang_id;
    int user_id;
    String diachi;
    String sodienthoai;
    String tongtien;
    List<Item> item;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDonhang_id() {
        return donhang_id;
    }

    public void setDonhang_id(int donhang_id) {
        this.donhang_id = donhang_id;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
