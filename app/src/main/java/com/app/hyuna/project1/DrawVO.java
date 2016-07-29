package com.app.hyuna.project1;

import android.graphics.Bitmap;

/**
 * Created by 4강의실 on 2016-07-29.
 */
public class DrawVO {

    private int no;
    private String title;
    private String memo;
    private String date;
    private String draw;
    private String id;
    private Bitmap drawBit;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getDrawBit() {
        return drawBit;
    }

    public void setDrawBit(Bitmap drawBit) {
        this.drawBit = drawBit;
    }

    public DrawVO(int no, String title, String memo, String date, String draw, String id) {
        this.no = no;
        this.title = title;
        this.memo = memo;
        this.date = date;
        this.draw = draw;
        this.id = id;
    }

    @Override
    public String toString() {
        return "PostVO{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", memo='" + memo + '\'' +
                ", date=" + date +
                ", draw='" + draw + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
