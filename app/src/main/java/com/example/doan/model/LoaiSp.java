package com.example.doan.model;

public class LoaiSp {
    int sanpham_id;
    String tensanpham;
    String hinhanh;

    public LoaiSp( String hinhanh, String tensanpham) {

        this.hinhanh = hinhanh;
        this.tensanpham = tensanpham;
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

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

}
