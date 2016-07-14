package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 4강의실 on 2016-07-11.
 */
public class JoinActivity extends Activity {
    EditText txtId,txtMail,txtName,txtPw1,txtPw2;
    Button btJoin,btCancel,btCheck;
    String p1,p2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        btJoin = (Button)findViewById(R.id.btnJoin);
        btCancel = (Button)findViewById(R.id.btnCancel);
        btCheck = (Button)findViewById(R.id.btnCheck);

        txtId = (EditText)findViewById(R.id.txtId);
        txtPw1 = (EditText)findViewById(R.id.txtPw1);
        txtPw2 = (EditText)findViewById(R.id.txtPw2);
        txtName = (EditText)findViewById(R.id.txtName);
        txtMail = (EditText)findViewById(R.id.txtMail);

        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 아이디 중복확인
            }
        });

        txtPw2.setOnFocusChangeListener(new View.OnFocusChangeListener() { //비밀번호 확인 check
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!txtPw2.hasFocus()){
                    p1 = txtPw1.getText().toString();
                    p2 = txtPw2.getText().toString();
                    if(p1.equals(p2))
                        btJoin.setEnabled(true);
                    else{
                        btJoin.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"비밀번호가 틀려요!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 회원가입
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() { //cancle버튼 누르면 로그인 화면으로
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
