package com.example.android_project.notify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android_project.R;

import java.util.ArrayList;

public class NotifyAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    ArrayList<NotifyItem> arrayList;
    int layout;

    public NotifyAdapter(Context context, ArrayList<NotifyItem> arrayList, int layout) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.layout = layout;
    }

    public void removeAllItem(){
        arrayList.clear();
        notifyDataSetChanged();
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


        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new PersonViewHolder();
            viewHolder.notify_title =  convertView.findViewById(R.id.notify_title);


            viewHolder.notify_item = convertView.findViewById(R.id.notify_item);


            convertView.setTag(viewHolder);
        }else{   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (PersonViewHolder) convertView.getTag();
        }

        viewHolder.notify_title.setText(arrayList.get(position).getNotify_title());

//        Log.d("arrayList", arrayList.get(position).getNotify_content());
     /*   viewHolder.board_content.setText(arrayList.get(position).getBoard_content());*/




        return convertView;
    }






    public static class PersonViewHolder
    {
        public TextView notify_title;
        public LinearLayout notify_item;

    }
}
























