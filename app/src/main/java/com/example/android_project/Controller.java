package com.example.android_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android_project.Common.PopupActivity;
import com.example.android_project.Garage.Car_garage;
import com.example.android_project.board.Board;
import com.example.android_project.carInfo.MycarstarInsert;
import com.example.android_project.home.Home;
import com.example.android_project.notify.Notify;
import com.example.android_project.setting.Setting;

import static com.example.android_project.Login_user.LoginMemberDTO.mycar_star;
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;

public class Controller extends AppCompatActivity {

    Home home;
    Car_garage garage;
    Board board;
    Notify notify;
    Setting setting;
    String result_star ="0";

    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);



        home = new Home();
        garage = new Car_garage();
        board = new Board();
        notify = new Notify();
        setting = new Setting();


        getSupportFragmentManager().beginTransaction().replace(R.id.container,home).commit();

        // 버튼 클릭시 사용되는 리스너를 구현합니다.
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView_main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
                        switch (item.getItemId()) {

                            case R.id.menuitem_1:
                                fragment = home;
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,home).commit();

                                return true;


                            case R.id.menuitem_2:
                                fragment = garage;

                                if(mycar_star.contains("0")){

                                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                                    startActivityForResult(intent, 1);
                                    break;

                                }else {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container,garage).commit();
                                    return true;
                                }



                            case R.id.menuitem_3:
                                fragment = board;
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,board).commit();
                                return true;

                            case R.id.menuitem_4:
                                fragment = notify;
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,notify).commit();
                                return true;

                            case R.id.menuitem_5:
                                fragment = setting;
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,setting).commit();
                                return true;
                        }
                        return false;
                    }
                });
    }

//팝업창 star 값 돌려받기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
               result_star = data.getStringExtra("result");
               mycar_star = result_star;
               MycarstarInsert star = new MycarstarInsert(user_id,result_star);
               star.execute();

               getSupportFragmentManager().beginTransaction().replace(R.id.container,garage).commit();
            }
        }
    }

}
