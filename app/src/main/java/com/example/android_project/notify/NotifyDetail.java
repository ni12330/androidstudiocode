package com.example.android_project.notify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_project.R;



public class NotifyDetail extends AppCompatActivity {

    TextView notify_title,notify_content,notify_date;
    String title="",content="",date="";

    Button btn_cancel;

    java.text.SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_detail);

        notify_title = findViewById(R.id.notify_title);
        notify_content = findViewById(R.id.notify_content);
        notify_date = findViewById(R.id.notify_date);
        btn_cancel = findViewById(R.id.btn_cancel);


        df = new java.text.SimpleDateFormat("yyyy년MM월dd월");

        Intent intent = getIntent();
        title=intent.getStringExtra("notify_title");
        content=intent.getStringExtra("notify_content");
        date=intent.getStringExtra("notify_date");


        notify_title.setText(title);
        notify_content.setText(content);
        notify_date.setText(date);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });








    }
}