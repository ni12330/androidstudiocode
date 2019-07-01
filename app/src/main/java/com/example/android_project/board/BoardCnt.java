package com.example.android_project.board;

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

public class BoardCnt extends AsyncTask<Void, Void, Void> {

    String board_id;
    String readcnt;
    public BoardCnt(int board_id,int readcnt) {
        this.board_id = String.valueOf(board_id);
        this.readcnt = String.valueOf(readcnt);

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

            builder1.appendQueryParameter("board_id", board_id);
            builder1.appendQueryParameter("readcnt", readcnt);

            String postURL = ipConfig + "/index/boandcnt"+builder1;

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