package com.example.android_project.recode_input;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android_project.R;

import java.util.ArrayList;

public class Fragment1 extends Fragment {

    InputRecode inputRecode;

    ListView input_listview1;
    ArrayList<RepairDTO> arrayList;
    RepairAdapter adapter;

    ImageView imageView;
    TextView sub_text;
    String result = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        inputRecode = (InputRecode) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.input_fragment1, container, false);


        arrayList = new ArrayList<RepairDTO>();

        input_listview1 = rootView.findViewById(R.id.input_listview1);
        imageView = rootView.findViewById(R.id.sub_image);

        adapter = new RepairAdapter(getContext(), arrayList, R.layout.sub_item);

        input_listview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        input_listview1.setAdapter(adapter);

        RepairList repairList = new RepairList(arrayList, adapter);
        repairList.execute();

        return rootView;
    }
}
