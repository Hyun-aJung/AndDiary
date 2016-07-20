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
    Intent intent;
    String userId;
    int listNum;
    final int MEMO=0,DRAW=1,POST=2,SET=3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("Memo List");

        intent = getIntent();
        userId = intent.getExtras().getString("userId");
        listNum = intent.getExtras().getInt("list");//intent로 보낸 데이터 받기

        //Tab메뉴 만들기
        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpecMemo = tabHost.newTabSpec("Memo").setIndicator("MEMO").setContent(R.id.tabMemo);
        tabHost.addTab(tabSpecMemo);

        TabHost.TabSpec tabSpecDraw = tabHost.newTabSpec("Draw").setIndicator("DRAW").setContent(R.id.tabDraw);
        tabHost.addTab(tabSpecDraw);

        TabHost.TabSpec tabSpecPost = tabHost.newTabSpec("Post").setIndicator("POST").setContent(R.id.tabPost);
        tabHost.addTab(tabSpecPost);

        TabHost.TabSpec tabSpecSet = tabHost.newTabSpec("Set").setIndicator("SETTING").setContent(R.id.tabSet);
        tabHost.addTab(tabSpecSet);
        //탭호스트 상단메뉴바 만들기 끝

        btnMemoNew = (Button)findViewById(R.id.btnMemoNew);
        btnPostNew = (Button)findViewById(R.id.btnPostNew);
        btnDrawNew = (Button)findViewById(R.id.btnDrawNew);

        //메모버튼 클릭해서 들어왔을때는 메모List띄우기. Default는 memo
        if(listNum==MEMO)        tabHost.setCurrentTab(0);
        else if(listNum==DRAW)  tabHost.setCurrentTab(1);
        else if(listNum==POST)  tabHost.setCurrentTab(2);
        else if(listNum==SET)   tabHost.setCurrentTab(3);
        else                      tabHost.setCurrentTab(0);



        //TODO tabMEMO
        btnMemoNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,WriteMemoActivity.class);
                intent.putExtra("userId",userId);
                startActivityForResult(intent,0);
            }
        });


        //TODO tabDraw
        btnDrawNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,WriteDrawActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        //TODO tabPost
        btnPostNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,WritePostActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        //TODO tabSet


        //TODO memo는 customListview 해서 title, memo 차례대로 보여주자
        //TODO 스크롤뷰 해서 전체db갯수 count한다음에 tablelayoutg. 3으로 나눈만큼 tablerow생성해서 Post,Draw 보여줌


    }
}
