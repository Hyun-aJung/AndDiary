package com.app.hyuna.project1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 4강의실 on 2016-07-14.
 */
public class WriteDrawActivity extends Activity {
    EditText edtTitle;
    ImageButton btnBlack, btnBlue, btnGreen, btnRed, btnYellow;
    CustomView customView;
    //MyView myView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_draw);
        setTitle("Drawing Memo");
        customView = (CustomView)findViewById(R.id.myView);
        customView.setMcontext(WriteDrawActivity.this);




        //MyView myView = new MyView(getApplicationContext());
        //setContentView(R.id.myView);

    }
}
/*
class CustomView extends android.view.View{
    private Context mcontext = null;
    private Paint paint = null;
    ArrayList<Point> points = new ArrayList<Point>();

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

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
    public void setMcontext(Context context){
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
*/




/*
class MyView extends View {//Canvas 만들기
    Paint paint = new Paint();
    Path path = new Path();
    public MyView(Context context) {
        super(context);
    }

    ArrayList<Point> points = new ArrayList<Point>();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        for (int i = 0; i < points.size(); i++) {
            if (!points.get(i).isDraw) {
                continue;
            }
            canvas.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y, paint);
        }
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
        return true;
    }
}*/



/*
class MyView extends View {

    Paint paint = new Paint();
    Path path = new Path();

    float y ;
    float x ;

    public MyView(Context context) {
        super(context);
    }


    protected void onDraw(Canvas canvas) {
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x = event.getX();
        y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();

        return true;
    }
}
*/


                /*
                case MotionEvent.ACTION_DOWN:
                    startX = (int)event.getX();
                    startY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                        stopX = (int)event.getX();
                        stopY = (int)event.getY();
                        this.invalidate();
                        break;*/

//return true;
//}
//}
//}
