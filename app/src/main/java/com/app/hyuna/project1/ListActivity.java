package com.app.hyuna.project1;


import android.app.TabActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


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
    static  String noCheck="-1";


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        //menu.setHeaderIcon() //TODO
        menu.setHeaderTitle("삭제하시겠습니까?");
        menuInflater.inflate(R.menu.menu1,menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("noChec!!!",item.getItemId()+"");
        switch (item.getItemId()){
            case R.id.item1:
                if(!noCheck.equals(-1)){
                    Log.d("noChec!!!",noCheck);
                    task = new DeleteMemoTask().execute(noCheck);
                    Intent mIntent = new Intent(getApplicationContext(),ListActivity.class);
                    mIntent.putExtra("userId",userId);
                    mIntent.putExtra("list",MEMO);
                    startActivity(mIntent);
                    finish();
                }
                return true;
            case R.id.item2:
                return true;
        }

        return false;
    }

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

        btnMemoNew = (Button) findViewById(R.id.btnMemoNew);
        btnPostNew = (Button) findViewById(R.id.btnPostNew);
        btnDrawNew = (Button) findViewById(R.id.btnDrawNew);
        listView = (ListView) findViewById(R.id.listViewMemo);
        registerForContextMenu(listView);


        //메모버튼 클릭해서 들어왔을때는 메모List띄우기. Default는 memo
        if (listNum == MEMO) tabHost.setCurrentTab(0);
        else if (listNum == DRAW) tabHost.setCurrentTab(1);
        else if (listNum == POST) tabHost.setCurrentTab(2);
        else if (listNum == SET) tabHost.setCurrentTab(3);
        else tabHost.setCurrentTab(0);



        if (tabHost.getCurrentTab() == 0) {//Memo일때
            adapter = new CustomWidgetAdapter(getApplicationContext());
            listView.setAdapter(adapter);
            task = new ReadMemoTask().execute(userId);
        }
        btnMemoNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, WriteMemoActivity.class);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, 0);
            }
        });

        if (tabHost.getCurrentTab() == 1) {
            //TODO tabDraw
        }
        btnDrawNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, WriteDrawActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        if (tabHost.getCurrentTab() == 2) {
            //TODO tabPost
        }
        btnPostNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, WritePostActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        if (tabHost.getCurrentTab() == 3) {
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
//            Gson gson = new Gson();
//            Type collectionType = new TypeToken<MemoVO>(){}.getType();
//            List<MemoVO> lists = gson.fromJson(s,collectionType);

            try{
                JSONArray jo = new JSONArray(s);
                memoList = new ArrayList<HashMap<String, String>>();
                for(int i=0; i<jo.length(); i++){
                    HashMap<String,String> memoView = new HashMap<String, String>();
                    JSONObject jsonObject = jo.getJSONObject(i);
                    memoView.put("title",jsonObject.getString("title"));
                    memoView.put("memo",jsonObject.getString("memo"));
                    memoView.put("date",jsonObject.getString("date"));

                    memoView.put("no",jsonObject.getString("no"));
                    memoView.put("id",jsonObject.getString("id"));
                    memoList.add(memoView);
                }

            for(int i=0; i<jo.length(); i++) {
                CustomWidgetRow temp = new CustomWidgetRow(memoList.get(i).get("title"),memoList.get(i).get("memo"),memoList.get(i).get("date"));
                adapter.add(temp);
            }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(),ReadMemoActivity.class);
                        intent.putExtra("ReadCheck","1");
                        intent.putExtra("title",memoList.get(i).get("title").toString());
                        intent.putExtra("memo",memoList.get(i).get("memo").toString());
                        intent.putExtra("no",memoList.get(i).get("no").toString());
                        intent.putExtra("id",memoList.get(i).get("id").toString());
                        intent.putExtra("date",memoList.get(i).get("date").toString());
                        startActivity(intent);
                        finish();
                    }
                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.d("noCechk1!!!!!!!!!!!!!",noCheck);
                        noCheck = memoList.get(i).get("no").toString();
                        Log.d("noCechk2!!!!!!!!!!!!!",noCheck);
                        return false;
                    }
                });



            }catch (JSONException e){
                e.getStackTrace();
                Log.d("??????????","JSONException Error");
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
    private class DeleteMemoTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            HttpDeleteData(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"삭제가 완료되었습니다!",Toast.LENGTH_SHORT).show();
        }

        public String HttpDeleteData(String str){
            String myResult="";
            try{
                URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/delete_memo.php");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setDefaultUseCaches(false);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestMethod("POST");

                http.setRequestProperty("content-type",
                        "application/x-www-form-urlencoded");
                StringBuffer buffer = new StringBuffer();
                buffer.append("no").append("=").append(str);

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
