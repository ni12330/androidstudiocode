package com.example.android_project.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.android_project.Main.MainSelect;
import com.example.android_project.R;
import com.example.android_project.home_alignlist.Home_list;
import com.example.android_project.recode_input.InputRecode;
import com.example.android_project.record.MyRepairMenu;
import com.example.android_project.record.RepairRecord;


import static com.example.android_project.Login_user.LoginMemberDTO.comp_id;
import static com.example.android_project.Login_user.LoginMemberDTO.car_image;
import static com.example.android_project.Login_user.LoginMemberDTO.car_nickname;
import static com.example.android_project.Login_user.LoginMemberDTO.temperature;
import static com.example.android_project.Login_user.LoginMemberDTO.rain;
import static com.example.android_project.Login_user.LoginMemberDTO.sky;




public class Home extends Fragment {

    public static String efficiency;



    ImageView logo;
    LinearLayout myimg_view;
    ImageView today_wether;
    ImageView icon_weather;
    TextView model_weather;
    TextView self_wash;
    TextView today_ment;

    TextView nickname, myAvgMile, carMile;

    String hom_comp_id = comp_id;
    String hom_car_img = "image";
    Button btn_record,btn_chart,btn_ins,btn_list,btn_gas, btn_repair_record;







    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_home,container,false);
        Log.d("car_image",car_image);

        if(car_image!=null) {
            hom_car_img = car_image;
        }
        myimg_view = rootView.findViewById(R.id.img_view);
        logo = rootView.findViewById(R.id.cardImg);
        nickname = rootView.findViewById(R.id.nickname);
        myAvgMile = rootView.findViewById(R.id.myAvgMile);
        carMile = rootView.findViewById(R.id.carMile);


//        btn_repair_record = rootView.findViewById(R.id.btn_repair_record);

        // myimg_view.setBackground(this.getResources().getDrawable(R.drawable.carimg2));

        Log.d("mycar",hom_car_img);
        if (hom_comp_id.equals("kia")) {
            logo.setBackground(this.getResources().getDrawable(R.drawable.car_kia));
        } else if (hom_comp_id.equals("chevrolet")) {
            logo.setBackground(this.getResources().getDrawable(R.drawable.car_chevrolet));
        } else if (hom_comp_id.equals("hyundai")) {
            logo.setBackground(this.getResources().getDrawable(R.drawable.car_hyundai));
        } else if (hom_comp_id.equals("audi")) {
            logo.setBackground(this.getResources().getDrawable(R.drawable.car_audi));
        } else if (hom_comp_id.equals("benz")) {
            logo.setBackground(this.getResources().getDrawable(R.drawable.car_benz));
        } else if (hom_comp_id.equals("bmw")) {
            logo.setBackground(this.getResources().getDrawable(R.drawable.car_bmw));
        }


        myimg_view.setBackground(this.getResources().getDrawable(R.drawable.carimg2));


        if(hom_car_img!=null) {

            ImageView image = new ImageView(getContext());
            Glide
                    .with(this)
                    .load(hom_car_img)
                    .override(myimg_view.getWidth(),myimg_view.getHeight()).into(new ViewTarget<LinearLayout, Drawable>(myimg_view) {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    myimg_view.setBackground(resource);
                }
            });
        }


        //날씨 만들기
        today_wether = rootView.findViewById(R.id.today_wether);
        icon_weather = rootView.findViewById(R.id.icon_weather);
        model_weather = rootView.findViewById(R.id.model_weather);
        self_wash = rootView.findViewById(R.id.self_wash);
        today_ment = rootView.findViewById(R.id.today_ment);



        model_weather.setText(temperature);

        /*- 하늘상태(SKY) 코드 : 맑음(1), 구름조금(2), 구름많음(3), 흐림(4)

                - 강수형태(PTY) 코드 : 없음(0), 비(1), 비/눈(2), 눈(3)
        여기서 비/눈은 비와 눈이 섞여 오는 것을 의미 (진눈개비)*/

        Log.d("메인날씨","aa"+sky+"aa"+rain+"aa"+temperature);

        if(rain.contains("0")){
            if(sky.contains("1")){
                Glide.with(getContext()).asGif().load(R.drawable.sky2).into(today_wether);
                icon_weather.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.model_sky1));
                today_ment.setText("맑음");
                self_wash.setText("더울 수 있지만 세차하기 좋아요.");
            }else if(sky.contains("2")){
                Glide.with(getContext()).asGif().load(R.drawable.sky2).into(today_wether);
                icon_weather.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.model_sky2));
                today_ment.setText("구름조금");
                self_wash.setText("세차하기 좋은 날씨입니다.");
            }else if(sky.contains("3")){
                Glide.with(getContext()).asGif().load(R.drawable.sky3).into(today_wether);
                icon_weather.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.model_sky3));
                today_ment.setText("구름많음");
                self_wash.setText("시원한 바람과 세차 어떠세요?");
            }else{
                Glide.with(getContext()).asGif().load(R.drawable.sky3).into(today_wether);
                icon_weather.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.model_sky4));
                today_ment.setText("흐림");
                self_wash.setText("우중충한 마음을 세차와 함께 깨끗하게");
            }
        }else{
            if(rain.contains("1")){
                Glide.with(getContext()).asGif().load(R.drawable.pty1).into(today_wether);
                icon_weather.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.model_sky5));
                today_ment.setText("비");
                self_wash.setText("오늘은 자연세차의 시간입니다.");
            }else if(rain.contains("2")){
                Glide.with(getContext()).asGif().load(R.drawable.pty1).into(today_wether);
                icon_weather.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.model_pty2));
                today_ment.setText("비/눈");
                self_wash.setText("오늘 세차를 하시려구요?");
            }else if(rain.contains("3")){
                Glide.with(getContext()).asGif().load(R.drawable.pty3).into(today_wether);
                icon_weather.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.model_pty3));
                today_ment.setText("눈");
                self_wash.setText("차를 사랑하지 않으시군요...");
            }
        }



        MainSelect ms = new MainSelect();
        try {
            String result = ms.execute().get();
            efficiency = result.split(",")[0].trim();
            String myCarEff = result.split(",")[1].trim();

            Log.d("home:result", efficiency + " : " + myCarEff);

            nickname.setText(car_nickname);
            myAvgMile.setText(efficiency);
            carMile.setText(myCarEff);


        } catch (Exception e) {
            e.printStackTrace();
        }


        btn_record = rootView.findViewById(R.id.goRecode);
        btn_chart = rootView.findViewById(R.id.goChart);
        btn_ins = rootView.findViewById(R.id.goIns);
        btn_list = rootView.findViewById(R.id.goList);
        btn_gas = rootView.findViewById(R.id.goGas);



        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InputRecode.class);
                startActivity(intent);
            }
        });

        btn_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Home_Chart.class);
                startActivity(intent);
            }
        });

        btn_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Home_ins.class);
                startActivity(intent);
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), Home_list.class);
//                startActivity(intent);

                Intent intent = new Intent(getContext(), MyRepairMenu.class);
                startActivity(intent);
            }
        });
        btn_gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Home_gas.class);
                startActivity(intent);
            }
        });


//        btn_repair_record.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), MyRepairMenu.class);
//                startActivity(intent);
//            }
//        });

        return rootView;
    }
}