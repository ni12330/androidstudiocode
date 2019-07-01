package com.example.android_project.carInfo;

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

import java.nio.charset.Charset;

import static com.example.android_project.Common.CommonMethod.ipConfig;

public class MycarstarInsert extends AsyncTask<Void, Void, Void> {

    String user_id;
    String mycar_star;
    public MycarstarInsert(String user_id, String mycar_star) {
        this.user_id = user_id;
        this.mycar_star = mycar_star;

    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            Uri.Builder builder1 = new Uri.Builder();

            builder1.appendQueryParameter("user_id", user_id);
            builder1.appendQueryParameter("mycar_star", mycar_star);

            String postURL = ipConfig + "/index/mycar_star"+builder1;

            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            httpEntity.getContent();


        }catch (Exception e) {
            e.printStackTrace();
            Log.d("sub", "BoardCnt Fail");
        }
        return null;
    }
}
