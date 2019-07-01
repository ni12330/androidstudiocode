package com.example.android_project.notify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android_project.R;
import com.example.android_project.board.BoardDetail;

import java.util.ArrayList;

public class Notify extends Fragment {
    TextView textView1;
    NotifyListSelect listSelect2;
    ListView listView2;
    NotifyAdapter myListAdapter2;
    ArrayList<NotifyItem> myItem2ArrayList;
    NotifyItem item2 = null;
    String notify_content;

    ProgressDialog progressDialog;


    public static Notify newInstance(){
        return new Notify();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_notify,container,false);
        /* textView1 = rootView.findViewById(R.id.textView1);*/
        myItem2ArrayList = new ArrayList<NotifyItem>();
        myListAdapter2 = new NotifyAdapter(getContext(),myItem2ArrayList, R.layout.activity_notify_subitem);
        listView2=rootView.findViewById(R.id.listView2);
        listView2.setAdapter(myListAdapter2);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item2 = (NotifyItem) myListAdapter2.getItem(position);



                Intent intent = new Intent(getContext(), NotifyDetail.class);
                intent.putExtra("notify_title",item2.notify_title);
                intent.putExtra("notify_content",item2.notify_content);
                intent.putExtra("notify_date",item2.notify_date);


                startActivity(intent);


     /*           board_title = item2.getBoard_title();
                board_content=item2.getBoard_content();
*/
            }
        });
        listSelect2 = new NotifyListSelect(myItem2ArrayList, myListAdapter2, progressDialog, "/index/notify");
        listSelect2.execute();
        return rootView;
    }


}
