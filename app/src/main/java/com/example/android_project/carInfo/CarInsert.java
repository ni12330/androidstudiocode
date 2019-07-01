package com.example.android_project.carInfo;

import android.net.Uri;
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

public class CarInsert extends AsyncTask<Void, Void, String> {

    private String user_id,comp_id, car_name, car_nickname, car_mileage;
    private String uploadType,imageFilePathA, imageUploadPathA;
    private String result="";

    public CarInsert(String user_id, String id, String name, String nickname,String car_mileage,String uploadType,String imageFilePathA,String imageUploadPathA) {
        this.user_id = user_id;
        this.comp_id = id;
        this.car_name = name;
        this.car_nickname = nickname;
        this.car_mileage = car_mileage;
        this.uploadType = uploadType;
        this.imageFilePathA = imageFilePathA;
        this.imageUploadPathA = imageUploadPathA;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));



            builder.addTextBody("user_id", user_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("comp_id", comp_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("car_name", car_name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("car_nickname", car_nickname, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("car_mileage", car_mileage, ContentType.create("Multipart/related", "UTF-8"));

            if(uploadType !=null) {
                //사진 저장한다고 표현
                builder.addTextBody("uploadType", uploadType, ContentType.create("Multipart/related", "UTF-8"));


                //DB에 저장될 이미지 경로
                builder.addTextBody("filepath", imageUploadPathA, ContentType.create("Multipart/related", "UTF-8"));

                //이미지 데이터 이클립스 전송하기
                builder.addPart("image", new FileBody(new File(imageFilePathA)));
            }













            /*
            Uri.Builder builder1 = new Uri.Builder();
            builder1.appendQueryParameter("user_id",user_id);
            builder1.appendQueryParameter("comp_id",comp_id);
            builder1.appendQueryParameter("car_name",car_name);
            builder1.appendQueryParameter("car_nickname",car_nickname);
            builder1.appendQueryParameter("car_image",car_image);
            builder1.appendQueryParameter("car_mileage",car_mileage);*/

            String postURL = ipConfig + "/index/info_insert";

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
