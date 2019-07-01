package com.example.android_project.home_alignlist;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android_project.R;

import java.util.ArrayList;

import static com.example.android_project.Login_user.LoginMemberDTO.user_id;

public class Home_list extends AppCompatActivity {

    ListAlign listAlign;
    ArrayList<AlignDTO> repair_itemarray;
    ListView listView;
    AlignAdapter adapter;
    AlignDTO selitem;
    String sel_id,sel_name,sel_term,sel_mile;
    Button btn_back;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);


        progressBar = findViewById(R.id.progressBar);
//        progressBar.setIndeterminate(false);


        repair_itemarray = new ArrayList<AlignDTO>();
        adapter = new AlignAdapter(this, repair_itemarray, R.layout.repair_item);
        listView = findViewById(R.id.alignlistview);
        listView.setAdapter(adapter);

        listAlign = new ListAlign(repair_itemarray, adapter, "/index/repair?user_id="+user_id);
        listAlign.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selitem = (AlignDTO) adapter.getItem(position);

                sel_id = selitem.getRepair_id();
                sel_name = selitem.getRepair_name();
                sel_term = selitem.getRepair_term();
                sel_mile = selitem.getRepair_mile();

                listView.getItemsCanFocus();

            }
        });


        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
