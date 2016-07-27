package com.app.hyuna.project1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by 4강의실 on 2016-07-14.
 */
public class WritePostActivity extends Activity {
    EditText edtTitle, edtMemo;
    Button btnNew, btnSave, btnDate;
    GridView gridView;
    String memoTitle, memo, userId;
    int listNum;
    final int MEMO = 0, DRAW = 1, POST = 2, SET = 3;
    String[] tempImg = {"0", "0", "0", "0", "0"};
    AsyncTask<?,?,?> task;
    GregorianCalendar calendar;
    Date d;
    int year1, month1, day1, check = 2;

    View dialogView;
    DatePicker datePicker;
    TimePicker timePicker;
    FrameLayout dateLayout, timeLayout;

    String fNow;
    AlertDialog.Builder dlg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        setTitle("Postscript");

        Intent intent = getIntent();
        userId = intent.getExtras().getString("userId");
        listNum = intent.getExtras().getInt("list");//intent로 보낸 데이터 받기

        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtMemo = (EditText) findViewById(R.id.edtMemo);
        gridView = (GridView) findViewById(R.id.gridView);
        btnNew = (Button) findViewById(R.id.btnNewImg);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDate = (Button) findViewById(R.id.btnDate);

        edtTitle.isFocused();
        DisplayMetrics displayMetrics = new DisplayMetrics();//Display size구해서 이쁘게 배치할거야 이미지
        WindowManager windowManager = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;

        GridViewImg grid = new GridViewImg(this);
        grid.setDisplaySize(deviceWidth,deviceWidth);

        //default 로 gridView 에 default 이미지만 보여줌
        gridView.setAdapter(grid);//new GridViewImg(this));

        edtMemo.setHint("메모를 입력하세요");
        btnNew.setOnClickListener(new View.OnClickListener() {//+버튼 클릭시
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), ChooseImageActivity.class);
                startActivityForResult(mIntent, 0);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {//save버튼 클릭시
            @Override
            public void onClick(View view) {
                memoTitle = edtTitle.getText().toString();
                memo = edtMemo.getText().toString();
                //sMemo = memo;
                //sTitle = memoTitle;//async에서 쓸변수
                if (memoTitle == null) {
                    Toast.makeText(getApplicationContext(), "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else if (false) {//추가할 이미지가 없을 떄
                    //TODO title, memo 없으면 입력하라하고 image 없으면 이미지는 저장하지 않고 DB저장
                    //TODO defalt 이미지 제외하고 추가한 이미지만 DB에 저장한다
                } else {
                    if(btnDate.getText().toString().equals("Choose Date")){ //날짜 선택 안했으면 현재 날짜로 자동 setting
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat fFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        fNow = fFormat.format(date);
                        btnDate.setText(fNow);
                    }
                    if(tempImg[1].equals("0")){//img1개
                        task = new WritePostTask1().execute(userId,fNow,memoTitle,memo,tempImg[0]);

                    }else if(tempImg[2].equals("0")){//img2개
                        task = new WritePostTask2().execute(userId,fNow,memoTitle,memo,tempImg[0],tempImg[1]);
                    }
                    else if(tempImg[3].equals("0")){//img3개
                        //task = new WritePostTask3().execute(userId,fNow,memoTitle,memo,tempImg[0],tempImg[1],tempImg[2]);
                    }
                    else if(tempImg[4].equals("0")){//img4개
                       // task = new WritePostTask4().execute(userId,fNow,memoTitle,memo,tempImg[0],tempImg[1],tempImg[2],tempImg[3]);
                    }else{//img5개
                        //task = new WritePostTask5().execute(userId,fNow,memoTitle,memo,tempImg[0],tempImg[1],tempImg[2],tempImg[3],tempImg[4]);
                    }
                    //이미지 포함 데이터 저장 끝나면 intent
                    Toast.makeText(getApplicationContext(),"저장되었습니다!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WritePostActivity.this, ListActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("list",POST);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() { //날짜 선택
            @Override
            public void onClick(View view) {
                dialogView = (View) View.inflate(getApplicationContext(), R.layout.activity_write_post_date, null);
                dlg = new AlertDialog.Builder(WritePostActivity.this);
                dlg.setTitle("Choose Date");
                dlg.setView(dialogView);
                datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
                timePicker = (TimePicker) dialogView.findViewById(R.id.timePicker);
                dateLayout = (FrameLayout) dialogView.findViewById(R.id.dateLayout);
                timeLayout = (FrameLayout) dialogView.findViewById(R.id.timeLayout);
                calendar = new GregorianCalendar();

                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                        new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {

                                year1 = year;
                                month1 = month;
                                day1 = day;

                                check = 1; //확인버튼 오류나지 않기 위해
                                dateLayout.setVisibility(View.INVISIBLE);
                                timeLayout.setVisibility(View.VISIBLE);
                            }
                        });

                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                        calendar.set(year1, month1, day1, hour, min);
                        d = calendar.getTime();

                        check = 0; //확인버튼 오류나지 않기 위해
                    }
                });


                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (check == 0) { //날짜 시간 모두 다 선택하고 확인 눌렀을 떄
                            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String time = sf.format(d);
                            btnDate.setText(time);
                        } else { //날짜만 선택하고 확인 눌렀을 떄
                            Toast.makeText(getApplicationContext(), "날짜와 시간을 다시선택하세요", Toast.LENGTH_SHORT).show();
                            dateLayout.setVisibility(View.VISIBLE);
                            timeLayout.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            tempImg[0] = data.getExtras().getString("pic0");
            tempImg[1] = data.getExtras().getString("pic1");
            tempImg[2] = data.getExtras().getString("pic2");
            tempImg[3] = data.getExtras().getString("pic3");
            tempImg[4] = data.getExtras().getString("pic4");
            Log.d("!!!!!!!!!temp",tempImg[1]);
            GridViewImg grid = new GridViewImg(this);
            grid.setBitImg(tempImg);
            gridView.setAdapter(grid);
        }
    }

//http://blog.naver.com/legendx/40132716891 따라하는중
    //https://www.simplifiedcoding.net/android-upload-image-to-server-using-php-mysql/ 참고햅
    //http://titis.tistory.com/48
    private class WritePostTask1 extends AsyncTask<String, Void, Void> { //img1개
    String noResult,myResult;

    @Override
    protected Void doInBackground(String... strings) {
/* no max인거 구할랬는데
            try {

                URL url1 = new URL("http://hyunazi.dothome.co.kr/AndDiary/get_no.php");
                HttpURLConnection http1 = (HttpURLConnection) url1.openConnection();
                http1.setDefaultUseCaches(false);
                http1.setDoInput(true);
                http1.setDoOutput(true);
                http1.setRequestMethod("POST");

                http1.setRequestProperty("content-type","application/x-www-form-urlencoded");

                OutputStreamWriter outStream = new OutputStreamWriter(http1.getOutputStream(), "UTF-8");
                PrintWriter writer = new PrintWriter(outStream);
                StringBuffer buffer = new StringBuffer();
                buffer.append("id").append("=").append(userId);
                writer.write(buffer.toString());
                writer.flush();

                InputStreamReader tmp = new InputStreamReader(
                        http1.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    builder.append(str + "\n");
                }
                noResult = builder.toString();

                Log.d("resultNo", "result : " + noResult);
//            } catch (MalformedURLException e) {
//                //
//            } catch (IOException e) {
//                //
//            }
*/
            try {//http://www.verydemo.com/demo_c116_i9557.html 갈아탐

                String boundary = "q0990sdfw0099l";
                String lineEnd = "\r\n";
                String twoHyphens = "--";

                URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/write_post1.php");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setChunkedStreamingMode(1024*1024);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setUseCaches(false);
                http.setRequestMethod("POST");

                http.setRequestProperty("Charset","UTF-8");
                http.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

                //Id
                DataOutputStream dos = new DataOutputStream(http.getOutputStream());
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"id\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[0]);
                dos.writeBytes(lineEnd);

                //Date
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"date\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[1]);
                dos.writeBytes(lineEnd);

                //title
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"title\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[2]);
                dos.writeBytes(lineEnd);

                //memo
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"memo\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[3]);
                dos.writeBytes(lineEnd);


                String filePath = strings[4];
                int index = filePath.lastIndexOf("/");
                String fileName = filePath.substring(index+1,filePath.length());

                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"img\";filename=\"" + fileName + "\""+lineEnd);
                dos.writeBytes(lineEnd);

                FileInputStream fis = new FileInputStream(filePath);
                int bytesAvailable = fis.available();
                int maxBufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                int bytesRead = fis.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    DataOutputStream dataWrite = new DataOutputStream(http.getOutputStream());
                    dataWrite.write(buffer, 0, bufferSize);
                    bytesAvailable = fis.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fis.read(buffer, 0, bufferSize);
                }
                fis.close();

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);
                dos.flush();


                InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    builder.append(str + "\n");
                }
                myResult = builder.toString();

                Log.d("resultPost", "result : " + myResult);

        } catch (MalformedURLException e) {
            e.getStackTrace();
        } catch (Exception e) {
            e.getStackTrace();
        }

        return null;
    }
}

    private class WritePostTask2 extends AsyncTask<String, Void, Void> {//img2
        String noResult,myResult;

        @Override
        protected Void doInBackground(String... strings) {

            try {//http://www.verydemo.com/demo_c116_i9557.html 갈아탐

                String boundary = "q0990sdfw0099l";
                String lineEnd = "\r\n";
                String twoHyphens = "--";

                URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/write_post2.php");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setChunkedStreamingMode(1024*1024);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setUseCaches(false);
                http.setRequestMethod("POST");

                http.setRequestProperty("Charset","UTF-8");
                http.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

                //Id
                DataOutputStream dos = new DataOutputStream(http.getOutputStream());
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"id\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[0]);
                dos.writeBytes(lineEnd);

                //Date
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"date\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[1]);
                dos.writeBytes(lineEnd);

                //title
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"title\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[2]);
                dos.writeBytes(lineEnd);

                //memo
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"memo\""+lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(strings[3]);
                dos.writeBytes(lineEnd);


                String filePath = strings[4];
                int index = filePath.lastIndexOf("/");
                String fileName = filePath.substring(index+1,filePath.length());

                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"img\";filename=\"" + fileName + "\""+lineEnd);
                dos.writeBytes(lineEnd);

                FileInputStream fis = new FileInputStream(filePath);
                int bytesAvailable = fis.available();
                int maxBufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                int bytesRead = fis.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    DataOutputStream dataWrite = new DataOutputStream(http.getOutputStream());
                    dataWrite.write(buffer, 0, bufferSize);
                    bytesAvailable = fis.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fis.read(buffer, 0, bufferSize);
                }
                fis.close();//img1끝
/*
                String filePath2 = strings[5];
                int index2 = filePath2.lastIndexOf("/");
                String fileName2 = filePath2.substring(index2+1,filePath2.length());

                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"img2\";filename=\"" + fileName2 + "\""+lineEnd);
                dos.writeBytes(lineEnd);

                FileInputStream fis2 = new FileInputStream(filePath2);
                int bytesAvailable2 = fis2.available();
                int maxBufferSize2 = 1024;
                int bufferSize2 = Math.min(bytesAvailable2, maxBufferSize2);
                byte[] buffer2 = new byte[bufferSize2];

                int bytesRead2 = fis2.read(buffer2, 0, bufferSize2);
                while (bytesRead2 > 0) {
                    DataOutputStream dataWrite = new DataOutputStream(http.getOutputStream());
                    dataWrite.write(buffer2, 0, bufferSize2);
                    bytesAvailable2 = fis2.available();
                    bufferSize2 = Math.min(bytesAvailable2, maxBufferSize2);
                    bytesRead2 = fis.read(buffer2, 0, bufferSize2);
                }
                fis2.close();
*/
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);
                dos.flush();


                InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    builder.append(str + "\n");
                }
                myResult = builder.toString();

                Log.d("resultPost", "result : " + myResult);

            } catch (MalformedURLException e) {
                e.getStackTrace();
            } catch (Exception e) {
                e.getStackTrace();
            }

            return null;
        }
    }

}
