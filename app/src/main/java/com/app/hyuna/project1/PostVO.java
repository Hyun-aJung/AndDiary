package com.app.hyuna.project1;

import java.util.Date;

/**
 * Created by 4강의실 on 2016-07-19.
 */
public class PostVO {

    private int no;
    private String title;
    private String memo;
    private Date date;
    private String img;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostVO(int no, String title, String memo, Date date, String img, String id) {
        this.no = no;
        this.title = title;
        this.memo = memo;
        this.date = date;
        this.img = img;
        this.id = id;
    }

    @Override
    public String toString() {
        return "PostVO{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", memo='" + memo + '\'' +
                ", date=" + date +
                ", img='" + img + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
