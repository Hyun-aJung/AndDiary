package com.app.hyuna.project1;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by 4강의실 on 2016-07-15.
 */

public class CustomDrawView extends View{
    private Paint mPaint;
    private Resources resources;
    private Bitmap bitmap;
    private Canvas canvas;

    ArrayList<Point> points = new ArrayList<Point>();
    static int sizeX,sizeY=0;


    public CustomDrawView(Context context){
        super(context);
        init(context,null,0);
    }

    public CustomDrawView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context,attrs,0);
    }

    public CustomDrawView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        resources = context.getResources();
        bitmap = Bitmap.createBitmap(300,300,Bitmap.Config.ARGB_8888);//300,300이었다
        canvas= new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        invalidate();

    }
    public void setBitmapSize(int sizeX,int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        bitmap = Bitmap.createBitmap(sizeX,sizeY,Bitmap.Config.ARGB_8888);//300,300이었다
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public Canvas getCanvas(){
        return canvas;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        for (int i = 0; i < points.size(); i++) {
            if (!points.get(i).isDraw) {
                continue;
            }
            if(i!=0)mPaint.setColor(points.get(i-1).getColor());
            canvas.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y,mPaint);//, points.get(i-1).getPaint());
        }
        //super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                points.add(new Point(event.getX(), event.getY(), mPaint.getColor(), true));
                this.invalidate();
                //break;
                return  true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_DOWN:
                points.add(new Point(event.getX(), event.getY(), mPaint.getColor(),false));
                this.invalidate();
                return true;
            default:
                break;
        }
        return false;
    }
    public void setColor(int color){
        mPaint.setColor(color);
    }

}