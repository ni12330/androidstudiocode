package com.example.android_project.setting;


import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.example.android_project.Common.CommonMethod.ipConfig;
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;
import static com.example.android_project.Login_user.LoginMemberDTO.user_pw;

public class MyPageUpdate extends AsyncTask<Void, Void, String> {
    String edit_car_nickname,edit_car_name,edit_comp_id,edit_car_mileage,edit_phone,edit_user_pw;
    String uploadType,imageFilePathA, imageUploadPathA;
    String result = "";



    public MyPageUpdate(String edit_car_nickname, String edit_car_name, String edit_comp_id,String edit_phone, String edit_user_pw, String car_mileage,String uploadType,String imageFilePathA, String imageUploadPathA) {
        this.edit_car_nickname = edit_car_nickname;
        this.edit_car_name = edit_car_name;
        this.edit_comp_id = edit_comp_id;
        this.edit_car_mileage = car_mileage;
        this.edit_phone = edit_phone;
        this.edit_user_pw = edit_user_pw;

        this.uploadType = uploadType;
        this.imageFilePathA = imageFilePathA;
        this.imageUploadPathA = imageUploadPathA;
    }


    @Override
    protected String doInBackground(Void... voids) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));


            builder.addTextBody("user_id", user_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("car_nickname", edit_car_nickname, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("car_name", edit_car_name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("comp_id", edit_comp_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("car_mileage", edit_car_mileage, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("phone", edit_phone, ContentType.create("Multipart/related", "UTF-8"));

            if (edit_user_pw.equals("")) {
                builder.addTextBody("user_pw", user_pw, ContentType.create("Multipart/related", "UTF-8"));
            } else {
                builder.addTextBody("user_pw", edit_user_pw, ContentType.create("Multipart/related", "UTF-8"));
            }


                if (uploadType.equals("image")) {
                    //사진 저장한다고 표현
                    builder.addTextBody("uploadType", uploadType, ContentType.create("Multipart/related", "UTF-8"));

                    //DB에 저장될 이미지 경로
                    builder.addTextBody("filepath", imageUploadPathA, ContentType.create("Multipart/related", "UTF-8"));

                    //이미지 데이터 이클립스 전송하기
                    builder.addPart("image", new FileBody(new File(imageFilePathA)));
                } else if (uploadType.equals("image_delete")) {
                    //사진 삭제 한다
                    builder.addTextBody("uploadType", uploadType, ContentType.create("Multipart/related", "UTF-8"));
                }else{
                    builder.addTextBody("uploadType", uploadType, ContentType.create("Multipart/related", "UTF-8"));
                }



            String postURL = ipConfig + "/index/myinfo";

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
