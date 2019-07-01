package com.example.android_project.Member;

public class MemberDTO {
    private String user_id, user_pw, user_name, phone, admin;

    public MemberDTO(String user_id, String admin) {
        this.user_id = user_id;
        this.admin = admin;
    }

    public MemberDTO(String user_id, String user_pw, String user_name, String phone, String admin) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.phone = phone;
        this.admin = admin;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
