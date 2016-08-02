package com.app.hyuna.project1;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by 4강의실 on 2016-07-14.
 */
public class WriteDrawActivity extends Activity {
    EditText edtTitle;
    ImageButton btnBlack, btnBlue, btnGreen, btnRed, btnYellow;
    Button btnSave;
    CustomDrawView customView;
    final int MEMO=0,DRAW=1,POST=2,SET=3;

    String title, userId;
    String fileName;
    int listNum;

    AsyncTask<?,?,?> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_draw);
        setTitle("Drawing Memo");


        Intent intent = getIntent();
        userId = intent.getExtras().getString("userId");
        listNum = intent.getExtras().getInt("list");

        customView = (CustomDrawView)findViewById(R.id.myView);

        btnBlack = (ImageButton)findViewById(R.id.btnBlack);
        btnBlue = (ImageButton)findViewById(R.id.btnBlue);
        btnGreen = (ImageButton)findViewById(R.id.btnGreen);
        btnYellow = (ImageButton)findViewById(R.id.btnYellow);
        btnRed = (ImageButton)findViewById(R.id.btnRed);
        btnSave = (Button)findViewById(R.id.btnSave);

        edtTitle = (EditText)findViewById(R.id.edtTitle);

        btnBlack.setOnClickListener(new View.OnClickListener() {//검정
            @Override
            public void onClick(View view) {
                customView.setColor(Color.BLACK);
            }
        });

        btnBlue.setOnClickListener(new View.OnClickListener() {//파랑
            @Override
            public void onClick(View view) {
                customView.setColor(Color.BLUE);
            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener() {//초록
            @Override
            public void onClick(View view) {
                customView.setColor(Color.GREEN);
            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener() {//노랑
            @Override
            public void onClick(View view) {
                customView.setColor(Color.YELLOW);
            }
        });

        btnRed.setOnClickListener(new View.OnClickListener() {//빨강
            @Override
            public void onClick(View view) {
                customView.setColor(Color.RED);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = edtTitle.getText().toString();

                customView.setBitmapSize(customView.getWidth(),customView.getHeight());

                //sd카드에 저장 시작
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                Bitmap b = customView.getBitmap();

                try{
                    File f = new File(path+"/drawNote");
                    f.mkdir();

                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat fFormat = new SimpleDateFormat("yyMMddhhmmss");
                    String fNow = fFormat.format(date);

                    fileName = userId+"_"+title+"_"+fNow+".png";
                    File f2 = new File(path+"/drawNote/"+fileName);

                    Canvas c = new Canvas(b);
                    customView.draw(c);

                    FileOutputStream fos = new FileOutputStream(f2);
                    if(fos!=null){
                        b.compress(Bitmap.CompressFormat.PNG,100,fos);
                        fos.close();
                    }
                }catch (Exception e){
                    e.getStackTrace();
                }
                //sd카드에 저장 완료
                task = new SavePostTask().execute(userId,title,fileName);
            }
        });
    }

    private class SavePostTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            HttpPostData(strings[0],strings[1],strings[2]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"저장되었습니다!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WriteDrawActivity.this,ListActivity.class);
            intent.putExtra("userId",userId);
            intent.putExtra("list",DRAW);
            startActivity(intent);
            finish();
        }
    }

    public void HttpPostData(String userId, String title, String memo){
        try{
            URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/write_draw.php");
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
            buffer.append("draw").append("=").append(fileName);

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