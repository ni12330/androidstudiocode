package com.example.android_project.Login_user;

public class LoginMemberDTO {


    public static String user_id, user_pw, user_name, phone, admin;
    public static String comp_id, car_name, car_nickname, car_image, car_mileage,mycar_star;
    public static String sky ="0",rain="1",temperature="27",weather;



    public LoginMemberDTO() {
    }




    public LoginMemberDTO(String user_id, String user_pw, String user_name, String phone, String admin, String comp_id, String car_name, String car_nickname, String car_image, String car_mileage,String mycar_star) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.phone = phone;
        this.admin = admin;
        this.comp_id = comp_id;
        this.car_name = car_name;
        this.car_nickname = car_nickname;
        this.car_image = car_image;
        this.car_mileage = car_mileage;
        this.mycar_star = mycar_star;

    }

    public static String getWeather() {
        return weather;
    }

    public static void setWeather(String weather) {
        LoginMemberDTO.weather = weather;
    }

    public static String getMycar_star() {
        return mycar_star;
    }

    public static void setMycar_star(String mycar_star) {
        LoginMemberDTO.mycar_star = mycar_star;
    }

    public static String getSky() {
        return sky;
    }

    public static void setSky(String sky) {
        LoginMemberDTO.sky = sky;
    }

    public static String getRain() {
        return rain;
    }

    public static void setRain(String rain) {
        LoginMemberDTO.rain = rain;
    }

    public static String getTemperature() {
        return temperature;
    }

    public static void setTemperature(String temperature) {
        LoginMemberDTO.temperature = temperature;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        LoginMemberDTO.user_id = user_id;
    }

    public static String getUser_pw() {
        return user_pw;
    }

    public static void setUser_pw(String user_pw) {
        LoginMemberDTO.user_pw = user_pw;
    }

    public static String getUser_name() {
        return user_name;
    }

    public static void setUser_name(String user_name) {
        LoginMemberDTO.user_name = user_name;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        LoginMemberDTO.phone = phone;
    }

    public static String getAdmin() {
        return admin;
    }

    public static void setAdmin(String admin) {
        LoginMemberDTO.admin = admin;
    }

    public static String getComp_id() {
        return comp_id;
    }

    public static void setComp_id(String comp_id) {
        LoginMemberDTO.comp_id = comp_id;
    }

    public static String getCar_name() {
        return car_name;
    }

    public static void setCar_name(String car_name) {
        LoginMemberDTO.car_name = car_name;
    }

    public static String getCar_nickname() {
        return car_nickname;
    }

    public static void setCar_nickname(String car_nickname) {
        LoginMemberDTO.car_nickname = car_nickname;
    }

    public static String getCar_image() {
        return car_image;
    }

    public static void setCar_image(String car_image) {
        LoginMemberDTO.car_image = car_image;
    }

    public static String getCar_mileage() {
        return car_mileage;
    }

    public static void setCar_mileage(String car_mileage) {
        LoginMemberDTO.car_mileage = car_mileage;
    }

}
