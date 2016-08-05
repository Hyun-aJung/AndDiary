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
    static int deviceWidth;

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
        deviceWidth = displayMetrics.widthPixels;

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
                }else if (memo == null) {//추가할 이미지가 없을 떄
                    Toast.makeText(getApplicationContext(), "메모를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if(btnDate.getText().toString().equals("Choose Date")){ //날짜 선택 안했으면 현재 날짜로 자동 setting
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat fFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        fNow = fFormat.format(date);
                        btnDate.setText(fNow);
                    }
                    task = new WritePostTask().execute(userId,fNow,memoTitle,memo,tempImg[0],tempImg[1],tempImg[2],tempImg[3],tempImg[4]);

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
            grid.setDisplaySize(deviceWidth,deviceWidth);
            gridView.setAdapter(grid);

        }
    }

//http://blog.naver.com/legendx/40132716891 따라하는중
    //https://www.simplifiedcoding.net/android-upload-image-to-server-using-php-mysql/ 참고햅
    //http://titis.tistory.com/48
//http://www.verydemo.com/demo_c116_i9557.html 갈아탐
    private class WritePostTask extends AsyncTask<String, Void, Void> {//img2
        String noResult,myResult;

        @Override
        protected Void doInBackground(String... strings) {

            try {

                String boundary = "q0990sdfw0099l";
                String lineEnd = "\r\n";
                String twoHyphens = "--";

                URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/write_post3.php");
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

                if(!strings[4].equals("0")){//img1
                String filePath = strings[4];
                int index = filePath.lastIndexOf("/");
                String fileName = userId+"__"+filePath.substring(index+1,filePath.length());
                    Log.d("!!!!!!!!!fildname1", fileName);

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
                }
                if(!strings[5].equals("0")) {//img2

                    dos.writeBytes(lineEnd);
                    String filePath2 = strings[5];
                    int index2 = filePath2.lastIndexOf("/");
                    String fileName2 = userId+"__"+filePath2.substring(index2 + 1, filePath2.length());
                    Log.d("!!!!!!!!!fildname2", fileName2);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"img2\";filename=\"" + fileName2 + "\"" + lineEnd);
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
                        bytesRead2 = fis2.read(buffer2, 0, bufferSize2);
                    }
                    fis2.close();
                }
                if(!strings[6].equals("0")) {//img3

                    dos.writeBytes(lineEnd);
                    String filePath3 = strings[6];
                    int index3 = filePath3.lastIndexOf("/");
                    String fileName3 =userId+"__"+ filePath3.substring(index3 + 1, filePath3.length());
                    Log.d("!!!!!!!!!fildname3", fileName3);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"img3\";filename=\"" + fileName3 + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    FileInputStream fis3 = new FileInputStream(filePath3);
                    int bytesAvailable3 = fis3.available();
                    int maxBufferSize3 = 1024;
                    int bufferSize3 = Math.min(bytesAvailable3, maxBufferSize3);
                    byte[] buffer3 = new byte[bufferSize3];

                    int bytesRead3 = fis3.read(buffer3, 0, bufferSize3);
                    while (bytesRead3 > 0) {
                        DataOutputStream dataWrite = new DataOutputStream(http.getOutputStream());
                        dataWrite.write(buffer3, 0, bufferSize3);
                        bytesAvailable3 = fis3.available();
                        bufferSize3 = Math.min(bytesAvailable3, maxBufferSize3);
                        bytesRead3 = fis3.read(buffer3, 0, bufferSize3);
                    }
                    fis3.close();
                }
                if(!strings[7].equals("0")) {//img4

                    dos.writeBytes(lineEnd);
                    String filePath4 = strings[7];
                    int index4 = filePath4.lastIndexOf("/");
                    String fileName4 = userId+"__"+filePath4.substring(index4 + 1, filePath4.length());
                    Log.d("!!!!!!!!!fildname4", fileName4);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"img4\";filename=\"" + fileName4 + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    FileInputStream fis4 = new FileInputStream(filePath4);
                    int bytesAvailable4 = fis4.available();
                    int maxBufferSize4 = 1024;
                    int bufferSize4 = Math.min(bytesAvailable4, maxBufferSize4);
                    byte[] buffer4 = new byte[bufferSize4];

                    int bytesRead4 = fis4.read(buffer4, 0, bufferSize4);
                    while (bytesRead4 > 0) {
                        DataOutputStream dataWrite = new DataOutputStream(http.getOutputStream());
                        dataWrite.write(buffer4, 0, bufferSize4);
                        bytesAvailable4 = fis4.available();
                        bufferSize4 = Math.min(bytesAvailable4, maxBufferSize4);
                        bytesRead4 = fis4.read(buffer4, 0, bufferSize4);
                    }
                    fis4.close();
                }
                if(!strings[8].equals("0")) {//img5

                    dos.writeBytes(lineEnd);
                    String filePath5 = strings[8];
                    int index5 = filePath5.lastIndexOf("/");
                    String fileName5 = userId+"__"+filePath5.substring(index5 + 1, filePath5.length());
                    Log.d("!!!!!!!!!fildname5", fileName5);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"img5\";filename=\"" + fileName5 + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    FileInputStream fis5 = new FileInputStream(filePath5);
                    int bytesAvailable5 = fis5.available();
                    int maxBufferSize5 = 1024;
                    int bufferSize5 = Math.min(bytesAvailable5, maxBufferSize5);
                    byte[] buffer5 = new byte[bufferSize5];

                    int bytesRead5 = fis5.read(buffer5, 0, bufferSize5);
                    while (bytesRead5 > 0) {
                        DataOutputStream dataWrite = new DataOutputStream(http.getOutputStream());
                        dataWrite.write(buffer5, 0, bufferSize5);
                        bytesAvailable5 = fis5.available();
                        bufferSize5 = Math.min(bytesAvailable5, maxBufferSize5);
                        bytesRead5 = fis5.read(buffer5, 0, bufferSize5);
                    }
                    fis5.close();
                }

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
