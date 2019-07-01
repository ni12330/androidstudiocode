package com.example.android_project.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.android_project.MainActivity;
import com.example.android_project.R;
import com.facebook.login.LoginManager;
import com.kakao.auth.Session;

import static com.example.android_project.Login_user.LoginMemberDTO.admin;
import static com.example.android_project.Login_user.LoginMemberDTO.car_image;
import static com.example.android_project.Login_user.LoginMemberDTO.car_mileage;
import static com.example.android_project.Login_user.LoginMemberDTO.car_name;
import static com.example.android_project.Login_user.LoginMemberDTO.car_nickname;
import static com.example.android_project.Login_user.LoginMemberDTO.comp_id;
import static com.example.android_project.Login_user.LoginMemberDTO.phone;
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;
import static com.example.android_project.Login_user.LoginMemberDTO.user_name;
import static com.example.android_project.Login_user.LoginMemberDTO.user_pw;
import static com.example.android_project.MainActivity.kakao;

public class Setting extends Fragment {

    RelativeLayout admin_btn;
    LinearLayout admin_text;

    Button go_Mypage;
    Button btn_logout;

    Button setting_btn_admin;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_setting,container,false);
        /*textView1 = rootView.findViewById(R.id.textView1);*/
        btn_logout=rootView.findViewById(R.id.btn_logout);
        go_Mypage = rootView.findViewById(R.id.go_Mypage);
        admin_btn = rootView.findViewById(R.id.admin_btn);
        admin_text = rootView.findViewById(R.id.admin_text);

        btn_logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setTitle("로그아웃")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //스태틱변수 초기화하여 로그아웃
                                user_id = null;
                                user_pw = null;
                                user_name = null;
                                phone = null;
                                admin = null;
                                comp_id = null;
                                car_name = null;
                                car_nickname = null;
                                car_image = null;
                                car_mileage = null;

                                //카톡세션닫아서 로그아웃
                                Session.getCurrentSession().removeCallback(kakao);
                                Session.getCurrentSession().close();

                                LoginManager.getInstance().logOut();

                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = ab.create();
                alertDialog.show();

            }
        });

        go_Mypage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyPage.class);
                startActivity(intent);
            }
        });



        if(admin.contains("Y")){
            admin_btn.setVisibility(View.VISIBLE);
            admin_text.setVisibility(View.VISIBLE);
        }

        setting_btn_admin = rootView.findViewById(R.id.setting_btn_admin);
        setting_btn_admin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Authority.class);
                startActivity(intent);
            }
        });

        return rootView;


    }

}

































