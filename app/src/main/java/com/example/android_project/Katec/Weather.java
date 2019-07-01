package com.example.android_project.Katec;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.android_project.Common.CommonMethod.ipConfig;

public class Weather extends AsyncTask<Void,Void,String> {

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;
    String result = "";


    String weather_x;
    String weather_y;
    public Weather(String weather_x, String weather_y) {
        this.weather_x = weather_x;
        this.weather_y = weather_y;
    }


    @Override
    protected String doInBackground(Void... voids) {
        try {
        InputStream inputStream = null;
        // MultipartEntityBuild 생성
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Charset.forName("UTF-8"));

        Uri.Builder builder1 = new Uri.Builder();
        builder1.appendQueryParameter("weather_x", weather_x);
        builder1.appendQueryParameter("weather_y", weather_y);

        String postURL = ipConfig + "/index/weather" + builder1;
        HttpClient httpClient = AndroidHttpClient.newInstance("Android");
        HttpPost httpPost = new HttpPost(postURL);
        httpPost.setEntity(builder.build());
        httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        inputStream = httpEntity.getContent();


            /*하늘상태(SKY) 코드 : 맑음(1), 구름조금(2), 구름많음(3), 흐림(4)

                    - 강수형태(PTY) 코드 : 없음(0), 비(1), 비/눈(2), 눈(3)
            여기서 비/눈은 비와 눈이 섞여 오는 것을 의미 (진눈개비)*/


            int i;
            StringBuffer buffer = new StringBuffer();
            byte[] b = new byte[4096];

            while( (i = inputStream.read(b)) != -1){
                buffer.append(new String(b, 0, i));
            }
            result = buffer.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

}
