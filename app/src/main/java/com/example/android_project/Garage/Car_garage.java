package com.example.android_project.Garage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_project.ChildFragment3;
import com.example.android_project.R;

import  static com.example.android_project.Login_user.LoginMemberDTO.mycar_star;
import java.util.concurrent.ExecutionException;


public class Car_garage extends Fragment {
    TextView rank, myEff, efficiency, my_money;
    String result = "";
  /*  Button go_carBoard;*/
    TextView marke_star;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_car_garage,container,false);
        /*textView1 = rootView.findViewById(R.id.textView1);*/

       /* go_carBoard=rootView.findViewById(R.id.go_carBoard);
        go_carBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChildFragment3.class);
                startActivity(intent);
            }
        });
*/




        rank = rootView.findViewById(R.id.rank);
        myEff = rootView.findViewById(R.id.myEff);
        efficiency = rootView.findViewById(R.id.efficiency);
        my_money = rootView.findViewById(R.id.my_money);
        marke_star = rootView.findViewById(R.id.marke_star);
        marke_star.setText(mycar_star);


        garageSelect gs = new garageSelect();
        try {
            result = gs.execute().get();
            myEff.setText(result.split(",")[0].trim());
            efficiency.setText(result.split(",")[1].trim());
            rank.setText(result.split(",")[2].trim());
            my_money.setText(result.split(",")[3].trim());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}
