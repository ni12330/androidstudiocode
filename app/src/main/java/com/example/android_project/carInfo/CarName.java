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

import java.io.InputStream;

import static com.example.android_project.Common.CommonMethod.ipConfig;

public class CarName extends AsyncTask<Void, Void, String> {

    String result = "" ,comp_id = "";
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    public CarName(String comp_id) {
        this.comp_id = comp_id;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            Uri.Builder builder1 = new Uri.Builder();

            builder1.appendQueryParameter("comp_id", comp_id);

            // 전송
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(ipConfig+"/index/car_name"+builder1);
            httpPost.setEntity(builder.build());
            httpPost.setEntity(httpEntity);
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();

            InputStream inputStream = null;
            inputStream = httpEntity.getContent();

            int i;
            StringBuffer buffer = new StringBuffer();
            byte[] b = new byte[4096];
            while( (i = inputStream.read(b)) != -1){
                buffer.append(new String(b, 0, i));
            }
            result = buffer.toString();

        }catch (Exception e) {
            Log.d("Sub1", e.getMessage());
            e.printStackTrace();
        }finally {
            httpClient = null;
            httpClient = null;
            httpPost = null;
            httpResponse = null;
            httpEntity = null;
        }

        return result;
    }
}
