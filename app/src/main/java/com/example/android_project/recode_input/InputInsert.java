package com.example.android_project.recode_input;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.InputStream;
import java.nio.charset.Charset;

import static com.example.android_project.Common.CommonMethod.ipConfig;
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;

public class InputInsert extends AsyncTask<Void, Void, String>  {
    String name, cost, memo, check_date, check_mileage, a = "";

    public InputInsert(String name, String cost, String memo, String check_date, String check_mileage) {
        this.name = name;
        this.cost = cost;
        this.memo = memo;
        //2019년5월5일
        String year = check_date.substring(0,4);
        String month = check_date.substring(5, check_date.indexOf("월"));
        String day = check_date.substring(check_date.indexOf("월")+1).replace("일", "");

        if(Integer.parseInt(month) < 10) {
            month = "0" + month;
        }

        if(Integer.parseInt(day) < 10) {
            day = "0" + day;
        }

        this.check_date = year + month + day;

        this.check_mileage = check_mileage;
    }

    @Override
    protected String doInBackground(Void... String) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            Uri.Builder builder1 = new Uri.Builder();
            builder1.appendQueryParameter("repair_name", name);
            builder1.appendQueryParameter("repair_cost",cost);
            builder1.appendQueryParameter("memo", memo);
            builder1.appendQueryParameter("user_id", user_id);
            builder1.appendQueryParameter("month", check_date);
            builder1.appendQueryParameter("car_mileage", check_mileage);

            String postURL = ipConfig + "/index/inputInsert"+ builder1;

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

            a = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }
}