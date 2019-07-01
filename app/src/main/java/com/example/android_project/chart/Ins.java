package com.example.android_project.chart;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;

import java.io.InputStream;
import java.nio.charset.Charset;

import static com.example.android_project.Common.CommonMethod.ipConfig;

public class Ins extends AsyncTask<Void, Void, String> {
    String day, mileage, price, liter,com_nm;
    String result = "";

    public Ins(String day, String mileage, String price, String liter,String com_nm) {
        this.mileage = mileage;
        this.price = price;
        this.liter = liter;
        this.day = day;
        this.com_nm = com_nm;
    }


    @Override
    protected String doInBackground(Void... voids) {

        String year = day.substring(0,4);
        String month = day.substring(5, day.indexOf("월"));
        String nowday = day.substring(day.indexOf("월")+1).replace("일", "");

        if(Integer.parseInt(month) < 10) {
            month = "0" + month;
        }

        if(Integer.parseInt(nowday) < 10) {
            nowday = "0" + nowday;
        }

        day = year + month + nowday;




        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));



            builder.addTextBody("user_id", user_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("month", day, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("car_mileage", mileage, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("oil_cost", price, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("oil", liter, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("com_nm", com_nm, ContentType.create("Multipart/related", "UTF-8"));




/*
            Uri.Builder builder1 = new Uri.Builder();
            builder1.appendQueryParameter("user_id", user_id);

            builder1.appendQueryParameter("month", day);
            builder1.appendQueryParameter("car_mileage", mileage);
            builder1.appendQueryParameter("oil_cost", price);
            builder1.appendQueryParameter("oil", liter);*/

            String postURL = ipConfig + "/index/oilinsert";

            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream inputStream = null;
            inputStream = httpEntity.getContent();

            int i;
            StringBuffer buffer = new StringBuffer();
            byte[] b = new byte[4096];

            while( (i = inputStream.read(b)) != -1){
                buffer.append(new String(b, 0, i));
            }

            result = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("sub", "Oil_Cost FAIL");
        }

        return result;
    }
}