package com.example.android_project.record;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android_project.R;
import com.example.android_project.reply.ReplyAdapter;
import com.example.android_project.reply.ReplyDelete;
import com.example.android_project.reply.ReplySelect;


import java.util.ArrayList;


public class MyRepairAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<RecordDTO> arrayList;
    int layout;
    String ruser_id;

    public MyRepairAdapter(Context context, ArrayList<RecordDTO> arrayList, int layout) {
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
        final MyRepairAdapter.RecordViewHolder viewHolder;


        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new MyRepairAdapter.RecordViewHolder();
            viewHolder.repair_name =  convertView.findViewById(R.id.repair_name);
            viewHolder.repair_cost =  convertView.findViewById(R.id.repair_cost);
            viewHolder.memo = convertView.findViewById(R.id.memo);
            viewHolder.repair_date = convertView.findViewById(R.id.repair_date);

            viewHolder.repair_delete = convertView.findViewById(R.id.repair_delete);

            convertView.setTag(viewHolder);
        }else{   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (MyRepairAdapter.RecordViewHolder) convertView.getTag();
        }

        viewHolder.repair_name.setText(arrayList.get(position).getRepair_name());
        viewHolder.repair_cost.setText("금액  " + arrayList.get(position).getRepair_cost() + "원");
        viewHolder.memo.setText("MEMO\n" + arrayList.get(position).getMemo());
        viewHolder.repair_date.setText(arrayList.get(position).getRecord_date()
                .replace("월" , ".").replace(",", ". "));

        //repair 삭제하기
        viewHolder.repair_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.get(position);


                AlertDialog.Builder ab = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setTitle("")
                        .setMessage("정비정보를 삭제 하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String record_id = arrayList.get(position).getRecord_id();
                                MyrecordDelete delete = new MyrecordDelete(record_id);
                                delete.execute();

                                removeAllItem();
                                MyRepairList repair = new MyRepairList(arrayList,MyRepairAdapter.this);
                                repair.execute();

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



    public static class RecordViewHolder{
        public TextView repair_name;
        public TextView repair_cost;
        public TextView memo;
        public TextView repair_date;
        public Button repair_delete;
    }
}