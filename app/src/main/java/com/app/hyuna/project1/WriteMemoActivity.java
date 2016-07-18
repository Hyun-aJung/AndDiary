package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 4강의실 on 2016-07-14.
 */
public class WriteMemoActivity extends Activity {
    EditText edtTitle, edtMemo;
    Button btnSave;
    String title, memo;
    String userId;
    int listNum;
    final int MEMO=0,DRAW=1,POST=2,SET=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_memo);
        setTitle("Short Memo");
        Intent intent = getIntent();
        userId = intent.getExtras().getString("userId");
        listNum = intent.getExtras().getInt("list");//intent로 보낸 데이터 받기

        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtMemo = (EditText)findViewById(R.id.edtMemo);
        btnSave = (Button)findViewById(R.id.btnSave);

        edtTitle.isFocused();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = edtTitle.getText().toString();
                memo = edtMemo.getText().toString();
                if(title==""){
                    Toast.makeText(getApplicationContext(),"제목을 입력하세요",Toast.LENGTH_SHORT).show();
                }else{
                //TODO DB에 저장


                    Intent intent = new Intent(WriteMemoActivity.this,ListActivity.class);
                    intent.putExtra("userId",userId);
                    intent.putExtra("list",MEMO);
                    startActivity(intent);
                    finish();
                }
            }
        });


        //TextWatcher하면 TextView한줄 글자수 제한 할 수 있음
    }
}
