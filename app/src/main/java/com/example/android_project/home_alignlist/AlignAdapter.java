package com.example.android_project.home_alignlist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android_project.R;

import java.util.ArrayList;

public class AlignAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<AlignDTO> arrayList;
    int layout;

    public AlignAdapter(Context context, ArrayList<AlignDTO> arrayList, int layout) {
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final PersonViewHolder viewHolder;


        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new PersonViewHolder();

            //viewHolder.board_read_cnt = convertView.findViewById(R.id.board_read_cnt);
            viewHolder.repair_id =  convertView.findViewById(R.id.repair_id);
            viewHolder.repair_name = convertView.findViewById(R.id.repair_name);
            viewHolder.repair_term = convertView.findViewById(R.id.repair_term);
            viewHolder.repair_mile = convertView.findViewById(R.id.repair_mile);
            viewHolder.progressBar = convertView.findViewById(R.id.progressBar);


            convertView.setTag(viewHolder);
        }else{   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (PersonViewHolder) convertView.getTag();
        }


        //viewHolder.board_read_cnt.setText(arrayList.get(position).getReadcnt());
        viewHolder.repair_id.setText(arrayList.get(position).getRepair_id());
        viewHolder.repair_name.setText(arrayList.get(position).getRepair_name());
        viewHolder.repair_term.setText(arrayList.get(position).getRepair_term());
        viewHolder.repair_mile.setText(arrayList.get(position).getRepair_mile());
        viewHolder.progressBar.setMax(10000);
//        viewHolder.progressBar.setProgress(Integer.parseInt(arrayList.get(position).getRepair_mile()));
        /*   viewHolder.board_content.setText(arrayList.get(position).getBoard_content());*/


     /*   viewHolder.progressBar.getProgressDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
        viewHolder.progressBar.setMax(Integer.parseInt(car_mileage)+Integer.parseInt(arrayList.get(position).getRepair_mile()));
        viewHolder.progressBar.setProgress(Integer.parseInt(car_mileage));*/


        return convertView;
    }

    public static class PersonViewHolder{
        public TextView repair_id;
        public TextView repair_name;
        public TextView repair_term;
        public TextView repair_mile;
        public ProgressBar progressBar;
    }

}
