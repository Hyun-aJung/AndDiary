package com.app.hyuna.project1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class WidgetMain extends AppWidgetProvider {
    public static String PENDING_ACTION="com.app.hyuna.project1";
   /* @Override원래코드임 고치지시도즁
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        for(int i=0; i<N; i++){
            //TODO 위젯 생성 전, 메모 리스트창 뜨고 선택하면 그 내용으로 위젯 만들어짐
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildViews(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            //appWidgetManager.partiallyUpdateAppWidget(appWidgetId, views);


            //views.setTextViewText(R.id.widgetTitle,"title");
            //views.setTextViewText(R.id.widgetMemo,"memo");

        }
    }*/

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        for(int i=0; i<N; i++){
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildViews(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.activity_widget_layout);
        String action  = intent.getAction();
        if(action.equals(PENDING_ACTION)){
            rv.setTextViewText(R.id.widgetTitle,String.valueOf(intent.getStringExtra("tempTitle")));
            rv.setTextViewText(R.id.widgetMemo,String.valueOf(intent.getStringExtra("tempMemo")));
        }
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
