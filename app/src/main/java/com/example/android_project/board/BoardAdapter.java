package com.example.android_project.board;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android_project.R;
import com.example.android_project.board.BoardDTO;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<BoardDTO> arrayList;
    int layout;

    public BoardAdapter(Context context, ArrayList<BoardDTO> arrayList, int layout) {
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new PersonViewHolder();


            viewHolder.board_title =  convertView.findViewById(R.id.board_title);
            viewHolder.board_read_cnt = convertView.findViewById(R.id.board_read_cnt);
            viewHolder.board_reply_cnt = convertView.findViewById(R.id.board_reply_cnt);
            viewHolder.sympathy_cnt = convertView.findViewById(R.id.sympathy_cnt);



            convertView.setTag(viewHolder);
        }else{   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (PersonViewHolder) convertView.getTag();
        }



        viewHolder.board_title.setText(arrayList.get(position).getBoard_title());
        viewHolder.board_read_cnt.setText(String.valueOf(arrayList.get(position).getReadcnt()));
        viewHolder.board_reply_cnt.setText(String.valueOf(arrayList.get(position).getBoard_reply_cnt()));
        viewHolder.sympathy_cnt.setText(String.valueOf(arrayList.get(position).getSympathy_cnt()));






        return convertView;
    }

    public static class PersonViewHolder{
        public TextView board_title;
        public TextView board_content;
        public TextView board_reply_cnt;
        public TextView board_read_cnt;
        public TextView sympathy_cnt;
    }
}