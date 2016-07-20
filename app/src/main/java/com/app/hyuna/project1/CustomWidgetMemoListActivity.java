package com.app.hyuna.project1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class CustomWidgetMemoListActivity extends Activity{
    ListView listView;
    CustomWidgetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_list);

        listView = (ListView)findViewById(R.id.listView2);
        adapter = new CustomWidgetAdapter(getApplicationContext(), R.layout.activity_widget_list_item);
        listView.setAdapter(adapter);
        //TODO DB에서 메모 읽어오기
        String tempTitle = "hi title1";
        String tempMemo = "hi Memo1";
        CustomWidgetRow temp = new CustomWidgetRow(tempTitle,tempMemo);
        adapter.add(temp);

        tempTitle = "hi title2";
        tempMemo = "hi Memo2";
        temp = new CustomWidgetRow(tempTitle,tempMemo);
        adapter.add(temp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //TODO 위젯 ㄱㄱ으로 연동
                //adapter.getItem(position).getText()
            }
        });

    }
}
