package com.example.android_project.record;

import android.app.ProgressDialog;
import android.net.Uri;
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
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;

public class MyOilList extends AsyncTask<Void, Void, Void> {
    ArrayList<RecordDTO> arrayList;
    MyOilAdapter adapter;
    ProgressDialog progressDialog;
    String postURL = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    public MyOilList(ArrayList<RecordDTO> arrayList, MyOilAdapter adapter) {
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

            Uri.Builder builder1 = new Uri.Builder();
            builder1.appendQueryParameter("user_id", user_id);

            String postURL = ipConfig + "/index/myOilList" + builder1;

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


    public RecordDTO readMessage(JsonReader reader) throws IOException {

        String station = "", oil_cost = "", car_mileage = "", record_date = "", oil = "",record_id="";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("station")) {
                station = reader.nextString();
            } else if (readStr.equals("oil_cost")) {
                oil_cost = reader.nextString();
            } else if (readStr.equals("car_mileage")) {
                car_mileage = reader.nextString();
            } else if (readStr.equals("record_date")) {
                record_date = reader.nextString();
            } else if (readStr.equals("oil")) {
                oil = reader.nextString();
            } else if (readStr.equals("record_id")) {
                record_id = reader.nextString();
            } else  {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new RecordDTO(station, oil_cost, car_mileage, record_date, oil,record_id);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("record", "record Select Complete!!!");

        adapter.notifyDataSetChanged();
    }
}
