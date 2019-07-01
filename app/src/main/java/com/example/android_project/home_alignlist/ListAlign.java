package com.example.android_project.home_alignlist;


import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;

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

public class ListAlign extends AsyncTask<Void, Void, Void> {
    ArrayList<AlignDTO> myItemArrayList;
    AlignAdapter adapter;
    String postURL = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    public ListAlign(ArrayList<AlignDTO> myItemArrayList, AlignAdapter adapter, String url) {
        this.myItemArrayList = myItemArrayList;
        this.adapter = adapter;
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
        }catch (Exception e){
            e.printStackTrace();
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

    public AlignDTO readMessage(JsonReader reader) throws IOException {

        String repair_id = "",repair_name = "",repair_term = "",repair_mile = "";
        reader.beginObject();

        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("repair_id")) {
                repair_id = reader.nextString();
            } else if (readStr.equals("repair_name")) {
                repair_name = reader.nextString();
            } else if (readStr.equals("repair_term")) {
                repair_term = reader.nextString();
            } else if (readStr.equals("repair_mile")) {
                repair_mile = reader.nextString();
            } else  {
                reader.skipValue();
            }
        }
        reader.endObject();
        // Log.d("listselect:myitem", board_id + "," + board_title + "," + board_content );
        return new AlignDTO(repair_id, repair_name, repair_term, repair_mile);

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.notifyDataSetChanged();
    }

}
