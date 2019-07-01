package com.example.android_project.recode_input;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.Common.DatePickerPop;
import com.example.android_project.R;

import java.util.ArrayList;
import java.util.Date;

import static com.example.android_project.recode_input.InputAdapter.input_cost_val;
import static com.example.android_project.recode_input.InputAdapter.memo_val;
import static com.example.android_project.recode_input.InputRecode.result;

public class InputRecodeForm extends AppCompatActivity {
    private Button input_cancle, input_complete, calendar_btn;
    private TextView checkbox_val, date;
    private ListView input_listview;
    private InputAdapter adapter;
    private int num;
    private EditText mileage;
    java.text.SimpleDateFormat tmpDateFormat;

    public ArrayList<EditText> find = new ArrayList<>();
    public ArrayList<RepairDTO> listViewItemList = new ArrayList<RepairDTO>(); //리스트뷰
    private ArrayList<RepairDTO> filteredItemList = listViewItemList; //리스트뷰 임시저장소
    public ArrayList<String>find2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);

        num = 0;

        input_cancle = (Button)findViewById(R.id.input_cancle);
        input_complete = (Button)findViewById(R.id.input_complete);
        calendar_btn = (Button)findViewById(R.id.calendar_btn);
        input_listview = (ListView)findViewById(R.id.input_listview);

        mileage = (EditText)findViewById(R.id.mileage);


        date = (TextView)findViewById(R.id.date);
        tmpDateFormat = new java.text.SimpleDateFormat("yyyy년M월dd일");
        String tonow = tmpDateFormat.format(new Date());
        date.setText(tonow);


        checkbox_val = (TextView)findViewById(R.id.checkbox_val);

        adapter = new InputAdapter(this, R.layout.input_item);

        input_listview.setAdapter(adapter);

        String[] sp = result.split(",");

        for(int i=0; i<sp.length; i++){
            adapter.addItem(sp[i],"","",i);
        }

        adapter.notifyDataSetChanged();

        input_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result="";
                memo_val = "";
                input_cost_val="";
                finish();
            }
        });

        input_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_cost_val == null
                        || (input_cost_val.split(",").length < result.split(",").length)
                        || memo_val == null
                        || memo_val.split(",").length < result.split(",").length) {
                    Toast.makeText(InputRecodeForm.this, "빈칸을 입력하세세요", Toast.LENGTH_SHORT).show();
                }else {
                    boolean check_cost = true;
                    if(date.length() < 1) {
                        Toast.makeText(InputRecodeForm.this, "날짜를 선택하세요", Toast.LENGTH_SHORT).show();
                    }else if(mileage.getText().toString().length() < 1) {
                        Toast.makeText(InputRecodeForm.this, "주행거리를 입력하세요", Toast.LENGTH_SHORT).show();
                    }else{
                        String check_date = date.getText().toString();
                        String check_mileage = mileage.getText().toString();

                        Log.d("fdfdfdfdfdfdf", "result : " + result + ", cost : " + input_cost_val + ", memo : " + memo_val);

                        for(int i=0; i<result.split(",").length; i++) {
                            InputInsert inputInsert = new InputInsert(result.split(",")[i], input_cost_val.split(",")[i], memo_val.split(",")[i], check_date, check_mileage);
                            try {
                                inputInsert.execute().get();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Toast.makeText(InputRecodeForm.this, "저장 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        //팝업형식의 달력
        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputRecodeForm.this, DatePickerPop.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    //팝업창 star 값 돌려받기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                String result = data.getStringExtra("result");
                if(result != null){
                    date.setText(result);
                }
                // Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

                //getSupportFragmentManager().beginTransaction().replace(R.id.,garage).commit();
            }
        }
    }
}