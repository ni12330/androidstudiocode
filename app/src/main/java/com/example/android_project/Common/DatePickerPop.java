package com.example.android_project.Common;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.R;

public class DatePickerPop  extends Activity {

    TextView today;
    CalendarView calendar;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_date_picker);


        //UI 객체생성
        calendar = findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                result = year+"년"+(month + 1)+"월"+dayOfMonth+"일";
                Toast.makeText(DatePickerPop.this,"" + year + "/" +
                        (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                today.setText(result);
            }
        });



        today = findViewById(R.id.today);
        today.setText(result);
        /*ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingString = String.valueOf(rating);
                txtText.setText("별점은요"+rating);
            }
        });*/

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result",result);
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }
    //취소버튼
    public void OnClose(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}