package com.example.android_project.notify;

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
import java.util.ArrayList;

import static com.example.android_project.Common.CommonMethod.ipConfig;

public class NotifyListSelect extends AsyncTask<Void, Void, Void> {

    ArrayList<NotifyItem> myItem2ArrayList;
    NotifyAdapter adapter2;
    ProgressDialog progressDialog;
    String postURL="";

    public NotifyListSelect(ArrayList<NotifyItem> myItem2ArrayList, NotifyAdapter adapter2, ProgressDialog progressDialog, String url) {
        this.myItem2ArrayList = myItem2ArrayList;
        this.adapter2 = adapter2;
        this.progressDialog = progressDialog;
        postURL = ipConfig + url;

    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        myItem2ArrayList.clear();

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse=httpClient.execute(httpPost);
            httpEntity=httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);



        }catch(Exception e){

            Log.d("Notify", e.getMessage());
            e.printStackTrace();

        }finally{
            if(httpEntity != null){
                httpEntity=null;
            }
            if(httpResponse != null){
                httpResponse=null;
            }
            if(httpPost != null){
                httpPost=null;
            }
            if(httpClient!=null){
                httpClient=null;
            }
        }

        return null;
    }


    public void readJsonStream(InputStream inputStream) throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()){
                myItem2ArrayList.add(readMessage(reader));
            }
            reader.endArray();
        }finally {
            reader.close();
        }
    }

    public NotifyItem readMessage(JsonReader reader) throws IOException{
        String notify_content = null,notify_title=null,notify_date=null;

        reader.beginObject();
        while (reader.hasNext()){
            String readStr = reader.nextName();
            if(readStr.equals("notify_title")){
                notify_title=reader.nextString();
            }else if(readStr.equals("notify_content")){
                notify_content=reader.nextString();
            }else if(readStr.equals("notify_date")){
                notify_date=reader.nextString();
            }
            else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new NotifyItem(notify_title,notify_content,notify_date);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("Notify", "List Select Complete!!!");
        adapter2.notifyDataSetChanged();
    }

}































