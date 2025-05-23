package com.example.doan.model;

public class SanPhamMoi {
    private int sanphammoi_id;
    private String tensp;
    private String hinhanh;
    private String giasp;
    private String mota;
    private int loai;

    public int getSanphammoi_id() {
        return sanphammoi_id;
    }

    public void setSanphammoi_id(int sanphammoi_id) {
        this.sanphammoi_id = sanphammoi_id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }
}
