package com.example.android_project.board;


import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

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
import static com.example.android_project.Login_user.LoginMemberDTO.car_name;
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;


public class BoardInsert extends AsyncTask<Void, Void, String>  {

    private String board_title;
    private String board_content;

    private String uploadType, imageFilePathA, imageUploadPathA;
    private String result="";

    public BoardInsert(String board_title, String board_content,String uploadType,String imageFilePathA,String imageUploadPathA) {

        this.board_title = board_title;
        this.board_content = board_content;

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

            // 문자열 및 데이터 추가

            builder.addTextBody("board_title", board_title, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("board_content", board_content, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("car_name", car_name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_id", user_id, ContentType.create("Multipart/related", "UTF-8"));
            if(uploadType !=null) {
                //사진 저장한다고 표현
                builder.addTextBody("uploadType", uploadType, ContentType.create("Multipart/related", "UTF-8"));


                //DB에 저장될 이미지 경로
                builder.addTextBody("filepath", imageUploadPathA, ContentType.create("Multipart/related", "UTF-8"));

                //이미지 데이터 이클립스 전송하기
                builder.addPart("image", new FileBody(new File(imageFilePathA)));
            }


          /*  Uri.Builder builder1 = new Uri.Builder();
            builder1.appendQueryParameter("board_title",board_title);
            builder1.appendQueryParameter("board_content",board_content);*/

            String postURL = ipConfig + "/index/boardInsert";

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
            Log.d("사진은 등록 된것 같아요",result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}




