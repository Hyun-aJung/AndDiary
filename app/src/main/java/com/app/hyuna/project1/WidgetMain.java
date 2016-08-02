package com.app.hyuna.project1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class WidgetMain extends AppWidgetProvider {
    public static String PENDING_ACTION="com.app.hyuna.project1";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        for(int i=0; i<N; i++){
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildViews(context);
            this.refresh(context,views);
            //appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.partiallyUpdateAppWidget(appWidgetIds[i],views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        //위젯 업데이트를 수신했을 떄
        if(action.equals("android.appwidget.action.APPWIDGET_UPDATE")){
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            onUpdate(context,manager,manager.getAppWidgetIds(new ComponentName(context,getClass())));
        }
    }

    private void refresh(Context context, RemoteViews remoteViews) {
        SharedPreferences sp = context.getSharedPreferences("sp", Context.MODE_WORLD_WRITEABLE);
        remoteViews.setTextViewText(R.id.widgetTitle, sp.getString("ListGetTitle", "Login First AND"));
        remoteViews.setTextViewText(R.id.widgetMemo, sp.getString("ListGetMemo","Choose What you want!♥3♥"));
    }

    //static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

   // }//새로 만들었어
    private PendingIntent buildActivityIntent(Context context){
        Intent intent = new Intent(context, WidgetActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,intent,0);
        return  pi;
    }

    private RemoteViews buildViews(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.activity_widget_layout);
        views.setOnClickPendingIntent(R.id.widgetLayout,buildActivityIntent(context));
        return views;

    }

}
