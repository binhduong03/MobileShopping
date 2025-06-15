package com.example.doan.model;

public class LienHe {
    int contact_id;
    int user_id;
    String message;
    int is_read;

    public LienHe(int contact_id, int user_id, String message, int is_read) {
        this.contact_id = contact_id;
        this.user_id = user_id;
        this.message = message;
        this.is_read = is_read;
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }
}
