package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 4강의실 on 2016-07-11.
 */
public class LoginActivity extends Activity {
    Button btJoin,btLog;
    EditText userId,userPw;
    String id, pw;
    boolean loginOk = true;//TODO 원래는 false여야
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btJoin = (Button)findViewById(R.id.btJoin);
        btLog = (Button)findViewById(R.id.btLogin);
        userId = (EditText)findViewById(R.id.txtId);
        userPw = (EditText)findViewById(R.id.txtPw);
        id="ha";//TODO 임시 테스트용

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
                //TODO 회원정보 확인
                if(loginOk) {//TODO 회원정보 일치하면 -> true부분바꿔야
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userId",id);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 틀려요!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
