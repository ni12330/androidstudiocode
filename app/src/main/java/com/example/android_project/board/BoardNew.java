package com.example.android_project.board;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android_project.Common.CommonMethod;
import com.example.android_project.MainActivity;
import com.example.android_project.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.android_project.Common.CommonMethod.ipConfig;
import static com.example.android_project.Common.CommonMethod.isNetworkConnected;


public class BoardNew extends AppCompatActivity {

    Button btn_insert;
    Button takePhoto_btn;
    Button loadPhoto_btn;
    Button btn_cancel;

    EditText editText;
    EditText editText2;
    String result;

    ImageView boardImageView;

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;

    File file = null;
    long fileSize = 0;

    public String uploadType;
    public String imageFilePathA, imageUploadPathA;

    //업데이트시 필요
    java.text.SimpleDateFormat tmpDateFormat;
    String insertTyp = "insert";
    int board_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_insert);
        boardImageView=findViewById(R.id.boardImageView);
        takePhoto_btn=findViewById(R.id.takePhoto_btn);
        loadPhoto_btn=findViewById(R.id.loadPhoto_btn);
        btn_insert = findViewById(R.id.btn_insert);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        //내용불러오기
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);


        Intent intent = getIntent();
        String update_title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        board_id = intent.getIntExtra("board_id",0);
        String filepath = intent.getStringExtra("filepath");
        String filename = intent.getStringExtra("filename");

        if(update_title!=null){
            editText.setText(update_title);
            editText2.setText(content);
            imageFilePathA = filepath;
            insertTyp = "update";
        }
        if(filepath!=null){
            Glide
                    .with(this)
                    .load(filepath)
                    .override(200,200)
                    .into(boardImageView);
        }








        boardImageView.setVisibility(View.VISIBLE);


        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileSize <= 30000000) {
                    String board_title = editText.getText().toString();
                    String board_content = editText2.getText().toString();

                    if(insertTyp=="insert") {
                        BoardInsert insert = new BoardInsert(board_title, board_content, uploadType, imageFilePathA, imageUploadPathA);
                        try {
                            result = insert.execute().get().trim();

                            if (result.equals("success")) {
                                Toast.makeText(BoardNew.this, "등록완료", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(BoardNew.this, "등록실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        BoardUpdate update = new BoardUpdate(board_title, board_content,board_id, uploadType, imageFilePathA, imageUploadPathA);

                        try {
                            result = update.execute().get().trim();
                            if (result.contains("success")) {
                                Toast.makeText(BoardNew.this, "등록완료", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(BoardNew.this, "등록실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }




                      /*  Intent intent = new Intent(getApplicationContext(), Board.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        finish();*/
                } else {
                    // 알림창 띄움
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("알림");
                    builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택하세요.");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }

            }
        });

        takePhoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{
                    file = createFile();
                    Log.d("FilePath ", file.getAbsolutePath());

                }catch(Exception e){
                    Log.d("BoardNew", "Something Wrong", e);
                }

                boardImageView.setVisibility(View.GONE);

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
                boardImageView.setVisibility(View.GONE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
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

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            uploadType = "image";


            try {
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if (newBitmap != null) {
                    boardImageView.setImageBitmap(newBitmap);
                } else {
                    Toast.makeText(this, "이미지를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }

                imageFilePathA = file.getAbsolutePath();
                String uploadFileName = imageFilePathA.split("/")[imageFilePathA.split("/").length - 1];
                imageUploadPathA = ipConfig + "/index/resources/images/upload/" + uploadFileName;
            } catch (Exception e) {
                e.printStackTrace();

            }
        }else if(requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {
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
                if(newBitmap != null){
                    boardImageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }
                imageFilePathA = path;
                Log.d("BoardNew", "imageFilePathA Path : " + imageFilePathA);
                String uploadFileName = imageFilePathA.split("/")[imageFilePathA.split("/").length - 1];
                imageUploadPathA = ipConfig + "/index/resources/images/upload/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }else {
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

    public void btnCancelClicked(View view){
        finish();
    }
}
