package com.example.android_project.record;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

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

public class MyrecordDelete extends AsyncTask<Void, Void, Void> {

    String record_id = "";
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;


    public MyrecordDelete(String record_id) {
        this.record_id = record_id;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            Uri.Builder builder1 = new Uri.Builder();
            builder1.appendQueryParameter("user_id", user_id);
            builder1.appendQueryParameter("record_id", record_id);

            String postURL = ipConfig + "/index/record_delete" + builder1;

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());

            httpPost.setEntity(httpEntity);
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            httpEntity.getContent();



        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            httpClient = null;
            httpClient = null;
            httpPost = null;
            httpResponse = null;
            httpEntity = null;
        }

        return null;
    }
}
