package com.example.android_project.carInfo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.example.android_project.Common.CommonMethod;
import com.example.android_project.Controller;
import com.example.android_project.Login_user.LoginMember;
import com.example.android_project.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.android_project.Common.CommonMethod.ipConfig;

public class CarSignUp extends AppCompatActivity {

    Button btn_save;
    EditText car_nickname, car_mailege;
    CarComp carComp;
    CarName carName;
    Spinner spinner1, spinner2;
    ArrayList<String> arrayList, arrayList2;
    ArrayAdapter<String> arrayAdapter, arrayAdapter2;
    String result = "";
    String result2 = "";


    Button takePhoto_btn;
    Button loadPhoto_btn;
    //자동차 사진 인포
    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;
    File file = null;
    long fileSize = 0;
    public String uploadType;
    public String imageFilePathA, imageUploadPathA;
    java.text.SimpleDateFormat tmpDateFormat;

    ImageView mycar_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_sign_up);

        btn_save = findViewById(R.id.btn_save);
        car_nickname = findViewById(R.id.car_nickname);
        car_mailege = findViewById(R.id.car_mileage);

        //자동차 이미지 만들기 중
        takePhoto_btn = findViewById(R.id.takePhoto_btn);
        loadPhoto_btn = findViewById(R.id.loadPhoto_btn);
        mycar_image = findViewById(R.id.mycar_image);
        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();

        //차 회사 가져오는곳
        try {
            carComp = new CarComp();
            result = carComp.execute().get().trim();

            for (int i = 0; i < result.length(); i++) {
                arrayList.add(result.split(",")[i]);
                Log.d("aaa", arrayList.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);
        spinner1 = (Spinner) findViewById(R.id.comp_id);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setSelection(7);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), arrayList.get(i) + "가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
                if (arrayAdapter2 != null) {
                    arrayAdapter2.clear();
                    //차 이름가져오는곳
                    changespinner(i);

                } else {
                    //차 이름가져오는곳
                    changespinner(i);
                }
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = getIntent();
                    String user_id = intent.getStringExtra("user_id");
                    String id = spinner1.getSelectedItem().toString().trim();
                    String name = spinner2.getSelectedItem().toString().trim();
                    String nickname = car_nickname.getText().toString().trim();
                    String milege = car_mailege.getText().toString().trim();

                    CarInsert insert = new CarInsert(user_id, id, name, nickname, milege,uploadType,imageFilePathA, imageUploadPathA);
                    insert.execute().get().trim();
                    Toast.makeText(CarSignUp.this, "차정보등록완료", Toast.LENGTH_SHORT).show();
                    finish();


                    LoginMember loginMember = new LoginMember(user_id);
                    loginMember.execute();

                    intent = new Intent(getApplicationContext(), Controller.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        takePhoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    file = createFile();
                    Log.d("FilePath ", file.getAbsolutePath());

                } catch (Exception e) {
                    Log.d("BoardNew", "Something Wrong", e);
                }

                mycar_image.setVisibility(View.VISIBLE);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(), "com.test.android.test.fileprovider", file));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST);
                }

            }
        });

        loadPhoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mycar_image.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

    }


    public void changespinner(int i) {
        try {
            String comp_id = arrayList.get(i);
            carName = new CarName(comp_id);
            result2 = carName.execute().get().trim();

            for (int j = 0; j < result2.length(); j++) {
                arrayList2.add(result2.split(",")[j]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList2);
        spinner2 = (Spinner) findViewById(R.id.car_name);
        spinner2.setAdapter(arrayAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), arrayList2.get(i) + "가 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    private File createFile() throws IOException {

        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            uploadType = "image";


            try {
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if (newBitmap != null) {
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




}
