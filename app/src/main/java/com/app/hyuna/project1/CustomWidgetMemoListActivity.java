package com.app.hyuna.project1;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class CustomWidgetMemoListActivity extends Activity{
    private ListView listView;
    private CustomWidgetAdapter adapter;
    String tempTitle,tempMemo, tempDate,widgetTitle, widgetMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_list);

        listView = (ListView)findViewById(R.id.listView2);
        adapter = new CustomWidgetAdapter(getApplicationContext());//, R.layout.activity_widget_list_item);
        listView.setAdapter(adapter);
        //TODO DB에서 메모 읽어오기
        tempTitle = "hi title1";
        tempMemo = "hi Memo1";
        tempDate = "111";
        CustomWidgetRow temp = new CustomWidgetRow(tempTitle,tempMemo,tempDate);
        adapter.add(temp);

        tempTitle = "오늘은";
        tempMemo = "뇽이부기랑 데이트하는날~♥3 ♥";
        temp = new CustomWidgetRow(tempTitle,tempMemo,tempDate);
        adapter.add(temp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //TODO 위젯 ㄱㄱ으로 연동
                SharedPreferences sp = getSharedPreferences("sp", Context.MODE_WORLD_WRITEABLE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("ListGetTitle",adapter.getItem(position).getTitle());
                editor.putString("ListGetMemo",adapter.getItem(position).getMemo());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(),WidgetMain.class);
                intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                sendBroadcast(intent);
                finish();
            }
        });
    }
}
