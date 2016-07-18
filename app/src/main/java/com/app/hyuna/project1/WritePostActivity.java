package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by 4강의실 on 2016-07-14.
 */
public class WritePostActivity extends Activity {
    EditText edtTitle,edtMemo;
    Button btnNew,btnSave;
    GridView gridView;
    String memoTitle,memo;
    final int MEMO=0,DRAW=1,POST=2,SET=3;
    String userId;
    String[] tempImg={"0","0","0","0","0"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        setTitle("Postscript");


        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtMemo = (EditText)findViewById(R.id.edtMemo);
        gridView = (GridView)findViewById(R.id.gridView);
        btnNew = (Button)findViewById(R.id.btnNewImg);
        btnSave = (Button)findViewById(R.id.btnSave);

        edtTitle.isFocused();

        //default 로 gridView 에 default 이미지만 보여줌
        gridView.setAdapter(new GridViewImg(this));

        btnNew.setOnClickListener(new View.OnClickListener() {//+버튼 클릭시
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(),ChooseImageActivity.class);
                startActivityForResult(mIntent,0);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {//save버튼 클릭시
            @Override
            public void onClick(View view) {
                memoTitle = edtTitle.getText().toString();
                memo = edtMemo.getText().toString();
                if(memoTitle==null){
                    Toast.makeText(getApplicationContext(),"제목을 입력하세요.",Toast.LENGTH_SHORT).show();
                }else if(false) {//추가할 이미지가 없을 떄
                    //TODO title, memo 없으면 입력하라하고 image 없으면 이미지는 저장하지 않고 DB저장
                    //TODO defalt 이미지 제외하고 추가한 이미지만 DB에 저장한다
                }else{
                    Intent intent = new Intent(WritePostActivity.this,ListActivity.class);
                    intent.putExtra("userId",userId);
                    //intent.putExtra("list",POST);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            //userId = data.getExtras().getString("userId");//intent로 보낸 데이터 받기
            Log.d("??????????","받기전");
            tempImg[0] = data.getExtras().getString("pic0");
            tempImg[1] = data.getExtras().getString("pic1");
            tempImg[2] = data.getExtras().getString("pic2");
            tempImg[3] = data.getExtras().getString("pic3");
            tempImg[4] = data.getExtras().getString("pic4");

            // GridViewImg img = new GridViewImg(this);
            //tempImg[0].
            // img.setImg(tempImg);
            Log.d("??????????",tempImg[0]+", "+tempImg[1]+", "+tempImg[2]+", "+tempImg[3]+", "+tempImg[4]);
            GridViewImg grid = new GridViewImg(this);
            grid.setBitImg(tempImg);
            gridView.setAdapter(grid);
        }
    }
}
