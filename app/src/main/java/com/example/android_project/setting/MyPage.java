package com.example.android_project.setting;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android_project.Common.CommonMethod;
import com.example.android_project.R;
import com.example.android_project.carInfo.CarComp;
import com.example.android_project.carInfo.CarName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.android_project.Common.CommonMethod.ipConfig;
import static com.example.android_project.Login_user.LoginMemberDTO.car_image;
import static com.example.android_project.Login_user.LoginMemberDTO.car_name;
import static com.example.android_project.Login_user.LoginMemberDTO.car_nickname;
import static com.example.android_project.Login_user.LoginMemberDTO.comp_id;
import static com.example.android_project.Login_user.LoginMemberDTO.phone;
import static com.example.android_project.Login_user.LoginMemberDTO.car_mileage;


public class MyPage extends AppCompatActivity {

    EditText edit_car_nickname,edit_phone,edit_user_pw,edit_car_mileage;
    ImageView mycar_image;
    Button mypage_btn_update,mypage_btn_cancle;
    Spinner spinner1,spinner2;
    CarComp carComp;
    CarName carName;
    ArrayList<String> arrayList,arrayList2;
    ArrayAdapter<String> arrayAdapter,arrayAdapter2;
    String result,result2;





    Button takePhoto_btn;
    Button loadPhoto_btn;
    Button imagecencel_btn;
    //자동차 사진 인포
    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;
    public File file = null;
    long fileSize = 0;
    public String uploadType="non";
    public String imageFilePathA, imageUploadPathA;
    java.text.SimpleDateFormat tmpDateFormat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        edit_car_nickname = findViewById(R.id.car_nickname);
        edit_phone = findViewById(R.id.phone);
        edit_user_pw = findViewById(R.id.user_pw);
        mycar_image = findViewById(R.id.mycar_image);
        edit_car_mileage = findViewById(R.id.edit_car_mileage);



        takePhoto_btn = findViewById(R.id.takePhoto_btn);
        loadPhoto_btn = findViewById(R.id.loadPhoto_btn);
        mypage_btn_update = findViewById(R.id.mypage_btn_update);
        mypage_btn_cancle = findViewById(R.id.mypage_btn_cancle);
        imagecencel_btn = findViewById(R.id.imagecencel_btn);



        if(car_image!=""){
            Glide
                    .with(this)
                    .load(car_image)
                    .override(200,200)
                    .into(mycar_image);
        }





        edit_car_nickname.setText(car_nickname);


        edit_phone.setText(phone);
        edit_user_pw.setText("");


        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();

        try {
            carComp = new CarComp();
            result = carComp.execute().get().trim();

            for(int i = 0; i<result.length(); i++){
                arrayList.add(result.split(",")[i]);
                Log.d("aaa",arrayList.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);
        spinner1 = (Spinner)findViewById(R.id.comp_id);
        spinner1.setAdapter(arrayAdapter);

        for (int i = 0; i<arrayList.size(); i++){
            spinner1.setSelection(i);
            if(spinner1.getSelectedItem().toString().trim().contains(comp_id)){
                spinner1.setSelection(i);
                break;
            }
        }
        //회사의 자동차 가져오기
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),arrayList.get(i).trim()+"가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
                if(arrayAdapter2!=null){
                    arrayAdapter2.clear();
                    //차 이름가져오는곳
                    changespinner(i);

                }else{
                    //차 이름가져오는곳
                    changespinner(i);
                }
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //업데이트 버튼클릭시
        mypage_btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = edit_car_nickname.getText().toString().trim();
                String comp = spinner1.getSelectedItem().toString().trim();
                String name = spinner2.getSelectedItem().toString().trim();
                String phone = edit_phone.getText().toString().trim();
                String pw = edit_user_pw.getText().toString().trim();

                String new_car_mileage = edit_car_mileage.getText().toString().trim();
                Log.d("new_car_mileage",new_car_mileage);
                if(new_car_mileage.equals("")){
                    car_mileage = "old_car";
                }else{
                    car_mileage = new_car_mileage;
                }
                try {
                    MyPageUpdate update = new MyPageUpdate(nickname, name, comp, phone, pw,car_mileage,uploadType,imageFilePathA, imageUploadPathA);

                    result = update.execute().get().trim();
                    if(result.contains("success")) {
                        finish();
                    }else{
                        Toast.makeText(MyPage.this, "정보수정실패", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        //변경에서 뒤로가기
        mypage_btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //사직찍기 버튼
        takePhoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                   file = createFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("FilePath ", file.getAbsolutePath());


                mycar_image.setVisibility(View.VISIBLE);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(), "com.test.android.test.fileprovider", file));

                    startActivityForResult(intent, CAMERA_REQUEST);

            }
        });


        //갤러리 사진 불러오기
        loadPhoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

        //이미지 삭제 하기
        imagecencel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadType="image_delete";
                imageFilePathA="";
                imageUploadPathA="";
                /*icon_weather.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.model_pty1));*/
                mycar_image.setImageResource(0);        //이미지 지움
                mycar_image.setVisibility(View.GONE);   //이미지 상자 숨기기

                mycar_image.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.imageview_default));
            }
        });

    }//onCreate
    public void changespinner(int i){
        try {
            String comp_id = arrayList.get(i);
            carName = new CarName(comp_id);
            result2 = carName.execute().get().trim();

            for(int j = 0; j<result2.length(); j++){
                arrayList2.add(result2.split(",")[j]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList2);
        spinner2 = (Spinner)findViewById(R.id.car_name);
        spinner2.setAdapter(arrayAdapter2);

        for (int j = 0; j<arrayList2.size(); j++){
            spinner2.setSelection(j);
            if(spinner2.getSelectedItem().toString().trim().contains(car_name)){
                spinner2.setSelection(j);
                break;
            }
        }

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),arrayList2.get(i).trim()+"가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();

                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            uploadType = "image";


            try {
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if (newBitmap != null) {
                    mycar_image.setImageResource(0);        //이미지 지움
                    mycar_image.setImageBitmap(newBitmap);

                } else {
                    Toast.makeText(this, "이미지를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }

                imageFilePathA = file.getAbsolutePath();
                String uploadFileName = imageFilePathA.split("/")[imageFilePathA.split("/").length - 1];
                imageUploadPathA = ipConfig + "/index/resources/images/mycar_info/" + uploadFileName;
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
            uploadType = "image";
            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                }
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if (newBitmap != null) {
                    mycar_image.setImageResource(0);        //이미지 지움
                    mycar_image.setImageBitmap(newBitmap);

                } else {
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }
                imageFilePathA = path;
                Log.d("BoardNew", "imageFilePathA Path : " + imageFilePathA);
                String uploadFileName = imageFilePathA.split("/")[imageFilePathA.split("/").length - 1];
                imageUploadPathA = ipConfig + "/index/resources/images/mycar_info/" + uploadFileName;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("BoardNew => ", "imagepath is null, whatever something is wrong!!");
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }



    //자동차 이미지 바꾸기
    private File createFile() throws IOException {
        String filename = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storage = Environment.getExternalStorageDirectory();
        File curFile = new File(storage, filename);
        return curFile;
    }

}
