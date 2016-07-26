package com.app.hyuna.project1;



/**
 * Created by 4강의실 on 2016-07-18.
 */
public class MemberVO {
    private String name;
    private String Id;
    private String email;
    private String pw;
    private String date;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MemberVO(String id, String pw, String name, String email, String date) {
        this.name = name;
        Id = id;
        this.email = email;
        this.pw = pw;
        this.date = date;
    }
    /*public MemberVO( String id, String name, String email) {
        this.name = name;
        Id = id;
        this.email = email;
    }*/


    @Override
    public String toString() {
        return "MemberVO{" +
                "name='" + name + '\'' +
                ", Id='" + Id + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", date=" + date +
                '}';
    }

}
