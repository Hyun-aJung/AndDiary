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
        setContentView(R.layout.activity_widget_layout);
        Context mContext;
        mContext = getApplicationContext();
        Log.d("????????","hi");
        Intent intent = new Intent(getApplicationContext(),CustomWidgetMemoListActivity.class);
        startActivityForResult(intent,0);

//        this.finish();
    }

    @Override
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
    }

   /* @Override//강사님 코드
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_layout);
        Context mContext;
        mContext = getApplicationContext();
        Intent intent;
        try{
            PackageManager pm = getPackageManager();
            pm.getPackageInfo(CHECK_PLAY_PACKAGE_NAME.trim(),PackageManager.GET_META_DATA);
            intent = mContext.getPackageManager().getLaunchIntentForPackage("com.app.hyuna.project1");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//setFlages
        }catch (PackageManager.NameNotFoundException e){
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps"));//https://play.google.com/store/apps/details?id=kr.co.pooq.android.tablet.v2"
            intent.setPackage("com.android.vending");//이건 뭔의미고?
        }
        startActivity(intent);
        this.finish();
    }*/

}
