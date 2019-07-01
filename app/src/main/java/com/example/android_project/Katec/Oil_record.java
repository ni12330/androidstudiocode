package com.example.android_project.Katec;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

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

public class Oil_record extends AsyncTask <ArrayList<Oil_DTO>, Void, ArrayList<Oil_DTO>> {


    String result = "";


    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    String x, y;
    ArrayList<Oil_DTO> oillist;

    String oil_kind;
    String prodcd;

    public Oil_record(ArrayList<Oil_DTO> oillist, String x_tude, String y_tude, String oil_kind) {
        this.oillist = oillist;
        this.x = x_tude;
        this.y = y_tude;
        this.oil_kind = oil_kind;
    }


    @Override
    protected ArrayList<Oil_DTO> doInBackground(ArrayList<Oil_DTO>... voids) {
        oillist.clear();

        try {
            InputStream inputStream = null;
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            Uri.Builder builder1 = new Uri.Builder();
            if(oil_kind.contains("휘발유")){
                prodcd = "B027";
                builder1.appendQueryParameter("prodcd", prodcd);
            }else{
                prodcd = "D047";
                builder1.appendQueryParameter("prodcd", prodcd);
            }
            builder1.appendQueryParameter("x", x);
            builder1.appendQueryParameter("y", y);

            String postURL = ipConfig + "/index/katec" + builder1;

            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();


            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);
            //Log.d("asdf","katec왓다감");


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("sub", "garageList Fail");
        }

        return oillist;
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                oillist.add(readMessage(reader));
            }
            Log.d("listsize:", oillist.size() + "");
            reader.endArray();
        } catch (Exception e){
            e.getMessage();
        } finally {
            reader.close();
        }
    }
    public Oil_DTO readMessage(JsonReader reader) throws IOException {

        String com_code = "";
        String oil_name = "",oil_price = "",distance = "",gis_x = "",gis_y = "",oil_comp = "";



        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.contains("UNI_ID")) {
                com_code = reader.nextString();
            } else if (readStr.contains("POLL_DIV_CD")) {
                oil_comp = reader.nextString();
            } else if (readStr.contains("OS_NM")) {
                oil_name = reader.nextString();
            } else if (readStr.contains("PRICE")) {
                oil_price = reader.nextString();
            } else if (readStr.contains("DISTANCE")) {
                distance = reader.nextString();
            } else if (readStr.contains("GIS_X_COOR")) {
                gis_x = reader.nextString();
            } else if (readStr.contains("GIS_Y_COOR")) {
                gis_y = reader.nextString();
            } else  {
                reader.skipValue();
            }
        }
        reader.endObject();
        // Log.d("listselect:myitem", board_id + "," + board_title + "," + board_content );
        return new Oil_DTO(oil_name,oil_price,distance,gis_x,gis_y,oil_comp,com_code);
    }
}
