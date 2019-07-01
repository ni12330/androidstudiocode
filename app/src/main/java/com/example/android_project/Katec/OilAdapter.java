package com.example.android_project.Katec;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_project.R;

import java.util.ArrayList;

public class OilAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Oil_DTO> arrayList;
    int layout;

    public OilAdapter(Context context, ArrayList<Oil_DTO> arrayList, int layout) {
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
        final OilAdapter.OilViewHolder viewHolder;


        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new OilAdapter.OilViewHolder();


            viewHolder.oil_name = convertView.findViewById(R.id.oil_name);
            viewHolder.oil_cost = convertView.findViewById(R.id.oil_cost);
            viewHolder.oil_comp = convertView.findViewById(R.id.oil_comp);

            convertView.setTag(viewHolder);
        }else{   // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (OilAdapter.OilViewHolder) convertView.getTag();
        }


        //viewHolder.board_read_cnt.setText(arrayList.get(position).getReadcnt());
        viewHolder.oil_name.setText(arrayList.get(position).getOil_name());
        viewHolder.oil_cost.setText(String.valueOf(arrayList.get(position).getOil_price()));
        String oilcomp = arrayList.get(position).getOil_comp();
        if(oilcomp.contains("SKE")){
            viewHolder.oil_comp.setImageResource(R.drawable.ske);
        }else if(oilcomp.contains("GSC")){
            viewHolder.oil_comp.setImageResource(R.drawable.gsc);
        }else if(oilcomp.contains("HDO")){
            viewHolder.oil_comp.setImageResource(R.drawable.hdo);
        }else if(oilcomp.contains("SOL")){
            viewHolder.oil_comp.setImageResource(R.drawable.sol);
        }else {
            viewHolder.oil_comp.setImageResource(R.drawable.ske);
        }





        return convertView;
    }

    public static class OilViewHolder{
        public TextView oil_name;
        public TextView oil_cost;
        public ImageView oil_comp;
    }

    
}
