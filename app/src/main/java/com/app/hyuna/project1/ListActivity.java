package com.app.hyuna.project1;


import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
    ArrayList<HashMap<String,String>> memoList, memoListPost;

    AsyncTask<?,?,?> task,taskPost;
////////
    LinearLayout layoutDraw;
    Button btnMemoNew,btnPostNew,btnDrawNew, btnDrawPre, btnDrawNext;
    TextView edtDrawTitle, edtDrawDate;
    CustomListDrawView myPicture;
    File[] imageFiles;
    String imageFname;
    int drawNum;
///////이 블락 draw 애들

    String deleteContextCheck="";

    Intent intent;
    String userId;
    static String noCheck="-1";
    static String postCheck="-1";
    ListView listView,listViewPost;
    CustomWidgetAdapter adapter, adapterPost;

    TextView logout;

    int listNum;
    final int MEMO=0,DRAW=1,POST=2,SET=3;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menu.setHeaderIcon(R.drawable.mnpd);
        menu.setHeaderTitle("삭제하시겠습니까?");
        menuInflater.inflate(R.menu.menu1,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                if(deleteContextCheck.equals("memo")) {//Memo 롱클릭 삭제
                    if (!noCheck.equals(-1)) {
                        task = new DeleteMemoTask().execute(noCheck);
                        Intent mIntent = new Intent(getApplicationContext(), ListActivity.class);
                        mIntent.putExtra("userId", userId);
                        mIntent.putExtra("list", MEMO);
                        startActivity(mIntent);
                        finish();
                    }
                }else if(deleteContextCheck.equals("draw")){ //Draw 롱클릭 삭제
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    int count = imageFname.lastIndexOf("/");
                    String temp  = imageFname.substring(count);
                    File file = new File(path+"/drawNote"+temp);
                    file.delete();
                    Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                    intent.putExtra("userId",userId);
                    intent.putExtra("list",DRAW);
                    startActivity(intent);
                    finish();
                }else if(deleteContextCheck.equals("post")){//Post 롱클릭 삭제
                    if (!postCheck.equals(-1)) {
                        task = new DeletePostTask().execute(postCheck);
                        Intent mIntent = new Intent(getApplicationContext(), ListActivity.class);
                        mIntent.putExtra("userId", userId);
                        mIntent.putExtra("list", POST);
                        startActivity(mIntent);
                        finish();
                    }
                }

                return true;
            case R.id.item2:    //취소버튼
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
        final TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpecMemo = tabHost.newTabSpec("Memo").setIndicator("MEMO").setContent(R.id.tabMemo);
        tabHost.addTab(tabSpecMemo);

        TabHost.TabSpec tabSpecDraw = tabHost.newTabSpec("Draw").setIndicator("DRAW").setContent(R.id.tabDraw);
        tabHost.addTab(tabSpecDraw);

        TabHost.TabSpec tabSpecPost = tabHost.newTabSpec("Post").setIndicator("POST").setContent(R.id.tabPost);
        tabHost.addTab(tabSpecPost);

        TabHost.TabSpec tabSpecSet = tabHost.newTabSpec("Set").setIndicator("SET").setContent(R.id.tabSet);
        tabHost.addTab(tabSpecSet);
        //탭호스트 상단메뉴바 만들기 끝

        //Memo 애들 선언
        btnMemoNew = (Button) findViewById(R.id.btnMemoNew);
        listView = (ListView) findViewById(R.id.listViewMemo);
        adapter = new CustomWidgetAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        task = new ReadMemoTask().execute(userId);

        //draw애들 선언
        btnDrawNew = (Button) findViewById(R.id.btnDrawNew);
        btnDrawPre = (Button)findViewById(R.id.btnDrawPre);
        btnDrawNext = (Button)findViewById(R.id.btnDrawNext);
        edtDrawTitle = (TextView)findViewById(R.id.edtDrawTitle);
        edtDrawDate = (TextView)findViewById(R.id.edtDrawDate);
        myPicture = (CustomListDrawView)findViewById(R.id.myPictureView1);
        layoutDraw = (LinearLayout)findViewById(R.id.layoutDraw);//CustomView 롱클릭 하려고

        //post 선언
        btnPostNew = (Button) findViewById(R.id.btnPostNew);
        listViewPost = (ListView) findViewById(R.id.listViewPost);
        adapterPost = new CustomWidgetAdapter(getApplicationContext());
        listViewPost.setAdapter(adapterPost);
        taskPost = new ReadPostTask().execute(userId);

        //Set 선언
        logout = (TextView)findViewById(R.id.logout);


        //메모버튼 클릭해서 들어왔을때는 메모List띄우기. Default는 memo
        if (listNum == MEMO) tabHost.setCurrentTab(0);
        else if (listNum == DRAW) tabHost.setCurrentTab(1);
        else if (listNum == POST) tabHost.setCurrentTab(2);
        else if (listNum == SET) tabHost.setCurrentTab(3);
        else tabHost.setCurrentTab(0);


//        tabHost

        ///Memo일때
        if (tabHost.getCurrentTab() == 0) {
            unregisterForContextMenu(layoutDraw);
            unregisterForContextMenu(listViewPost);
            registerForContextMenu(listView);
            deleteContextCheck="memo";
            //task = new ReadMemoTask().execute(userId);
        }
        btnMemoNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, WriteMemoActivity.class);
                intent.putExtra("userId", userId);
                //startActivityForResult(intent, 0);
                startActivity(intent);
                finish();
            }
        });



        //Draw일 떄
        if (tabHost.getCurrentTab() == 1) {
            unregisterForContextMenu(listView);
            unregisterForContextMenu(listViewPost);
            registerForContextMenu(layoutDraw);
            deleteContextCheck="draw";

            imageFiles = new File("/sdcard/drawNote").listFiles();
            imageFname = imageFiles[0].toString();
            myPicture.imagePath = imageFname;
            int indexCount = imageFname.lastIndexOf("/");
            String temp = imageFname.substring(indexCount+1);
            String[] temp1 = temp.split("_");
            String tempDate = temp1[2];
            tempDate = "20"+tempDate.substring(0,2)+"-"+tempDate.substring(2,4)+"-"+tempDate.substring(4,6)+" "+tempDate.substring(6,8)+":"+tempDate.substring(8,10);

            edtDrawTitle.setText(temp1[1]);
            edtDrawDate.setText(tempDate);
        }
        btnDrawNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, WriteDrawActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        });
        btnDrawPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawNum<=0){
                    Toast.makeText(getApplicationContext(),"첫번째 입니다.",Toast.LENGTH_SHORT).show();
                }else{
                    drawNum--;
                    imageFname = imageFiles[drawNum].toString();
                    myPicture.imagePath = imageFname;
                    myPicture.invalidate();
                    int indexCount = imageFname.lastIndexOf("/");
                    String temp = imageFname.substring(indexCount+1);
                    String[] temp1 = temp.split("_");
                    String tempDate = temp1[2];
                    tempDate = "20"+tempDate.substring(0,2)+"-"+tempDate.substring(2,4)+"-"+tempDate.substring(4,6)+" "+tempDate.substring(6,8)+":"+tempDate.substring(8,10);
                    edtDrawTitle.setText(temp1[1]);
                    edtDrawDate.setText(tempDate);
                }
            }
        });
        btnDrawNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawNum>=imageFiles.length-1){
                    Toast.makeText(getApplicationContext(),"마지막 입니다.",Toast.LENGTH_SHORT).show();
                }else{
                    drawNum++;
                    imageFname = imageFiles[drawNum].toString();
                    myPicture.imagePath = imageFname;
                    myPicture.invalidate();
                    int indexCount = imageFname.lastIndexOf("/");
                    String temp = imageFname.substring(indexCount+1);
                    String[] temp1 = temp.split("_");
                    String tempDate = temp1[2];
                    tempDate = "20"+tempDate.substring(0,2)+"-"+tempDate.substring(2,4)+"-"+tempDate.substring(4,6)+" "+tempDate.substring(6,8)+":"+tempDate.substring(8,10);
                    edtDrawTitle.setText(temp1[1]);
                    edtDrawDate.setText(tempDate);
                }
            }
        });



        //POST
        if (tabHost.getCurrentTab() == 2) {
            unregisterForContextMenu(listView);
            unregisterForContextMenu(layoutDraw);
            registerForContextMenu(listViewPost);
            deleteContextCheck="post";
            //taskPost = new ReadPostTask().execute(userId);
        }
        btnPostNew.setOnClickListener(new View.OnClickListener() {// + 버튼누를때 새창 띄워서 새 메모 하기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, WritePostActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        });


        //Set
        if (tabHost.getCurrentTab() == 3) {
            unregisterForContextMenu(listView);
            unregisterForContextMenu(layoutDraw);
            unregisterForContextMenu(listViewPost);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outFs = openFileOutput("id.txt", Context.MODE_WORLD_WRITEABLE);
                    String str = "!!default!!"+"////////////////////";
                    outFs.write(str.getBytes());
                    outFs.close();

                }catch (IOException e){
                    e.getStackTrace();
                }

                Intent intent = new Intent(ListActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(s.equals("Memo")){
                    unregisterForContextMenu(listViewPost);
                    unregisterForContextMenu(layoutDraw);
                    registerForContextMenu(listView);
                    deleteContextCheck="memo";

                }else if(s.equals("Draw")){
                    unregisterForContextMenu(listViewPost);
                    unregisterForContextMenu(listView);
                    registerForContextMenu(layoutDraw);
                    deleteContextCheck="draw";

                    imageFiles = new File("/sdcard/drawNote").listFiles();
                    imageFname = imageFiles[0].toString();
                    myPicture.imagePath = imageFname;
                    int indexCount = imageFname.lastIndexOf("/");
                    String temp = imageFname.substring(indexCount+1);
                    String[] temp1 = temp.split("_");
                    String tempDate = temp1[2];
                    tempDate = "20"+tempDate.substring(0,2)+"-"+tempDate.substring(2,4)+"-"+tempDate.substring(4,6)+" "+tempDate.substring(6,8)+":"+tempDate.substring(8,10);

                    edtDrawTitle.setText(temp1[1]);
                    edtDrawDate.setText(tempDate);

                }else if(s.equals("Post")){
                    unregisterForContextMenu(listView);
                    unregisterForContextMenu(layoutDraw);
                    registerForContextMenu(listViewPost);
                    deleteContextCheck="post";
                    taskPost = new ReadPostTask().execute(userId);
                }else if(s.equals("Set")){
                    unregisterForContextMenu(listView);
                    unregisterForContextMenu(layoutDraw);
                    unregisterForContextMenu(listViewPost);
                }
            }
        });


    }

    private class ReadMemoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpPost1Data(strings[0]);
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
                    adapter.notifyDataSetChanged();
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
                        //finish();
                    }
                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        noCheck = memoList.get(i).get("no").toString();
                        adapter.notifyDataSetChanged();
                        return false;
                    }
                });



            }catch (JSONException e){
                e.getStackTrace();
                Log.d("??????????","JSONException Error(Read Memo Task PostExecute)");
            }
        }

        public String HttpPost1Data(String str){
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
                Log.d("??????????","JSONException Error(Read Memo Task)");
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
                Log.d("??????????","JSONException Error(Delete Memo Task)");
                //
            } // try
            return myResult;
        }
    }
///////////////////////////////////////////////////////////////////////////////POST task
    private class ReadPostTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpPostData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            try{
                JSONArray jo = new JSONArray(s);
                memoListPost = new ArrayList<HashMap<String, String>>();
                for(int i=0; i<jo.length(); i++){
                    HashMap<String,String> memoView = new HashMap<String, String>();
                    JSONObject jsonObject = jo.getJSONObject(i);
                    memoView.put("title",jsonObject.getString("title"));
                    memoView.put("memo",jsonObject.getString("memo"));
                    memoView.put("date",jsonObject.getString("date"));

                    memoView.put("img",jsonObject.getString("img"));
                    memoView.put("no",jsonObject.getString("no"));
                    memoView.put("id",jsonObject.getString("id"));

                    memoListPost.add(memoView);
                }

                for(int i=0; i<jo.length(); i++) {//listView에 보여줄 용도
                    CustomWidgetRow temp = new CustomWidgetRow(memoListPost.get(i).get("title"),memoListPost.get(i).get("memo"),memoListPost.get(i).get("date"));
                    adapterPost.add(temp);
                    adapterPost.notifyDataSetChanged();
                }
                listViewPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(),ReadPostActivity.class);
                        intent.putExtra("ReadCheck","1");
                        intent.putExtra("title",memoListPost.get(i).get("title").toString());
                        intent.putExtra("memo",memoListPost.get(i).get("memo").toString());
                        intent.putExtra("no",memoListPost.get(i).get("no").toString());
                        intent.putExtra("id",memoListPost.get(i).get("id").toString());
                        intent.putExtra("date",memoListPost.get(i).get("date").toString());
                        intent.putExtra("img",memoListPost.get(i).get("img").toString());
                        startActivity(intent);
                        //finish();
                    }
                });

                listViewPost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        postCheck = memoListPost.get(i).get("no").toString();
                        adapterPost.notifyDataSetChanged();
                        return false;
                    }
                });



            }catch (JSONException e){
                e.getStackTrace();
                Log.d("??????????","JSONException Error(Read Post Task)");
            }
        }

        public String HttpPostData(String str){
            String myResult="";
            try{
                URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/read_post.php");
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
    private class DeletePostTask extends AsyncTask<String, Void, Void>{
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
                URL url = new URL("http://hyunazi.dothome.co.kr/AndDiary/delete_post.php");
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
                Log.d("??????????","JSONException Error(Delete Post Task)");
            } // try
            return myResult;
        }
    }
}
