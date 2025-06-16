package com.example.doan.model;

public class LoaiSp {
    int menu_id;
    String name;
    String image;
    int status;

    public LoaiSp(int menu_id, String name, String image, int status) {
        this.menu_id = menu_id;
        this.name = name;
        this.image = image;
        this.status = status;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
