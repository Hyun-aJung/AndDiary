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
public class JoinActivity extends Activity {
    EditText txtId, txtMail, txtName, txtPw1, txtPw2;
    Button btJoin, btCancel, btCheck;
    String p1, p2, email,id,pw,name,mail;
    boolean mailCheck = false, nameCheck = false, pwCheck = false, idCheck = false;
    AsyncTask<?, ?, ?> joinTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        setTitle("Join");
        btJoin = (Button) findViewById(R.id.btnJoin);
        btCancel = (Button) findViewById(R.id.btnCancel);
        btCheck = (Button) findViewById(R.id.btnCheck);

        txtId = (EditText) findViewById(R.id.txtId);
        txtPw1 = (EditText) findViewById(R.id.txtPw1);
        txtPw2 = (EditText) findViewById(R.id.txtPw2);
        txtName = (EditText) findViewById(R.id.txtName);
        txtMail = (EditText) findViewById(R.id.txtMail);

        btJoin.setEnabled(false);//아직 칸이 모두 안채워졌으니 false
//        if() {
//            if (pwCheck && nameCheck && idCheck && mailCheck) { //모든칸 잘 되었으면 Join버튼 클릭 가능(회원가입 가능)
//                btJoin.setEnabled(true);
//            } else btJoin.setEnabled(false);
//        }

        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                //TODO 아이디 중복확인(primary Key)
                //if ID 유일하다면 idCheck=true;
                if (count <= 0) {
                    idCheck = true;
                    Toast.makeText(getApplicationContext(), "사용 가능한 ID입니다", Toast.LENGTH_SHORT).show();
                } else idCheck = false;

                if (pwCheck && nameCheck && idCheck && mailCheck) { //모든칸 잘 되었으면 Join버튼 클릭 가능(회원가입 가능)
                    btJoin.setEnabled(true);
                } else btJoin.setEnabled(false);
            }
        });
        txtPw1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!txtPw1.hasFocus()) {
                    p1 = txtPw1.getText().toString();
                    p2 = txtPw2.getText().toString();
                    if (p1.equals(p2) && !p1.equals("")) pwCheck = true;
                    else pwCheck = false;
                }
                if (pwCheck && nameCheck && idCheck && mailCheck) { //모든칸 잘 되었으면 Join버튼 클릭 가능(회원가입 가능)
                    btJoin.setEnabled(true);
                } else btJoin.setEnabled(false);

            }
        });
        txtPw2.setOnFocusChangeListener(new View.OnFocusChangeListener() { //비밀번호 확인 check
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!txtPw2.hasFocus()) {
                    p1 = txtPw1.getText().toString();
                    p2 = txtPw2.getText().toString();
                    if (p1.equals(p2)) pwCheck = true;
                    else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 틀려요!", Toast.LENGTH_SHORT).show();
                        txtPw2.setText("");
                        txtPw2.isFocused();
                        pwCheck = false;
                    }

                    if (pwCheck && nameCheck && idCheck && mailCheck) { //모든칸 잘 되었으면 Join버튼 클릭 가능(회원가입 가능)
                        btJoin.setEnabled(true);
                    } else btJoin.setEnabled(false);
                }
            }
        });

        txtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!txtName.hasFocus()) {
                    if (txtName.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "이름 빈칸임", Toast.LENGTH_SHORT).show();
                        nameCheck = false;
                    } else nameCheck = true;

                    if (pwCheck && nameCheck && idCheck && mailCheck) { //모든칸 잘 되었으면 Join버튼 클릭 가능(회원가입 가능)
                        btJoin.setEnabled(true);
                    } else btJoin.setEnabled(false);
                }

            }
        });


        txtMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {//Mail 형식 check
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!txtMail.hasFocus()) {
                    email = txtMail.getText().toString();
                    if (email.contains("@")) mailCheck = true; //mail에 @이 포함되있는가
                    else mailCheck = false;
                    if (mailCheck == false) {
                        Toast.makeText(getApplicationContext(), "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                        txtMail.isFocused();
                    }

                    if (pwCheck && nameCheck && idCheck && mailCheck) { //모든칸 잘 되었으면 Join버튼 클릭 가능(회원가입 가능)
                        btJoin.setEnabled(true);
                    } else btJoin.setEnabled(false);
                }
            }
        });

        btJoin.setOnClickListener(new View.OnClickListener() {//회원가입 버튼 눌렀을 때
            @Override
            public void onClick(View view) {
                id = txtId.getText().toString();
                pw = txtPw1.getText().toString();
                name = txtName.getText().toString();
                mail = txtMail.getText().toString();
                joinTask = new MemberJoinJson().execute(id,pw,name,mail);
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() { //cancle버튼 누르면 로그인 화면으로
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    class MemberJoinJson extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            HttpPostData(strings[0],strings[1],strings[2],strings[3]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void HttpPostData(String id, String pw, String name, String mail) {
        try {
            URL url = new URL(
                    "http://hyunazi.dothome.co.kr/AndDiary/join.php");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            http.setRequestProperty("content-type",
                    "application/x-www-form-urlencoded");
            StringBuffer buffer = new StringBuffer();
            buffer.append("id").append("=").append(id).append("&");
            buffer.append("pw").append("=").append(pw).append("&");
            buffer.append("name").append("=").append(name).append("&");
            buffer.append("mail").append("=").append(mail);

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
                builder.append(str + "\n");
            }
            String myResult = builder.toString();

            Log.d("result", "result : " + myResult);
        } catch (MalformedURLException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        } // try
    } // HttpPostData

}
