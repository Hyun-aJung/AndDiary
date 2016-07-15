package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

/**
 * Created by 4강의실 on 2016-07-14.
 */
public class WritePostActivity extends Activity {
    EditText edtTitle,edtMemo;
    Button btnNew,btnSave;
    GridView gridView;
    String memoTitle,memo;
    final int MEMO=0,DRAW=1,POST=2,SET=3;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        setTitle("Postscript");

        Intent intent = getIntent();
        userId = intent.getExtras().getString("userId");//intent로 보낸 데이터 받기

        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtMemo = (EditText)findViewById(R.id.edtMemo);
        gridView = (GridView)findViewById(R.id.gridView);
        btnNew = (Button)findViewById(R.id.btnNewImg);
        btnSave = (Button)findViewById(R.id.btnSave);

        //default 로 gridView 에 default 이미지만 보여줌
        gridView.setAdapter(new GridViewImg(this));

        btnNew.setOnClickListener(new View.OnClickListener() {//+버튼 클릭시
            @Override
            public void onClick(View view) {
                //TODO image파일 불러와서 gridView변경
                //TODO +버튼 눌러 이미지 추가하면 default 이미지에서 해당 이미지로 바꿈
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {//save버튼 클릭시
            @Override
            public void onClick(View view) {
                memoTitle = edtTitle.getText().toString();
                memo = edtMemo.getText().toString();
                //TODO title, memo 없으면 입력하라하고 image 없으면 이미지는 저장하지 않고 DB저장
                //TODO defalt 이미지 제외하고 추가한 이미지만 DB에 저장한다

                Intent intent = new Intent(WritePostActivity.this,ListActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("list",POST);
                startActivity(intent);
                finish();
            }
        });
    }
}
