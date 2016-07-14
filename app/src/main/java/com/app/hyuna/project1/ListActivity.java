package com.app.hyuna.project1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * Created by 4강의실 on 2016-07-14.
 */
@SuppressWarnings("deprecation")
public class ListActivity extends TabActivity{
    Button btnMemoNew,btnPostNew,btnDrawNew;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpecMemo = tabHost.newTabSpec("Memo").setIndicator("MEMO").setContent(R.id.tabMemo);
        tabHost.addTab(tabSpecMemo);

        TabHost.TabSpec tabSpecDraw = tabHost.newTabSpec("Draw").setIndicator("DRAW").setContent(R.id.tabDraw);
        tabHost.addTab(tabSpecDraw);

        TabHost.TabSpec tabSpecPost = tabHost.newTabSpec("Post").setIndicator("POST").setContent(R.id.tabPost);
        tabHost.addTab(tabSpecPost);

        TabHost.TabSpec tabSpecSet = tabHost.newTabSpec("Set").setIndicator("SETTING").setContent(R.id.tabSet);
        tabHost.addTab(tabSpecSet);

        tabHost.setCurrentTab(0);//탭호스트 상단메뉴바 만들기 끝

        btnMemoNew = (Button)findViewById(R.id.btnMemoNew);
        btnPostNew = (Button)findViewById(R.id.btnPostNew);
        btnDrawNew = (Button)findViewById(R.id.btnDrawNew);


        //TODO tabMEMO
        /*getTabHost().getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        btnMemoNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,WriteMemoActivity.class);
                //intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });


        //TODO tabDraw

        //TODO tabPost

        //TODO tabSet

    }
}
