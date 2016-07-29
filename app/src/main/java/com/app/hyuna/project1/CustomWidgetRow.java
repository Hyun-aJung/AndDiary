package com.app.hyuna.project1;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class CustomWidgetRow {
    private String title;
    private String memo;
    private String date;
    private String id;
    private String no;

    public CustomWidgetRow(String title, String memo, String date, String id, String no) {
        this.title = title;
        this.memo = memo;
        this.date = date;
        this.id = id;
        this.no = no;
    }

    public CustomWidgetRow(String title, String memo, String date) {
        this.title = title;
        this.memo = memo;
        this.date = date;
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
