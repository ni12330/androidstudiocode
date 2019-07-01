package com.example.android_project.Member;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.nio.charset.Charset;

import static com.example.android_project.Common.CommonMethod.ipConfig;

public class MemberSignUp extends AsyncTask<Void, Void, Void> {

    private String user_id, user_pw, user_name, phone;

    public MemberSignUp(String user_id, String user_pw, String user_name, String  phone) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.phone = phone;
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
            builder1.appendQueryParameter("user_id",user_id);
            builder1.appendQueryParameter("user_pw",user_pw);
            builder1.appendQueryParameter("user_name",user_name);
            builder1.appendQueryParameter("phone",phone);

            String postURL = ipConfig + "/index/signUp"+builder1;

            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
