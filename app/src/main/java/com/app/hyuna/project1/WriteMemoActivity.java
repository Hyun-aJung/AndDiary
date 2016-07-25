package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

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
    AsyncTask<?,?,?> task;

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
                    task = new WriteMemoTask().execute(userId,title,memo);

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

    private class WriteMemoTask extends AsyncTask<String, Void,Void>{
        @Override
        protected Void doInBackground(String... strings) {
            HttpPostData(strings[0],strings[1],strings[2]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void HttpPostData(String userId, String title, String memo){
        try{
            URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/write_memo.php");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            http.setRequestProperty("content-type",
                    "application/x-www-form-urlencoded");
            StringBuffer buffer = new StringBuffer();
            buffer.append("id").append("=").append(userId).append("&");
            buffer.append("title").append("=").append(title).append("&");
            buffer.append("memo").append("=").append(memo);
            Log.d("!!!!!!!!","id : "+userId+", title : " + title+", memo : "+memo);
            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(),"UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            Log.d("Buffer :","Buffer :" + buffer.toString());
            writer.flush();

        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        } // try
    }
}
