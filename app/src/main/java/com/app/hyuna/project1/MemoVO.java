package com.app.hyuna.project1;

import java.util.Date;

/**
 * Created by 4강의실 on 2016-07-19.
 */
public class MemoVO {
    private int no;
    private String title;
    private String memo;
    private String date;
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
/*
    public MemoVO(int no, String title, String memo, String date, String id) {
        this.no = no;
        this.title = title;
        this.memo = memo;
        this.date = date;
        this.id = id;
    }*/

    public MemoVO(int no, String title, String memo, String date) {
        this.no = no;
        this.title = title;
        this.memo = memo;
        this.date = date;
    }

    @Override
    public String toString() {
        return "MemoVO{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", memo='" + memo + '\'' +
                ", date=" + date +
                ", id='" + id + '\'' +
                '}';
    }
}
