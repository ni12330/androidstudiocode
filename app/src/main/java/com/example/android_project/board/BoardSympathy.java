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

import java.io.InputStream;
import java.nio.charset.Charset;

import static com.example.android_project.Common.CommonMethod.ipConfig;

public class BoardSympathy extends AsyncTask<Void, Void, String> {

    String boar_id;
    String user_id;
    String result = "";

    public BoardSympathy(int board_id, String user_id) {
        this.boar_id = String.valueOf(board_id);
        this.user_id = user_id;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            Uri.Builder builder1 = new Uri.Builder();

            builder1.appendQueryParameter("board_id", boar_id);
            builder1.appendQueryParameter("user_id", user_id);

            String postURL = ipConfig + "/index/sympathy"+builder1;

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

            Log.d("sub", result);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("sub", "idCheck Fail");
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
