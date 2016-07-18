package com.app.hyuna.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by 4강의실 on 2016-07-15.
 */
public class CustomView extends android.view.View{
    private Context mcontext = null;
    private Paint paint = null;
    ArrayList<Point> points = new ArrayList<Point>();

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context) {
        super(context);
    }

    public void initPaint(int i){
        points.clear();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
    }
    public void setMcontext(Context mcontext){
        this.mcontext = mcontext;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < points.size(); i++) {
            if (!points.get(i).isDraw) {
                continue;
            }
            canvas.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y, paint);
        }
        //super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                points.add(new Point(event.getX(), event.getY(), true));
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_DOWN:
                points.add(new Point(event.getX(), event.getY(), false));
            default:
                break;
        }
        return false;
    }
}