package com.app.hyuna.project1;


import android.app.TabActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by 4강의실 on 2016-07-14.
 */
@SuppressWarnings("deprecation")
public class ListActivity extends TabActivity{
    public static MemoVO mvo;
    ArrayList<HashMap<String,String>> memoList;

    AsyncTask<?,?,?> task;

    Button btnMemoNew,btnPostNew,btnDrawNew;
    Intent intent;
    String userId;
    ListView listView;
    CustomWidgetAdapter adapter;
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
        listView = (ListView)findViewById(R.id.listViewMemo);

        //메모버튼 클릭해서 들어왔을때는 메모List띄우기. Default는 memo
        if(listNum==MEMO)        tabHost.setCurrentTab(0);
        else if(listNum==DRAW)  tabHost.setCurrentTab(1);
        else if(listNum==POST)  tabHost.setCurrentTab(2);
        else if(listNum==SET)   tabHost.setCurrentTab(3);
        else                      tabHost.setCurrentTab(0);




        if(tabHost.getCurrentTab()==0) {//Memo일때
            //memo탭에 listview 보여주자
            //TODO tabMEMO
            adapter = new CustomWidgetAdapter(getApplicationContext());
            listView.setAdapter(adapter);
           // task = new ReadMemoTask().execute(userId);
        }
        btnMemoNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,WriteMemoActivity.class);
                intent.putExtra("userId",userId);
                startActivityForResult(intent,0);
            }
        });

        if(tabHost.getCurrentTab()==1) {
            //TODO tabDraw
        }
        btnDrawNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,WriteDrawActivity.class);
                //Intent intent = new Intent(ListActivity.this,CustomWidgetMemoListActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        if(tabHost.getCurrentTab()==2) {
            //TODO tabPost
        }
        btnPostNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this,WritePostActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        if(tabHost.getCurrentTab()==3) {
            //TODO tabSet
        }


        //TODO memo는 customListview 해서 title, memo 차례대로 보여주자
        //TODO 스크롤뷰 해서 전체db갯수 count한다음에 tablelayoutg. 3으로 나눈만큼 tablerow생성해서 Post,Draw 보여줌


    }

    private class ReadMemoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpPostData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<MemoVO>(){}.getType();
            List<MemoVO> lists = gson.fromJson(s,collectionType);

            memoList = new ArrayList<HashMap<String, String>>();
            int count=0;
            for(MemoVO vo : lists){
                HashMap<String,String> memoView = new HashMap<String, String>();
                memoView.put("title",vo.getTitle().toString());
                memoView.put("memo",vo.getMemo().toString());
                memoList.add(memoView);
                count++;
            }

            String tempTitle = "hi title1";
            String tempMemo = "hi Memo1";//바꾸야대
            //while()
            for(int i=0; i<count; i++) {
                CustomWidgetRow temp = new CustomWidgetRow(memoList.get(i).get("title"),memoList.get(i).get("memo"));
                adapter.add(temp);
            }
        }

        public String HttpPostData(String str){
            String myResult="";
            try{
                URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/read_memo.php");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setDefaultUseCaches(false);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestMethod("POST");

                http.setRequestProperty("content-type",
                        "application/x-www-form-urlencoded");
                StringBuffer buffer = new StringBuffer();
                buffer.append("id").append("=").append(str);

                OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(),"UTF-8");
                PrintWriter writer = new PrintWriter(outStream);
                writer.write(buffer.toString());
                writer.flush();

                InputStreamReader tmp = new InputStreamReader(
                        http.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String strResult;
                while ((strResult = reader.readLine()) != null) {
                    builder.append(strResult + "\n");
                }
                myResult = builder.toString();

                Log.d("result", "result : " + myResult);
            } catch (MalformedURLException e) {
                //
            } catch (IOException e) {
                //
            } // try
            return myResult;
        }
    }
}
