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

public class OilRecord extends Fragment {
    ListView oil_view;

    ArrayList<RecordDTO> myItemArrayList;
    MyOilAdapter adapter;
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
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.oil_list, container, false);

        oil_view = rootView.findViewById(R.id.oil_view);

        myItemArrayList = new ArrayList<RecordDTO>();

        adapter = new MyOilAdapter(getContext(), myItemArrayList, R.layout.my_oil_list_item);

        oil_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        oil_view.setAdapter(adapter);

        MyOilList myOilList = new MyOilList(myItemArrayList, adapter);
        myOilList.execute();

        return rootView;
    }
}
