package com.example.resultapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login=findViewById(R.id.btn_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString().toString())){
                    username.setError("Enter username");
                }
                else if (TextUtils.isEmpty(password.getText().toString().toString())){
                    password.setError("Enter password");
                }
                else{
                    Toast.makeText(MainActivity.this, "All fields entered", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}