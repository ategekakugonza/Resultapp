package com.example.resultapp;

public class Http_Urls {
    private final static String local_s = "http://172.16.0.2:8080/resultapp/";
    private final static String address = "" + local_s;
    public final static String LOGIN_PATH = address + "login.php";
    public final static String ADD_RESULTS = address + "save_marks.php";
    public final static String VIEW_RESULTS = address + "view_my_results.php";
    public final static String UPDATE_RESULTS = address + "update_results.php";
    public final static String DELETE_RECORD = address + "delete_record.php";
}
