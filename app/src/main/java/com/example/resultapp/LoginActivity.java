package com.example.resultapp;

import static com.example.resultapp.Http_Urls.LOGIN_PATH;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        find_data_existence();
        setContentView(R.layout.activity_login);
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
                    func_login_user();
                }
            }
        });
    }

    private void func_login_user() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, LOGIN_PATH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.contains("logged-in-successfully")) {
                    String final_results=response.replace("logged-in-successfully","");
                    try {
                        JSONObject jsonObject=new JSONObject(final_results);
                        SharedPreferences sharedPreferences = getSharedPreferences(Pref.storage(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("pref", "checked");
                        editor.putString("user_id", jsonObject.getString("id"));
                        editor.putString("fname", jsonObject.getString("fname"));
                        editor.putString("lname", jsonObject.getString("lname"));
                        editor.putString("gender", jsonObject.getString("gender"));
                        editor.putString("phone", jsonObject.getString("phone"));
                        editor.putString("date_created", jsonObject.getString("date_created"));
                        editor.apply();
                        Toast.makeText(LoginActivity.this,"Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeScreen.class));
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username.getText().toString().trim());
                params.put("password",password.getText().toString().trim());
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }

    private void find_data_existence() {
        SharedPreferences sharedPreferences = getSharedPreferences(Pref.storage(), Context.MODE_PRIVATE);
        String pref = sharedPreferences.getString("pref", null);
        if (pref == null || pref == "") {

        }
        else{
            startActivity(new Intent(LoginActivity.this, HomeScreen.class));
            finish();
        }
    }
}