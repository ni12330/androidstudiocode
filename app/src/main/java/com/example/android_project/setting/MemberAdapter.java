package com.example.android_project.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android_project.Member.MemberDTO;
import com.example.android_project.R;

import java.util.ArrayList;

public class MemberAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<MemberDTO> arrayList;
    int layout;



    public MemberAdapter(Context context, ArrayList<MemberDTO> arrayList, int layout) {
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
        final MemberAdapter.MemberViewHolder viewHolder;


        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new MemberAdapter.MemberViewHolder();

            viewHolder.member_user_id =  convertView.findViewById(R.id.user_id);
            viewHolder.authority =  convertView.findViewById(R.id.authority);
            viewHolder.myitem = convertView.findViewById(R.id.myitem);

            convertView.setTag(viewHolder);
        }else{   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (MemberAdapter.MemberViewHolder) convertView.getTag();
        }

        viewHolder.member_user_id.setText(arrayList.get(position).getUser_id());
        viewHolder.authority.setText(arrayList.get(position).getAdmin());

        /*reply_delete*/
/*        Button reply_delete = (Button) convertView.findViewById(R.id.reply_delete);
        reply_delete.setOnClickListener((View.OnClickListener) context);*/

        return convertView;
    }

    public static class MemberViewHolder{
        public TextView authority;
        public TextView member_user_id;
        public LinearLayout myitem;
    }
}
