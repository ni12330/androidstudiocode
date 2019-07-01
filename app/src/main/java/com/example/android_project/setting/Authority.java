package com.example.android_project.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android_project.Member.MemberDTO;
import com.example.android_project.R;

import java.util.ArrayList;

public class Authority extends AppCompatActivity {

    ListView memberView;
    ArrayList<MemberDTO> myItemArrayList;
    MemberAdapter adapter;
    MemberList memberList = null;
    MemberDTO dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority);

        myItemArrayList = new ArrayList<MemberDTO>();


        memberView = findViewById(R.id.memberView);

        adapter = new MemberAdapter(getApplicationContext(), myItemArrayList, R.layout.authority_item);

        memberView.setAdapter(adapter);

        memberList = new MemberList(myItemArrayList, adapter);
        memberList.execute();


        memberView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dto = (MemberDTO) adapter.getItem(position);

                AlertDialog.Builder ab = new AlertDialog.Builder(Authority.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setTitle("권한부여")
                        .setMessage("권한을 부여/해제")
                        .setPositiveButton("부여/해제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String u_id = dto.getUser_id();
                                String admin_state = dto.getAdmin();

                                AuthorityUpdate authorityUpdate = new AuthorityUpdate(u_id, admin_state);
                                authorityUpdate.execute();
                                finish();
                                startActivity(getIntent());


                                Toast.makeText(Authority.this, "권한 부여/해제 완료", Toast.LENGTH_SHORT).show();

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
    }
}
