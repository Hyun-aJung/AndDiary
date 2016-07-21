package com.app.hyuna.project1;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class WidgetActivity extends Activity{
    private static final String CHECK_PLAY_PACKAGE_NAME = "com.app.hyuna.project1";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    /*@Override //위젯 누르면 같은내용창이 앱위에 뜬다
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_layout);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras !=null){
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        LinearLayout layout = (LinearLayout)findViewById(R.id.widgetLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = WidgetActivity.this;
                AppWidgetManager widgetMgr = AppWidgetManager.getInstance(context);
                WidgetMain.updateWidget(context, widgetMgr, mAppWidgetId);
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
                setResult(RESULT_OK,resultValue);
                finish();
            }
        });
    }
    */

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_layout);
        Context mContext;
        mContext = getApplicationContext();
        Intent intent = new Intent(getApplicationContext(),CustomWidgetMemoListActivity.class);
        /*try{
            PackageManager pm = getPackageManager();
            pm.getPackageInfo(CHECK_PLAY_PACKAGE_NAME.trim(),PackageManager.GET_META_DATA);
            intent = mContext.getPackageManager().getLaunchIntentForPackage("com.app.hyuna.project1");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//setFlages
        }catch (PackageManager.NameNotFoundException e){
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps"));//https://play.google.com/store/apps/details?id=kr.co.pooq.android.tablet.v2"
            intent.setPackage("com.android.vending");//이건 뭔의미고?
        }*/
        startActivity(intent);
        this.finish();
    }
}
