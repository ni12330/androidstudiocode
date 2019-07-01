package com.example.android_project.record;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android_project.R;

import java.util.ArrayList;

public class RepairRecord extends Fragment {
    ListView repair_view;

    ArrayList<RecordDTO> myItemArrayList;
    MyRepairAdapter adapter;
    MyRepairMenu myRepairMenu;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        myRepairMenu = (MyRepairMenu) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.repair_list, container, false);

        repair_view = rootView.findViewById(R.id.repair_view);

        myItemArrayList = new ArrayList<RecordDTO>();

        adapter = new MyRepairAdapter(getContext(), myItemArrayList, R.layout.my_repair_list_item);

        repair_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        repair_view.setAdapter(adapter);

        MyRepairList myRepairList = new MyRepairList(myItemArrayList, adapter);
        myRepairList.execute();

        return rootView;
    }
}



