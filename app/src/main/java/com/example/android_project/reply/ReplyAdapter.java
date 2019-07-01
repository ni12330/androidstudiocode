package com.example.android_project.reply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.R;

import static com.example.android_project.Login_user.LoginMemberDTO.admin;
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;

import java.util.ArrayList;

public class ReplyAdapter extends BaseAdapter{
    Context context;
    LayoutInflater inflater;
    ArrayList<ReplyDTO> arrayList;
    int layout;
    String ruser_id;

    public ReplyAdapter(Context context, ArrayList<ReplyDTO> arrayList, int layout) {
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
        final ReplyViewHolder viewHolder;


        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new ReplyViewHolder();
            viewHolder.reply_content =  convertView.findViewById(R.id.reply_content);
            viewHolder.reply_date =  convertView.findViewById(R.id.reply_date);
            viewHolder.reply_user_id = convertView.findViewById(R.id.reply_user_id);
            //viewHolder.myitem = convertView.findViewById(R.id.myitem);
            viewHolder.reply_delete = convertView.findViewById(R.id.reply_delete);

            convertView.setTag(viewHolder);
        }else{   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (ReplyViewHolder) convertView.getTag();
        }

        viewHolder.reply_date.setText(arrayList.get(position).getReply_date());
        viewHolder.reply_content.setText(arrayList.get(position).getReply_content());
        viewHolder.reply_user_id.setText(arrayList.get(position).getUser_id());

        ruser_id = arrayList.get(position).getUser_id();
        if(ruser_id.equals(user_id)||admin.contains("Y")){
            viewHolder.reply_delete.setVisibility(View.VISIBLE);
        }


        //처리방법
       viewHolder.reply_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               arrayList.get(position);
               String reply_id = arrayList.get(position).getReply_id();
               int board_id = Integer.parseInt(arrayList.get(position).getBoard_id());
               Toast.makeText(context, reply_id, Toast.LENGTH_SHORT).show();
               ReplyDelete replyDelete = new ReplyDelete(reply_id);
               replyDelete.execute();


               removeAllItem();
               ReplySelect rs = new ReplySelect(arrayList,ReplyAdapter.this, board_id);
               rs.execute();
            }
        });


        /*reply_delete*/
/*        Button reply_delete = (Button) convertView.findViewById(R.id.reply_delete);
        reply_delete.setOnClickListener((View.OnClickListener) context);*/

        return convertView;
    }



    public static class ReplyViewHolder{
        public TextView reply_content;
        public TextView reply_date;
        public TextView reply_user_id;
        public LinearLayout myitem;
        public Button reply_delete;
    }



}