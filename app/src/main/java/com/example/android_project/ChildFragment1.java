package com.example.android_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.android_project.Member.MemberDTO;
import com.example.android_project.board.BoardAdapter;
import com.example.android_project.board.BoardDTO;
import com.example.android_project.board.BoardDetail;
import com.example.android_project.board.ListSelect;

import java.util.ArrayList;


public class ChildFragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ListSelect listSelect;

    ArrayList<BoardDTO> myItemArrayList;
    ListView listView;
    BoardAdapter adapter;

    BoardDTO selItem = null;


    String  selboard_title;
    String selboard_content;
    String selboard_user_nick;
    String seluser_id,selcomp_id,selboard_date,selmycar,selfilepath,selfilename;
    int selboard_id,readcnt;


    ProgressDialog progressDialog;

    SwipeRefreshLayout swipeRefreshLayout;

    public static ChildFragment1 newInstance(){
        return new ChildFragment1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_child_fragment1, container, false);

        swipeRefreshLayout
                =(SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        myItemArrayList = new ArrayList<BoardDTO>();
        adapter = new BoardAdapter(getContext(), myItemArrayList, R.layout.sub1_item);
        listView = rootView.findViewById(R.id.listView1);
        listView.setAdapter(adapter);


        listSelect = new ListSelect(myItemArrayList, adapter, progressDialog,"/index/board?kind=board");
        listSelect.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("position",position+":");
                // 리스트뷰에 커스텀어댑터를 적용한거에서 각항목 가져오기
                selItem = (BoardDTO) adapter.getItem(position);

                selboard_id = selItem.getBoard_id();
                selboard_title = selItem.getBoard_title();
                selboard_content = selItem.getBoard_content();




                //전체 내용 가자가기
                seluser_id = selItem.getUser_id();
                selcomp_id = selItem.getComp_id();
                selboard_date = selItem.getBoard_date();
                selmycar = selItem.getMycar();
                readcnt = selItem.getReadcnt();
                selfilename = selItem.getFilename();
                selfilepath = selItem.getFilepath();
                selboard_user_nick = selItem.getBoard_user_nick();
                listView.getItemsCanFocus();


                //BoardDetail로 화면 전환할때 까져가기
                Intent intent = new Intent(getContext(),BoardDetail.class);
                intent.putExtra("board_id",selboard_id);
                intent.putExtra("board_title",selboard_title);
                intent.putExtra("board_content",selboard_content);


                intent.putExtra("user_id",seluser_id);
                intent.putExtra("comp_id",selcomp_id);
                intent.putExtra("board_date",selboard_date);
                intent.putExtra("mycar",selmycar);
                intent.putExtra("readcnt",readcnt);
                intent.putExtra("filename",selfilename);
                intent.putExtra("filepath",selfilepath);
                intent.putExtra("board_user_nick",selboard_user_nick);
                startActivity(intent);




            }
        });



        return rootView;
    }

    @Override
    public void onRefresh() {
        myItemArrayList = new ArrayList<BoardDTO>();
        adapter = new BoardAdapter(getContext(), myItemArrayList, R.layout.sub1_item);
        listView.setAdapter(adapter);
        listSelect = new ListSelect(myItemArrayList, adapter, progressDialog,"/index/board?kind=board");
        listSelect.execute();
        swipeRefreshLayout.setRefreshing(false);
    }
}