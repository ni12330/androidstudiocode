package com.example.android_project.recode_input;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android_project.R;

import java.util.ArrayList;

public class InputAdapter extends BaseAdapter {

    TextView checkbox_val;
    Context context;
    int layout;
    LayoutInflater inflater;

    public  ArrayList<RepairDTO> listViewItemList = new ArrayList<RepairDTO>();
    private ArrayList<RepairDTO> filteredItemList = listViewItemList;

    public static String input_cost_val, memo_val;

    public InputAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<RepairDTO> getFilteredItemList() {
        return filteredItemList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        final ViewHolder holder;

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.input_item, parent, false);

            holder.input_cost = (EditText)convertView.findViewById(R.id.input_cost);
            holder.memo = (EditText)convertView.findViewById(R.id.memo);

            checkbox_val = (TextView) convertView.findViewById(R.id.checkbox_val);

            if(listViewItemList.get(pos).getCheckbox_val() != null){
                checkbox_val.setText(listViewItemList.get(pos).getCheckbox_val());
            }

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.ref = position;

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        final EditText input_cost = (EditText)convertView.findViewById(R.id.input_cost);

        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        final RepairDTO listViewItem = filteredItemList.get(position);

        holder.input_cost.setText(listViewItem.getCost());
        holder.memo.setText(listViewItem.getMemo());

        holder.input_cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredItemList.get(holder.ref).setCost(s.toString());
                input_cost_val = "";
                for(int i=0; i<filteredItemList.size(); i++) {
                    if(input_cost_val != null){
                        input_cost_val += filteredItemList.get(i).getCost() + ",";
                    }else {
                        input_cost_val += "0,";
                    }
                }
            }
        });

        holder.memo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredItemList.get(holder.ref).setMemo(s.toString());

                memo_val = "";
                for(int i=0; i<filteredItemList.size(); i++) {
                    if(memo_val != null){
                        memo_val += filteredItemList.get(i).getMemo() + ",";
                    }else {
                        memo_val += "-,";
                    }
                }
            }
        });

        return convertView;
    }

    public void addItem(String checkbox_val, String cost, String memo, int num) {
        RepairDTO item = new RepairDTO();
        item.setCheckbox_val(checkbox_val);
        item.setCost(cost);
        item.setMemo(memo);

        listViewItemList.add(item);
    }

  /*  public void delItem() {
        if (listViewItemList.size() < 1) {
        } else {
            listViewItemList.remove(listViewItemList.size() - 1);
        }
    }*/
}