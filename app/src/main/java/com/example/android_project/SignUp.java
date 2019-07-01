package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.Member.MemberIdCheck;
import com.example.android_project.Member.MemberSignUp;
import com.example.android_project.carInfo.CarSignUp;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;



public class SignUp extends AppCompatActivity {

    Button btn_goSignin;
    Button btn_signUp;

    TextView user_id, user_pw, user_name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn_goSignin = findViewById(R.id.btn_goSignin);
        btn_signUp = findViewById(R.id.btn_signUp);

        Intent intent = getIntent();

        btn_goSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnintent = new Intent();
                finish();
                overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
            }
        });

        user_id = findViewById(R.id.user_id);
        user_pw = findViewById(R.id.user_pw);
        user_name = findViewById(R.id.user_name);
        phone = findViewById(R.id. phone);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }

    private void check() {
        String id_req = "^\\S[a-zA-Z0-9]*$"; //아이디는 공백X, 대소문자 숫자만 입력

        String pw_req = "^\\S[a-zA-Z0-9]*$"; //공백X, 대소문자, 숫자

        String phone_req = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}";

        String id = user_id.getText().toString();
        String pw = user_pw.getText().toString();
        String name = user_name.getText().toString();
        String tel = phone.getText().toString();


        if(id.equals("")) {
            Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
            /*user_id.setFocusableInTouchMode(true);*/
            user_id.requestFocus();
            return;
        }else if(id.length() > 10) {
            Toast.makeText(this, "아이디를 10자 이하로 입력하세요", Toast.LENGTH_SHORT).show();
            user_id.requestFocus();
            return;
        }else if(id.length() < 5) {
            Toast.makeText(this, "아이디를 5자 이상 입력하세요", Toast.LENGTH_SHORT).show();
            user_id.requestFocus();
            return;
        }else if(!Pattern.matches(id_req, id)){
            Toast.makeText(this, "영문 대소문자 숫자만 입력하세요.", Toast.LENGTH_SHORT).show();
            user_id.requestFocus();
            return;
        }

        if(pw.equals("")) {
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            user_pw.requestFocus();
            return;
        }else if(!Pattern.matches(pw_req, pw)) {
            Toast.makeText(this, "영문자와 숫자만 입력하세요", Toast.LENGTH_SHORT).show();
            user_pw.requestFocus();
            return;
        }else if(pw.length() < 6) {
            Toast.makeText(this, "비밀번호를 6자 이상 입력하세요", Toast.LENGTH_SHORT).show();
            user_pw.requestFocus();
            return;
        }else if(pw.length() > 15) {
            Toast.makeText(this, "비밀번호를 15자 이하로 입력하세요", Toast.LENGTH_SHORT).show();
            user_pw.requestFocus();
            return;
        }

        if(name.equals("")){
            Toast.makeText(this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
            user_name.requestFocus();
            return;
        }

        if(tel.length() != 13){
            Toast.makeText(this, "전화번호 자리수를 정확히 입력하세요", Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            return;
        }else if(!Pattern.matches(phone_req, tel)){
            Toast.makeText(this, "전화번호를 정확히 입력하세요", Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            return;
        }

        String result = "fail";

        MemberIdCheck memberIdCheck = new MemberIdCheck(id);
        try {
            result = memberIdCheck.execute().get().trim();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(result.equals("success")){

            Intent intent = new Intent(getApplicationContext(), CarSignUp.class);
            intent.putExtra("user_id",id);
            startActivity(intent);

            MemberSignUp memberInsert = new MemberSignUp(id, pw, name, tel);
            memberInsert.execute();
            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);
        }else {
            Toast.makeText(this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
