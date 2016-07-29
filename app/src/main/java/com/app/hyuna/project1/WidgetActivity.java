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
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context mContext;
        mContext = getApplicationContext();
        Intent intent = new Intent(getApplicationContext(),CustomWidgetMemoListActivity.class);
        startActivity(intent);
        finish();
    }
}
