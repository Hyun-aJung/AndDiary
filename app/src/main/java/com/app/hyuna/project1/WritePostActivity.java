package com.app.hyuna.project1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    final int MEMO = 0, DRAW = 1, POST = 2, SET = 3;
    String[] tempImg = {"0", "0", "0", "0", "0"};

    GregorianCalendar calendar;
    Date d;
    int year1, month1, day1, check = 2;

    View dialogView;
    DatePicker datePicker;
    TimePicker timePicker;
    FrameLayout dateLayout, timeLayout;

    AlertDialog.Builder dlg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        setTitle("Postscript");

        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtMemo = (EditText) findViewById(R.id.edtMemo);
        gridView = (GridView) findViewById(R.id.gridView);
        btnNew = (Button) findViewById(R.id.btnNewImg);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDate = (Button) findViewById(R.id.btnDate);

        edtTitle.isFocused();

        //default 로 gridView 에 default 이미지만 보여줌
        gridView.setAdapter(new GridViewImg(this));

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
                if (memoTitle == null) {
                    Toast.makeText(getApplicationContext(), "제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (false) {//추가할 이미지가 없을 떄
                    //TODO title, memo 없으면 입력하라하고 image 없으면 이미지는 저장하지 않고 DB저장
                    //TODO defalt 이미지 제외하고 추가한 이미지만 DB에 저장한다
                } else {
                    Intent intent = new Intent(WritePostActivity.this, ListActivity.class);
                    intent.putExtra("userId", userId);
                    //intent.putExtra("list",POST);
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

            GridViewImg grid = new GridViewImg(this);
            grid.setBitImg(tempImg);
            gridView.setAdapter(grid);
        }
    }
}
