package com.example.resultapp;

import static com.example.resultapp.Http_Urls.ADD_RESULTS;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddMarksActivity extends AppCompatActivity {

    private Button btn_save;
    private EditText student_name;
    private EditText student_index_no;
    private EditText mtc;
    private EditText scie;
    private EditText sst;
    private EditText eng;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marks);
        btn_save=findViewById(R.id.btn_submit);
        student_name=findViewById(R.id.sname);
        student_index_no=findViewById(R.id.regno);
        mtc=findViewById(R.id.mtc);
        scie=findViewById(R.id.scie);
        sst=findViewById(R.id.sst);
        eng=findViewById(R.id.eng);
        sharedPreferences= getSharedPreferences(Pref.storage(), Context.MODE_PRIVATE);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(student_name.getText().toString().trim()) || student_name.getText().toString().trim().length() < 5){
                    student_name.setError("Student name is invalid");
                }
              else if (TextUtils.isEmpty(student_index_no.getText().toString().trim()) || student_index_no.getText().toString().trim().length() < 1){
                    student_index_no.setError("Student Index No is invalid");
                }
               else if (TextUtils.isEmpty(eng.getText().toString().trim()) || Integer.parseInt(eng.getText().toString().trim()) < 0 ||Integer.parseInt(eng.getText().toString().trim()) > 100 ){
                    eng.setError("Maths Marks is Invalid");
                }
                else if (TextUtils.isEmpty(mtc.getText().toString().trim()) || Integer.parseInt(mtc.getText().toString().trim()) < 0 ||Integer.parseInt(mtc.getText().toString().trim()) > 100 ){
                    mtc.setError("Maths Marks is Invalid");
                }
                else if (TextUtils.isEmpty(scie.getText().toString().trim()) || Integer.parseInt(scie.getText().toString().trim()) < 0 ||Integer.parseInt(scie.getText().toString().trim()) > 100 ){
                    scie.setError("Maths Marks is Invalid");
                }
                else if (TextUtils.isEmpty(sst.getText().toString().trim()) || Integer.parseInt(sst.getText().toString().trim()) < 0 ||Integer.parseInt(sst.getText().toString().trim()) > 100 ){
                    sst.setError("Maths Marks is Invalid");
                }

                else{
//                    save marks
                    func_save_marks();

                }
            }
        });
    }
    private void func_save_marks() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, ADD_RESULTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.contains("duplicate")) {
                    Toast.makeText(AddMarksActivity.this, "Results For Student With this Index Number already created", Toast.LENGTH_SHORT).show();
                }
                else if (response.contains("Saved")) {
                    Toast.makeText(AddMarksActivity.this, "All is set Marks Submitted", Toast.LENGTH_SHORT).show();
                    student_name.setText("");
                    student_index_no.setText("");
                    scie.setText("");
                    sst.setText("");
                    eng.setText("");
                    mtc.setText("");
                }
                else {
                    Toast.makeText(AddMarksActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(AddMarksActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id",sharedPreferences.getString("user_id",null));
                params.put("name",student_name.getText().toString().trim());
                params.put("index",student_index_no.getText().toString().trim());
                params.put("mtc",mtc.getText().toString().trim());
                params.put("sci",scie.getText().toString().trim());
                params.put("sst",sst.getText().toString().trim());
                params.put("eng",eng.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddMarksActivity.this);
        requestQueue.add(request);
    }
}