package com.app.hyuna.project1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by 4강의실 on 2016-07-14.
 */
@SuppressWarnings("deprecation")
public class ListActivity extends TabActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpecMemo = tabHost.newTabSpec("Memo").setIndicator("MEMO");
        tabSpecMemo.setContent(R.id.tabMemo);
        tabHost.addTab(tabSpecMemo);

        TabHost.TabSpec tabSpecDraw = tabHost.newTabSpec("Draw").setIndicator("DRAW");
        tabSpecDraw.setContent(R.id.tabDraw);
        tabHost.addTab(tabSpecDraw);

        TabHost.TabSpec tabSpecPost = tabHost.newTabSpec("Post").setIndicator("POST");
        tabSpecPost.setContent(R.id.tabPost);
        tabHost.addTab(tabSpecPost);

        TabHost.TabSpec tabSpecSet = tabHost.newTabSpec("Set").setIndicator("SETTING");
        tabSpecSet.setContent(R.id.tabSet);
        tabHost.addTab(tabSpecSet);

        tabHost.setCurrentTab(0);

    }
}
