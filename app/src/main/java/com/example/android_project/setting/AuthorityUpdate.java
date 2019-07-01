package com.example.android_project.setting;

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

public class AuthorityUpdate extends AsyncTask<Void, Void, Void> {
    private String user_id, admin;

    public AuthorityUpdate(String user_id, String admin) {
        this.user_id = user_id;
        this.admin = admin;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

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
            builder1.appendQueryParameter("admin", admin);

            String postURL = ipConfig + "/index/authorityUpdate"+builder1;

            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream inputStream = null;
            inputStream = httpEntity.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
