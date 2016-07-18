package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by 4강의실 on 2016-07-12.
 */
public class IntroActivity extends Activity {
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        layout = (LinearLayout)findViewById(R.id.baseLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(mIntent);
            }
        });

    }
}
