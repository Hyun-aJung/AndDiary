package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by 4강의실 on 2016-07-11.
 */
public class JoinActivity extends Activity {
    EditText txtId,txtMail,txtName,txtPw1,txtPw2;
    Button btJoin,btCancel,btCheck;
    String p1,p2,mail;
    boolean mailCheck=false, nameCheck=false, pwCheck=false, idCheck=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        setTitle("Join");
        btJoin = (Button)findViewById(R.id.btnJoin);
        btCancel = (Button)findViewById(R.id.btnCancel);
        btCheck = (Button)findViewById(R.id.btnCheck);

        txtId = (EditText)findViewById(R.id.txtId);
        txtPw1 = (EditText)findViewById(R.id.txtPw1);
        txtPw2 = (EditText)findViewById(R.id.txtPw2);
        txtName = (EditText)findViewById(R.id.txtName);
        txtMail = (EditText)findViewById(R.id.txtMail);

        btJoin.setEnabled(false);//아직 칸이 모두 안채워졌으니 false

        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO 아이디 중복확인(primary Key)
                //if ID 유일하다면 idCheck=true;
            }
        });

        txtPw2.setOnFocusChangeListener(new View.OnFocusChangeListener() { //비밀번호 확인 check
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!txtPw2.hasFocus()) {
                    p1 = txtPw1.getText().toString();
                    p2 = txtPw2.getText().toString();
                    if (p1.equals(p2)){
                        btJoin.setEnabled(true);
                        pwCheck = true;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"비밀번호가 틀려요!",Toast.LENGTH_SHORT).show();
                        txtPw2.setText("");
                        txtPw2.isFocused();
                    }
                }
            }
        });


        txtMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {//Mail 형식 check
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!txtMail.hasFocus()){
                    mail = txtMail.getText().toString();
                    mailCheck = mail.contains("@"); //mail에 @이 포함되있는가

                    if(mailCheck==false){
                        Toast.makeText(getApplicationContext(),"이메일 형식이 올바르지 않습니다.",Toast.LENGTH_SHORT).show();
                        txtMail.isFocused();
                    }
                }
            }
        });

        if(pwCheck && nameCheck && idCheck && mailCheck){ //모든칸 잘 되었으면 Join버튼 클릭 가능(회원가입 가능)
            btJoin.setEnabled(true);
        }

        btJoin.setOnClickListener(new View.OnClickListener() {//회원가입 버튼 눌렀을 때
            @Override
            public void onClick(View view) {
                //TODO 회원가입 <DB처리>
                Intent intent =  new Intent(JoinActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
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
    class MemberJoinJson extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String jsonData="";
            HttpURLConnection urlConnection = null;
            try{
                URL url = new URL(URLSetting.url);
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.connect();
                //HttpGet

            }catch (MalformedURLException e){
                e.getStackTrace();
            }catch (IOException e){
                e.getStackTrace();
            }catch (Exception e){
                e.getStackTrace();
            }
            /*try{
                DefaultHttpClient httpClient = new DefaultHttpClient();
            }catch(UnsupportedEncodingException e){
                e.getStackTrace();
            }catch (IOException e){
                e.getStackTrace();
            }*/
            return jsonData;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}
