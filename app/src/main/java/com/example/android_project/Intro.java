package com.example.android_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.kakao.auth.Session;

import static com.example.android_project.MainActivity.kakao;
import static com.example.android_project.Login_user.LoginMemberDTO.weather;
public class Intro extends Activity {



    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {

            //카톡과 페이스북 로그아웃시키기 위함
            Session.getCurrentSession().removeCallback(kakao);
            Session.getCurrentSession().close();
            LoginManager.getInstance().logOut();




// 4초뒤에 다음화면(MainActivity)으로 넘어가기 Handler 사용
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent); // 다음화면으로 넘어가기
         overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity);
            finish(); // Activity 화면 제거
            overridePendingTransition(R.anim.not_move_activity, R.anim.rightout_activity);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro); // xml과 java소스를 연결
        weather = "false";
    } // end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
// 다시 화면에 들어어왔을 때 예약 걸어주기
        handler.postDelayed(r, 4000); // 4초 뒤에 Runnable 객체 수행
    }

    @Override
    protected void onPause() {
        super.onPause();
// 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(r); // 예약 취소
    }



}


