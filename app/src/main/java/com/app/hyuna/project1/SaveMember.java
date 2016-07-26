package com.app.hyuna.project1;

import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by 4강의실 on 2016-07-25.
 */
public class SaveMember {
    public static String id;
    public static MemberVO mvo;

    public SaveMember(){
    }

    public SaveMember(String id){
        this.id = id;
    }
    public void deleteMember(){
        mvo = null;
    }
    public void saveMemberJson(){
        new saveMemberJson().execute(id);
    }
    public MemberVO getMyMember(){
        return mvo;
    }

    private class saveMemberJson extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return HttpPostDate(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<MemberVO>(){}.getType();
            mvo = gson.fromJson(s,collectionType);

        }
    }
    public String HttpPostDate(String str){
        String myResult="";
        try {
            URL url = new URL(
                    "http://hyunazi.dothome.co.kr/AndDiary/save_member.php");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");

            http.setRequestProperty("content-type",
                    "application/x-www-form-urlencoded");
            StringBuffer buffer = new StringBuffer();
            buffer.append("id").append("=").append(str);

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            InputStreamReader tmp = new InputStreamReader(
                    http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String strResult;
            while ((strResult = reader.readLine()) != null) {
                builder.append(strResult+"\n");
            }
            myResult = builder.toString();

            Log.d("result", "result : " + myResult);
        } catch (MalformedURLException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        } // try
        return myResult;
    }
}
