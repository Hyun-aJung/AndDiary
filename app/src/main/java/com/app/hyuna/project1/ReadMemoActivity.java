package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 4강의실 on 2016-07-28.
 */
public class ReadMemoActivity extends Activity {
    LinearLayout btnLayout;
    EditText edtTitle, edtMemo;
    Button btnSave;
    String id, no, date, memo, title;
    AsyncTask<?,?,?> task;

    final int MEMO=0,DRAW=1,POST=2,SET=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_memo);
        setTitle("Read Memo");
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        no = intent.getExtras().getString("no");
        memo = intent.getExtras().getString("memo");
        title = intent.getExtras().getString("title");
        date = intent.getExtras().getString("date");

        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtMemo = (EditText)findViewById(R.id.edtMemo);
        btnSave = (Button)findViewById(R.id.btnSave);

        edtTitle.setText(title);

        edtMemo.setText(memo);

        btnSave.setText("update");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = edtTitle.getText().toString();
                memo = edtMemo.getText().toString();
                task = new UpdateMemoTask().execute(title,memo,no);
            }
        });
    }

    private class UpdateMemoTask extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... strings) {
            HttpPostData(strings[0],strings[1],strings[2]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(getApplicationContext(),ListActivity.class);
            intent.putExtra("userId",id);
            intent.putExtra("listNum",MEMO);
            startActivity(intent);
            finish();
        }

        public void HttpPostData(String title, String memo,String no){
            try{
                URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/update_memo.php");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setDefaultUseCaches(false);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestMethod("POST");

                http.setRequestProperty("content-type",
                        "application/x-www-form-urlencoded");
                StringBuffer buffer = new StringBuffer();
                buffer.append("title").append("=").append(title).append("&");
                buffer.append("memo").append("=").append(memo).append("&");
                buffer.append("no").append("=").append(no);

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
}
