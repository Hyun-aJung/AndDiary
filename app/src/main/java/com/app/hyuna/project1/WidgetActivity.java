package com.app.hyuna.project1;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class WidgetActivity extends Activity{
    private static final String CHECK_PLAY_PACKAGE_NAME = "com.app.hyuna.project1";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    String title, memo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_widget_layout);
        Context mContext;
        mContext = getApplicationContext();
        Intent intent = new Intent(getApplicationContext(),CustomWidgetMemoListActivity.class);
        startActivity(intent);
        finish();
        //startActivityForResult(intent,0);

//        this.finish();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            title = data.getStringExtra("tempTitle");
            memo = data.getStringExtra("tempMemo");
            TextView widgetTitle = (TextView)findViewById(R.id.widgetTitle);
            widgetTitle.setText(title);
            TextView widgetMemo = (TextView)findViewById(R.id.widgetMemo);
            widgetMemo.setText(memo);
            this.finish();

        }
    }*/


}
