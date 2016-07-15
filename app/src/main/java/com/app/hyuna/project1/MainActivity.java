package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    String userId;
    ImageButton btnDraw,btnSet,btnShort,btnPost;
    final int MEMO=0,DRAW=1,POST=2,SET=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        btnShort = (ImageButton) findViewById(R.id.btnShort);
        btnDraw =  (ImageButton) findViewById(R.id.btnDraw);
        btnPost =  (ImageButton) findViewById(R.id.btnPost);
        btnSet  =  (ImageButton) findViewById(R.id.btnSet);

        btnShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("list",MEMO);
                startActivity(intent);
            }
        });

        btnDraw.setOnClickListener(new View.OnClickListener() { //draw 버튼 클릭
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("list",DRAW);
                startActivity(intent);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("list",POST);
                startActivity(intent);
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("list",SET);
                startActivity(intent);
            }
        });





    }
}
