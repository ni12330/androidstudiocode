package com.example.android_project.board;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.android_project.ChildFragment1;
import com.example.android_project.ChildFragment2;
import com.example.android_project.ChildFragment3;
import com.example.android_project.R;

import android.widget.LinearLayout;


public class Board extends Fragment implements View.OnClickListener {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fv = inflater.inflate(R.layout.activity_board, container, false);

        Button btn_one, btn_two,btn_three;
        btn_one =  fv.findViewById(R.id.btn_one);
        btn_one.setOnClickListener(this);
        btn_two =  fv.findViewById(R.id.btn_two);
        btn_two.setOnClickListener(this);
        btn_three = fv.findViewById(R.id.btn_three);
        btn_three.setOnClickListener(this);

        Fragment fg = ChildFragment1.newInstance();
        setChildFragment(fg);

        FloatingActionButton fab = fv.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoardNew.class);
                startActivity(intent);
            }
        });

        return fv;
    }


    @Override
    public void onClick(View view) {
        Fragment fg;
        switch (view.getId()) {
            case R.id.btn_one:
                fg = ChildFragment1.newInstance();
                setChildFragment(fg);
                break;
            case R.id.btn_two:
                fg = ChildFragment2.newInstance();
                setChildFragment(fg);
                break;
            case R.id.btn_three:
                fg = ChildFragment3.newInstance();
                setChildFragment(fg);
                break;
        }
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.child_fragment_container, child);
            childFt.commit();
        }
    }
}




