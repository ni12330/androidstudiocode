package com.example.android_project.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android_project.R;

public class MyRepairMenu extends AppCompatActivity {

    TabLayout tab;
    RepairRecord fragment1;
    OilRecord fragment2;

    Fragment selected = null;

    Button inputRecode_btn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_list_menu);

        fragment1 = new RepairRecord();
        fragment2 = new OilRecord();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, fragment1).commit();

        inputRecode_btn_back=findViewById(R.id.inputRecode_btn_back);
        inputRecode_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tab = findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("정비기록"));
        tab.addTab(tab.newTab().setText("주유기록"));

        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position= tab.getPosition();

                if(position==0){
                    selected = fragment1;
                }else if(position==1){
                    selected = fragment2;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.containers, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
