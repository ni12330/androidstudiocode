package com.example.android_project.board;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Adapter;
import android.widget.SimpleCursorAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.android_project.Common.CommonMethod.ipConfig;


public class ListSelect extends AsyncTask<Void, Void, Void> {
    ArrayList<BoardDTO> myItemArrayList;
    BoardAdapter adapter;
    ProgressDialog progressDialog;
    String postURL = "";
    String kind = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;


    public ListSelect(ArrayList<BoardDTO> myItemArrayList, BoardAdapter adapter, ProgressDialog progressDialog,String url) {
        this.myItemArrayList = myItemArrayList;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
        postURL = ipConfig + url;
    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected Void doInBackground(Void... voids) {

        myItemArrayList.clear();


        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

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
            Log.d("Sub1", e.getMessage());
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
            reader.endArray();
        } finally {
            reader.close();
        }
    }
    public BoardDTO readMessage(JsonReader reader) throws IOException {

        String board_title = "",board_content = "",user_id = "",comp_id = "",board_date = "",mycar = "",filepath = "",filename = "",board_user_nick="";
        int board_id = 0,readcnt = 0,board_reply_cnt = 0,sympathy=0;



        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("board_title")) {
                board_title = reader.nextString();
            } else if (readStr.equals("board_content")) {
                board_content = reader.nextString();
            } else if (readStr.equals("user_id")) {
                user_id = reader.nextString();
            } else if (readStr.equals("comp_id")) {
                comp_id = reader.nextString();
            } else if (readStr.equals("board_date")) {
                board_date = reader.nextString();
            } else if (readStr.equals("mycar")) {
                mycar = reader.nextString();
            } else if (readStr.equals("filepath")) {
                filepath = reader.nextString();
            } else if (readStr.equals("filename")) {
                filename = reader.nextString();
            } else if (readStr.equals("board_id")) {
                board_id = Integer.parseInt(reader.nextString());
            } else if (readStr.equals("readcnt")) {
                readcnt = Integer.parseInt(reader.nextString());
            } else if(readStr.equals("board_reply_cnt")){
                board_reply_cnt = Integer.parseInt(reader.nextString());
            } else if(readStr.equals("sympathy")){
                sympathy = Integer.parseInt(reader.nextString());
            }else if(readStr.equals("board_user_nick")){
                board_user_nick = reader.nextString();
            } else  {
                reader.skipValue();
            }
        }
        reader.endObject();
        // Log.d("listselect:myitem", board_id + "," + board_title + "," + board_content );
        return new BoardDTO(board_title, board_content, user_id, comp_id, board_date, board_id, readcnt,mycar,filepath,filename,board_reply_cnt,sympathy,board_user_nick);


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("Sub1", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }


}