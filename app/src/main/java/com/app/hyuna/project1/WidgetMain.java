package com.app.hyuna.project1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class WidgetMain extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        for(int i=0; i<N; i++){
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildViews(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private PendingIntent buildActivityIntent(Context context){
        Intent intent = new Intent(context, WidgetActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,intent,0);
        return  pi;
    }

    private RemoteViews buildViews(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.activity_widget_layout);
        views.setOnClickPendingIntent(R.id.widgetMemo,buildActivityIntent(context));
        return views;

    }

}
