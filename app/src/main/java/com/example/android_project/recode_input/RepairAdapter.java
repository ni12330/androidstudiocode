package com.example.android_project.recode_input;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.android_project.R;

import java.util.ArrayList;

import static com.example.android_project.recode_input.InputRecode.result;

public class RepairAdapter  extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<RepairDTO> arrayList;
    int layout;
    int pos;

    InputRecode inputRecode;

    public RepairAdapter(Context context, ArrayList<RepairDTO> arrayList, int layout) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final RepairAdapter.RepairViewHolder viewHolder;


        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new RepairAdapter.RepairViewHolder();

            viewHolder.sub_text =  convertView.findViewById(R.id.sub_text);
            viewHolder.sub_image =  convertView.findViewById(R.id.sub_image);
            viewHolder.sub_checkbox = convertView.findViewById(R.id.sub_checkbox);
            //viewHolder.myitem = convertView.findViewById(R.id.myitem);

            convertView.setTag(viewHolder);
        }else {   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (RepairAdapter.RepairViewHolder) convertView.getTag();

            final int pos = position;
            viewHolder.sub_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(arrayList.get(pos).isConfirm()){
                        arrayList.get(pos).setConfirm(false);
                    }else{
                        arrayList.get(pos).setConfirm(true);
                    }

                    result = "";

                    for(int i=0; i<arrayList.size(); i++) {
                        if(arrayList.get(i).isConfirm()){
                            result += arrayList.get(i).getRepair_name() + ",";
                        }
                    }
                }
            });
        }


        //viewHolder.sub_checkbox.setOnCheckedChangeListener(RepairAdapter.this);

        //notifyDataSetChanged();

        viewHolder.sub_text.setText(arrayList.get(position).getRepair_name());

        viewHolder.sub_checkbox.setChecked(arrayList.get(position).isConfirm());

        String resName = "@drawable/check"+position;
        String packName = viewHolder.sub_image.getContext().getPackageName();   //패키지명
        int resID = viewHolder.sub_image.getContext().getResources().getIdentifier(resName, "drawable", packName);


        viewHolder.sub_image.setImageResource(resID);


        //viewHolder.sub_text.setText(var);
        //Glide.getPhotoCacheDir(viewHolder.sub_image.getContext(),var);



        //Glide.with(viewHolder.sub_image.getContext()).asGif().load(R.drawable.check0).into(viewHolder.sub_image);




        return convertView;
    }

    public static class RepairViewHolder{
        public TextView sub_text;
        public ImageView sub_image;
        public CheckBox sub_checkbox;
        //public LinearLayout myitem;
    }
}
