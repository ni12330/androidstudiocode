package com.example.android_project.recode_input;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

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

public class RepairList extends AsyncTask<Void, Void, Void> {
    ArrayList<RepairDTO> arrayList;
    RepairAdapter adapter;
    ProgressDialog progressDialog;
    String postURL = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    public RepairList(ArrayList<RepairDTO> arrayList, RepairAdapter adapter) {
        this.arrayList = arrayList;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            String postURL = ipConfig + "/index/repairList";

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
                arrayList.add(readMessage(reader));
            }

            reader.endArray();
        } finally {
            reader.close();
        }
    }


    public RepairDTO readMessage(JsonReader reader) throws IOException {

        String repair_id = "", repair_name = "", repair_term = "", repair_mile = "";


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

        return new RepairDTO(repair_id, repair_name, repair_term, repair_mile, false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("repair", "repair Select Complete!!!");

        adapter.notifyDataSetChanged();
    }
}
