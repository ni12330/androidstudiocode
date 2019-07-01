package com.example.android_project.recode_input;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_project.R;

public class InputRecode extends AppCompatActivity {
    TabLayout tabs;
    Fragment1 fragment1;
    //Fragment2 fragment2;

    Fragment selected = null;

    Bundle mBundle;

    Button input_select_complete;
    Button inputRecode_btn_back;

    public static String result = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_recode);

        fragment1 = new Fragment1();
        //fragment2 = new Fragment2();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        inputRecode_btn_back=findViewById(R.id.inputRecode_btn_back);
        inputRecode_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "";
                finish();
            }
        });

        input_select_complete=findViewById(R.id.input_select_complete);

        input_select_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result != "" && result != null){
                    Intent intent = new Intent(getApplicationContext(), InputRecodeForm.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(InputRecode.this, "한개 이상 체크하세요", Toast.LENGTH_SHORT).show();
                }
                //화면

                //intent.putExtra("checkList", result);
            }
        });


        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("정비항목"));
      /*  tabs.addTab(tabs.newTab().setText("기타항목"));*/

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position= tab.getPosition();

                if(position==0){
                    selected = fragment1;
                }else if(position==1){
                   // selected = fragment2;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void fragBtnClicked(Bundle bundle) {
        this.mBundle = bundle;
    }
}