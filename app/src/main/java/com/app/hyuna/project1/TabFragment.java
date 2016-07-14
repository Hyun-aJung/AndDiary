package com.app.hyuna.project1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by 4강의실 on 2016-07-14.
 */
public class TabFragment extends android.app.Fragment {
    String tabName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        tabName = data.getString("tabName");
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout baseLayout = new LinearLayout(ListActivity.this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setLayoutParams(params);

        if(tabName.equals("Memo")){}
        if(tabName.equals("Draw")){}
        if(tabName.equals("Set")){}
        if(tabName.equals("Post")){}
        return baseLayout;
    }*/
}
