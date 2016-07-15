package com.app.hyuna.project1;

/**
 * Created by 4강의실 on 2016-07-15.
 */
public class Point { //저장할 객체 만들기
    float x;
    float y;
    boolean isDraw;
    public Point(float x, float y, Boolean isDraw) {
        super();
        this.x = x;
        this.y = y;
        this.isDraw = isDraw;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }
}