package com.example.resultapp;

import static com.example.resultapp.Http_Urls.ADD_RESULTS;
import static com.example.resultapp.Http_Urls.VIEW_RESULTS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewStudentActivity  extends AppCompatActivity {
    private int type;
    private HashMap<String, String> ResultHash = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    java.util.List<Student_List> List;
    private Student_Adapter adapter;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        sharedPreferences= getSharedPreferences(Pref.storage(), Context.MODE_PRIVATE);

        List = new ArrayList<>();
        adapter = new Student_Adapter(ViewStudentActivity.this, List);
        recyclerView = findViewById(R.id.info_recycle);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewStudentActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        view_all_results();
    }

    private void view_all_results() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Finding your uploaded results...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, VIEW_RESULTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    if (response.equals("found") || response.contains("found")) {
                        String final_results = response.replace("found", "");
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(final_results);
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject jsonObject = array.getJSONObject(i);
                                List.add(new Student_List(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("index_no"),
                                        jsonObject.getString("student_name"),
                                        jsonObject.getString("mtc"),
                                        jsonObject.getString("eng"),
                                        jsonObject.getString("sst"),
                                        jsonObject.getString("scie"),
                                        jsonObject.getString("date_created"),
                                        jsonObject.getString("users_id")
                                ));
                                recyclerView.setAdapter(adapter);
                                progressDialog.dismiss();
                                //adding the product to product list
                            }
                            //creating adapter object and setting it to recyclerview
                        } catch (Exception e) {
                            Toast.makeText(ViewStudentActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ViewStudentActivity.this, "" + response, Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(ViewInformation.this,Dashboard.class));
//                            finish();
                        progressDialog.dismiss();

                    }
                } catch (Exception e) {
                    Toast.makeText(ViewStudentActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(ViewStudentActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id",sharedPreferences.getString("user_id",null));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ViewStudentActivity.this);
        requestQueue.add(request);
    }


    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    protected void onRestart() {
        recreate();
        super.onRestart();
    }
}
