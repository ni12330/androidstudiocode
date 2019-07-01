package com.example.android_project.reply;

import android.app.ProgressDialog;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.android_project.R;

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

public class ReplySelect extends AsyncTask<Void, Void, Void>{
    ArrayList<ReplyDTO> myItemArrayList;
    ReplyAdapter adapter;
    ProgressDialog progressDialog;
    String postURL = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    String board_id = "";

    public ReplySelect(ArrayList<ReplyDTO> myItemArrayList, ReplyAdapter adapter, int board_id) {
        this.myItemArrayList = myItemArrayList;
        this.adapter = adapter;
        this.board_id= String.valueOf(board_id);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            Uri.Builder builder1 = new Uri.Builder();
            builder1.appendQueryParameter("board_id", board_id);

            String postURL = ipConfig + "/index/replyList"+builder1;

            Log.d("reply", postURL);
            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());

            httpPost.setEntity(httpEntity);
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);


            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();

            inputStream.close();*/



        }catch (Exception e) {
            Log.d("reply", e.getMessage());
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




    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                myItemArrayList.add(readMessage(reader));
            }

            Log.d("arrayListSize", myItemArrayList.size() + ":");
            reader.endArray();
        } finally {
            reader.close();
        }
    }


    public ReplyDTO readMessage(JsonReader reader) throws IOException {

        String reply_content = "";
        String reply_user_id = "";
        String reply_ids = "";
        String board_ids = "";
        String reply_dates = "";


        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("reply_content")) {
                reply_content = reader.nextString();
            } else if (readStr.equals("user_id")) {
                reply_user_id = reader.nextString();
            } else if (readStr.equals("reply_id")) {
                reply_ids = reader.nextString();
            } else if (readStr.equals("board_id")) {
                board_ids = reader.nextString();
            } else if (readStr.equals("reply_date")) {
                reply_dates = reader.nextString();
            } else  {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new ReplyDTO(reply_content, reply_user_id, reply_dates, reply_ids, board_ids);



    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("reply", "reply Select Complete!!!");

        adapter.notifyDataSetChanged();
    }



}