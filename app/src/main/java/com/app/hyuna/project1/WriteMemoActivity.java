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
                if(title.equals("")){
                    Toast.makeText(getApplicationContext(),"제목을 입력하세요",Toast.LENGTH_SHORT).show();
                }else{
                    task = new WriteMemoTask().execute(userId,title,memo);

                }
            }
        });


        //TextWatcher하면 TextView한줄 글자수 제한 할 수 있음
    }

    private class WriteMemoTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            HttpPostData(strings[0],strings[1],strings[2]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"저장되었습니다!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WriteMemoActivity.this,ListActivity.class);
            intent.putExtra("userId",userId);
            intent.putExtra("list",MEMO);
            startActivity(intent);
            finish();
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

            Log.d("!!!!!!!",userId+", "+title+", "+memo);
            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(),"UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            InputStreamReader tmp = new InputStreamReader(
                    http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str + "\n");
            }
            String myResult = builder.toString();

            Log.d("result", "result : " + myResult);
        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        } // try
    }
}
