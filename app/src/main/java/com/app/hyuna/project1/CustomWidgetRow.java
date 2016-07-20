package com.app.hyuna.project1;

/**
 * Created by 4강의실 on 2016-07-20.
 */
public class CustomWidgetRow {
    private String title;
    private String memo;

    public CustomWidgetRow(String title, String memo) {
        this.title = title;
        this.memo = memo;
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
}
