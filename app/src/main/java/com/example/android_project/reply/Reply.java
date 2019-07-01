package com.example.android_project.reply;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Reply extends AppCompatActivity {

    ListView replyView;
    ArrayList<ReplyDTO> myItemArrayList;
    ReplyAdapter adapter;
    TextView user_id, reply_content, reply_date;
    int board_id;
    ReplyDTO replyDTO = null;
    int reply_id;

    ProgressDialog pg;

    Button reply_insert, reply_delete;
    EditText insert_reply_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply_layout);


        Intent intent = getIntent();
        board_id = intent.getIntExtra("board_id", 0);

        myItemArrayList = new ArrayList<ReplyDTO>();

        replyView = findViewById(R.id.replyView);
        user_id = findViewById(R.id.user_id);
        reply_content = findViewById(R.id.reply_content);
        reply_date = findViewById(R.id.reply_date);
        reply_insert = findViewById(R.id.reply_insert);
        reply_delete = findViewById(R.id.reply_delete);

        adapter = new ReplyAdapter(getApplicationContext(), myItemArrayList, R.layout.reply_sub);
        replyView.setAdapter(adapter);

        /*reply*/
        final ReplySelect[] replySelect = {new ReplySelect(myItemArrayList, adapter, board_id)};

        replySelect[0].execute();

        insert_reply_content=findViewById(R.id.insert_reply_content);
        reply_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply_contents = insert_reply_content.getText().toString();
                ReplyInsert replyInsert = new ReplyInsert(reply_contents, board_id);
                try {
                    if (replyInsert.execute().get().contains("success")) {
                        ReplySelect replySelect= new ReplySelect(myItemArrayList, adapter, board_id);
                        replySelect.execute();
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(Reply.this, "댓글 등록 성공", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Reply.this, "댓글 등록 실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



       /*reply_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        replyDTO = (ReplyDTO) adapter.getItem(position);

                        reply_id = Integer.parseInt(replyDTO.getReply_id());

                        ReplyDelete replyDelete = new ReplyDelete(reply_id);
                        try {
                            if (replyDelete.execute().get().contains("success")) {
                                Toast.makeText(Reply.this, "삭제 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Reply.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });*/
    }

/*   @Override
    protected void onResume() {
        super.onResume();
        ReplySelect replySelect= new ReplySelect(myItemArrayList, adapter, board_id);
        replySelect.execute();
        finish();
        startActivity(getIntent());
    }*/
}