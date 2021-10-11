package com.example.resultapp;

public class Student_List {
    private int id;
    private String index;
    private String name;
    private String mtc;
    private String eng;
    private String sst;
    private String scie;
    private String date;
    private String user_id;



    public Student_List() {
    }

    public Student_List(int id, String index, String name, String mtc, String eng, String sst, String scie, String date, String user_id) {
        this.id = id;
        this.index = index;
        this.name = name;
        this.mtc = mtc;
        this.eng = eng;
        this.sst = sst;
        this.scie = scie;
        this.date = date;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMtc() {
        return mtc;
    }

    public void setMtc(String mtc) {
        this.mtc = mtc;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getSst() {
        return sst;
    }

    public void setSst(String sst) {
        this.sst = sst;
    }

    public String getScie() {
        return scie;
    }

    public void setScie(String scie) {
        this.scie = scie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}


