package com.app.hyuna.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 4강의실 on 2016-07-29.
 */
public class CustomListDrawView extends View {
    String imagePath = null;
    public CustomListDrawView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            canvas.drawBitmap(bitmap,0,0,null);
            bitmap.recycle();
        }
    }


}
