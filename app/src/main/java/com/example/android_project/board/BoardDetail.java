package com.example.android_project.board;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.android_project.R;
import com.example.android_project.reply.Reply;
import com.nostra13.universalimageloader.core.ImageLoader;

import static com.example.android_project.Login_user.LoginMemberDTO.admin;
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;

public class BoardDetail extends AppCompatActivity {

    TextView board_title,board_content,board_UserName,board_makedate;
    Button btn_update,btn_delete;
    String title,content,result;

    String buser_id="",comp_id="",board_date="",mycar="",filepath="",filename="";
    int board_id,readcnt;
    Date makedate;

    ImageView image;



    LottieAnimationView lott_yes;
    LottieAnimationView lott_no;
    Button sympathy_btn;
    Button btn_reply;
    Button btn_finish;
    String sympathy_result = "";
    String board_user_nick = "";

    java.text.SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        board_makedate = findViewById(R.id.board_makedate);
        df = new java.text.SimpleDateFormat("yyyy년MM월dd월");
        board_UserName = findViewById(R.id.board_UserName);
        board_title = findViewById(R.id.board_title);
        board_content = findViewById(R.id.board_content);
        image = findViewById(R.id.image);

        btn_finish = findViewById(R.id.btn_cancel2);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);


        btn_reply=findViewById(R.id.btn_reply);
        btn_reply = findViewById(R.id.btn_reply);
        btn_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reply.class);

                intent.putExtra("board_id", board_id);
                startActivity(intent);
            }
        });








        Intent intent = getIntent();
        title=intent.getStringExtra("board_title");
        content=intent.getStringExtra("board_content");
        board_id= intent.getIntExtra("board_id",0);


        buser_id = intent.getStringExtra("user_id");
        comp_id = intent.getStringExtra("comp_id");
        board_date = intent.getStringExtra("board_date");
        mycar = intent.getStringExtra("mycar");
        filepath = intent.getStringExtra("filepath");
        filename = intent.getStringExtra("filename");
        readcnt = intent.getIntExtra("readcnt",readcnt);
        filename = intent.getStringExtra("filename");
        board_user_nick = intent.getStringExtra("board_user_nick");

                //ImageLoader.getInstance().displayImage(filepath, image, (ImageLoadingListener) imageLoader);


        //구글링
        Glide
                .with(this)
                .load(filepath)
                .override(200,200)
                .into(image);






        BoardCnt cnt = new BoardCnt(board_id,readcnt);
        cnt.execute();
        board_title.setText(title);
        board_content.setText(content);
        board_UserName.setText(board_user_nick);
        board_makedate.setText(board_date);





        //게시글 수정
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buser_id.equals(user_id)) {
                    Intent update_intent = new Intent(getApplicationContext(), BoardNew.class);
                    update_intent.putExtra("title", title);
                    update_intent.putExtra("content", content);
                    update_intent.putExtra("board_id", board_id);

                    update_intent.putExtra("filepath", filepath);
                    update_intent.putExtra("filename", filename);

                    finish();

                    startActivity(update_intent);
                    Toast.makeText(BoardDetail.this, "수정하러간다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BoardDetail.this, "수정 불가능 합니다", Toast.LENGTH_SHORT).show();
                }
            }
        });


        board_title.setEnabled(false);
        board_content.setEnabled(false);

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(buser_id.equals(user_id)||admin.contains("Y")){
            btn_delete.setVisibility(View.VISIBLE);
        }
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BoardDelete delete = new BoardDelete(board_id);
                try {
                    result = delete.execute().get().trim();

                    if(result.equals("success")){
                        Toast.makeText(BoardDetail.this, "삭제완료", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(BoardDetail.this, "삭제실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });




        //공감 하기 버튼 만들기
        sympathy_btn = findViewById(R.id.sympathy);
        lott_yes = findViewById(R.id.sympathy_yes);
        lott_no = findViewById(R.id.sympathy_no);
        sympathy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardSympathy bs = new BoardSympathy(board_id,user_id);
                try {
                    sympathy_result = bs.execute().get().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(sympathy_result.contains("insert")){
                    lott_yes.setVisibility(View.VISIBLE);
                    lott_yes.loop(true);
                    lott_no.loop(true);

                    //딜레이 효과
                    Handler deleyHandler = new Handler();
                    deleyHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lott_yes.setVisibility(View.GONE);

                        }
                    },3700);
                }else{
                    lott_no.setVisibility(View.VISIBLE);
                    lott_no.loop(true);
                    lott_yes.loop(true);

                    //딜레이 효과
                    Handler deleyHandler = new Handler();
                    deleyHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lott_no.setVisibility(View.GONE);

                        }
                    },2500);
                }
            }
        });

    }
}