package com.example.android_project.Login_user;

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

import static com.example.android_project.Common.CommonMethod.ipConfig;

public class LoginMember extends AsyncTask<Void, Void, String> {

    String user_id;
    String result = "";

    public LoginMember(String id) {
        this.user_id = id;
    }


    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            Uri.Builder builder1 = new Uri.Builder();
            builder1.appendQueryParameter("user_id", user_id);

            String postURL = ipConfig + "/index/loginMember"+builder1;

            Log.d("sub", "postURL : " + ipConfig + "/index/loginMember"+builder1);

            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream inputStream = null;
            inputStream = httpEntity.getContent();


            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            if(reader != null){
                JsonParser(inputStream);
                result = "success";
            }else {
                result = "fail";
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("sub", "LogninCheck Fail");
        }


        return result;
    }

    private LoginMemberDTO JsonParser(InputStream inputStream) throws IOException {

        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        String user_id = "", user_pw = "", user_name = "", phone = "", admin = "";
        String comp_id = "", car_name = "", car_nickname = "", car_image = "", car_mileage = "",mycar_star="";

        reader.beginObject();
        while (reader.hasNext()) {

            //데이터로 변환
            String readStr = reader.nextName();
            if (readStr.equals("user_id")) {
                user_id = reader.nextString();
            } else if (readStr.equals("user_pw")) {
                user_pw = reader.nextString();
            } else if (readStr.equals("user_name")) {
                user_name = reader.nextString();
            } else if (readStr.equals("phone")) {
                phone = reader.nextString();
            } else if (readStr.equals("admin")) {
                admin = reader.nextString();
            }else if (readStr.equals("comp_id")) {
                comp_id = reader.nextString();
            }else if (readStr.equals("car_name")) {
                car_name = reader.nextString();
            } else if (readStr.equals("car_nickname")) {
                car_nickname = reader.nextString();
            } else if (readStr.equals("car_mimage")) {
                car_image = reader.nextString();
            } else if (readStr.equals("car_mileage")) {
                car_mileage = reader.nextString();
            }else if (readStr.equals("mycar_star")) {
                mycar_star = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("listselect:myitem", user_id + "," + user_pw + "," + user_name + "," + phone
                + "," + admin + "," + comp_id);

        return new LoginMemberDTO(user_id,user_pw,user_name,phone,admin,comp_id,car_name,car_nickname,car_image,car_mileage,mycar_star);
    }


}