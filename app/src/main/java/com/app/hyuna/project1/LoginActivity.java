package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by 4강의실 on 2016-07-11.
 */
public class LoginActivity extends Activity {
    Button btJoin,btLog;
    EditText userId,userPw;
    String id, pw;
    boolean loginOk = false;
    AsyncTask<?,?,?> LoginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btJoin = (Button)findViewById(R.id.btJoin);
        btLog = (Button)findViewById(R.id.btLogin);
        userId = (EditText)findViewById(R.id.txtId);
        userPw = (EditText)findViewById(R.id.txtPw);

        btJoin.setOnClickListener(new View.OnClickListener() { //Join버튼 누르면 회원가입창 띄우기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
        btLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = userId.getText().toString();
                pw = userPw.getText().toString();

                LoginTask = new LoginTask().execute(id,pw);
                if(loginOk) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    SaveMember save = new SaveMember(id);
                    save.saveMemberJson();
                    intent.putExtra("userId",id);//TODO 없애
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 틀려요!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class LoginTask extends AsyncTask<String, Void, Integer>{
        @Override
        protected Integer doInBackground(String... strings) {
            return HttpPostData(strings[0],strings[1]);
        }

        @Override
        protected void onPostExecute(Integer str) {
            if(str>0){
                Toast.makeText(getApplicationContext(),"로그인 성공!",Toast.LENGTH_SHORT).show();
                loginOk=true;
            }
        }
    }

    public Integer HttpPostData(String syncId, String syncPw){
        Integer myResult=0;
        try {
            URL url = new URL(
                    "http://hyunazi.dothome.co.kr/AndDiary/login.php");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            http.setRequestProperty("content-type",
                    "application/x-www-form-urlencoded");
            StringBuffer buffer = new StringBuffer();
            buffer.append("id").append("=").append(syncId).append("&");
            buffer.append("pw").append("=").append(syncPw);

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            InputStreamReader tmp = new InputStreamReader(
                    http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str);
            }
            myResult = Integer.parseInt(builder.toString());

            Log.d("result", "result : " + myResult);
        } catch (MalformedURLException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        } // try
        return myResult;
    } // HttpPostData
}
