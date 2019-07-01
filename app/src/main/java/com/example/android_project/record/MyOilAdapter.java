package com.example.android_project.record;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.MainActivity;
import com.example.android_project.R;
import com.example.android_project.reply.ReplyAdapter;
import com.example.android_project.reply.ReplyDelete;
import com.example.android_project.reply.ReplySelect;
import com.facebook.login.LoginManager;
import com.kakao.auth.Session;

import java.util.ArrayList;


public class MyOilAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<RecordDTO> arrayList;
    int layout;
    String ruser_id;

    public MyOilAdapter(Context context, ArrayList<RecordDTO> arrayList, int layout) {
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void removeAllItem(){
        arrayList.clear();
//        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyOilAdapter.oilViewHolder viewHolder;


        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new MyOilAdapter.oilViewHolder();
            viewHolder.station =  convertView.findViewById(R.id.station);
            viewHolder.oil_cost =  convertView.findViewById(R.id.oil_cost);
            viewHolder.car_mileage = convertView.findViewById(R.id.car_mileage);
            viewHolder.oil = convertView.findViewById(R.id.oil);
            viewHolder.record_date = convertView.findViewById(R.id.record_date);
            viewHolder.oil_delete = convertView.findViewById(R.id.oil_delete);




            convertView.setTag(viewHolder);
        }else{   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (MyOilAdapter.oilViewHolder) convertView.getTag();
        }

        viewHolder.station.setText(arrayList.get(position).getStation());
        viewHolder.oil_cost.setText("금액 : " + String.valueOf(Integer.parseInt(arrayList.get(position).getOil_cost()) *
                Integer.parseInt(arrayList.get(position).getOil())) + "원");
        viewHolder.car_mileage.setText("주행거리  " + arrayList.get(position).getCar_mileage() + " km");
        viewHolder.record_date.setText(arrayList.get(position).getRecord_date()
                .replace("월" , ".").replace(",", ". "));
        viewHolder.oil.setText("주유량  " + arrayList.get(position).getOil() + "L");



        //oil 삭제하기
        viewHolder.oil_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.get(position);
                AlertDialog.Builder ab = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setTitle("")
                        .setMessage("주요정보를 삭제 하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String record_id = arrayList.get(position).getRecord_id();
                                MyrecordDelete delete = new MyrecordDelete(record_id);
                                delete.execute();

                                removeAllItem();
                                MyOilList oil = new MyOilList(arrayList,MyOilAdapter.this);
                                oil.execute();

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







        return convertView;
    }



    public static class oilViewHolder{
        public TextView station;
        public TextView oil_cost;
        public TextView car_mileage;
        public TextView record_date;
        public TextView oil;
        public Button oil_delete;
    }
}