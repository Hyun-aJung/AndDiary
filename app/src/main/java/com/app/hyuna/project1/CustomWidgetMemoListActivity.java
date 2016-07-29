package com.app.hyuna.project1;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
 * Created by 4강의실 on 2016-07-20.
 */
public class CustomWidgetMemoListActivity extends Activity{
    private ListView listView;
    private CustomWidgetAdapter adapter;
    String tempTitle,tempMemo, tempDate;
    ArrayList<HashMap<String,String>> memoList;
    AsyncTask<?,?,?> task;
    static String USERID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_list);

        listView = (ListView)findViewById(R.id.listView2);
        adapter = new CustomWidgetAdapter(getApplicationContext());//, R.layout.activity_widget_list_item);
        listView.setAdapter(adapter);
        task = new ReadMemoTask().execute(USERID);
    }

    private class ReadMemoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpPostData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
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

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//바탕화면 위젯으로연동
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        SharedPreferences sp = getSharedPreferences("sp", Context.MODE_WORLD_WRITEABLE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("ListGetTitle",memoList.get(position).get("title").toString());
                        editor.putString("ListGetMemo",memoList.get(position).get("memo").toString());
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(),WidgetMain.class);
                        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                        sendBroadcast(intent);
                        finish();
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
}
