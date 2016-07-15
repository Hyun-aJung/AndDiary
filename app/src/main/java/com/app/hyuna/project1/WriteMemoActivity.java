package com.app.hyuna.project1;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by 4강의실 on 2016-07-14.
 */
public class WriteMemoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_memo);
        setTitle("Short Memo");


        //TextWatcher하면 TextView한줄 글자수 제한 할 수 있음
    }
}
