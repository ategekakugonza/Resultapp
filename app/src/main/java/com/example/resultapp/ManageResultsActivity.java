package com.example.resultapp;

import static com.example.resultapp.Http_Urls.ADD_RESULTS;
import static com.example.resultapp.Http_Urls.DELETE_RECORD;
import static com.example.resultapp.Http_Urls.UPDATE_RESULTS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ManageResultsActivity extends AppCompatActivity {

    private Button btn_save,btn_delete;
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
        setContentView(R.layout.activity_manage_results);
        btn_save=findViewById(R.id.btn_submit);
        student_name=findViewById(R.id.sname);
        student_index_no=findViewById(R.id.regno);
        mtc=findViewById(R.id.mtc);
        scie=findViewById(R.id.scie);
        sst=findViewById(R.id.sst);
        eng=findViewById(R.id.eng);
        btn_delete=findViewById(R.id.btn_delete);
        sharedPreferences= getSharedPreferences(Pref.storage(), Context.MODE_PRIVATE);

        mtc.setText(""+getIntent().getStringExtra("mtc"));
        scie.setText(""+getIntent().getStringExtra("sci"));
        sst.setText(""+getIntent().getStringExtra("eng"));
        eng.setText(""+getIntent().getStringExtra("sst"));
        student_name.setText(""+getIntent().getStringExtra("name"));
        student_index_no.setText(""+getIntent().getStringExtra("index"));


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(ManageResultsActivity.this)
                        .setTitle("Delete Confirmation")
                        .setCancelable(false)
                        .setMessage("Are you sure you want to delete this record")
                        .setPositiveButton("Yes", (dialogInterface, i) -> deleted_result())
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog dialog= builder.create();
                dialog.show();
            }
        });



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
                    update_marks_func();

                }
            }
        });
    }

    private void deleted_result() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_RECORD, (Response.Listener<String>) response -> {
            progressDialog.dismiss();
          if (response.contains("deleted")) {
                Toast.makeText(ManageResultsActivity.this, "All is set Marks Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ManageResultsActivity.this,ViewStudentActivity.class));
                finish();
            }
            else {
                Toast.makeText(ManageResultsActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(ManageResultsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("mark_id",getIntent().getStringExtra("result_id"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ManageResultsActivity.this);
        requestQueue.add(request);
    }

    private void update_marks_func() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_RESULTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.contains("duplicate")) {
                    Toast.makeText(ManageResultsActivity.this, "Results For Student With this Index Number already created", Toast.LENGTH_SHORT).show();
                }
                else if (response.contains("Updated")) {
                    Toast.makeText(ManageResultsActivity.this, "All is set Marks Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ManageResultsActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(ManageResultsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("mark_id",getIntent().getStringExtra("result_id"));
                params.put("name",student_name.getText().toString().trim());
                params.put("index",student_index_no.getText().toString().trim());
                params.put("mtc",mtc.getText().toString().trim());
                params.put("sci",scie.getText().toString().trim());
                params.put("sst",sst.getText().toString().trim());
                params.put("eng",eng.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ManageResultsActivity.this);
        requestQueue.add(request);
    }
}