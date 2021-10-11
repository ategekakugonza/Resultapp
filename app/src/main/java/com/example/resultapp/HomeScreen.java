package com.example.resultapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {
        private TextView welcome_message;
        private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        welcome_message=findViewById(R.id.welcome_msg);
        sharedPreferences= getSharedPreferences(Pref.storage(), Context.MODE_PRIVATE);
        try {
            welcome_message.setText("Welcome "+sharedPreferences.getString("fname",null) +" "+sharedPreferences.getString("lname",null));
        }catch (Exception e){

        }

    }

    public void openStudentList(View view) {
        startActivity(new Intent(HomeScreen.this,ViewStudentActivity.class));
    }
    public void add_new_results(View view) {
        startActivity(new Intent(HomeScreen.this,AddMarksActivity.class));
    }
}