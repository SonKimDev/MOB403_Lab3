package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Bai2Activity extends AppCompatActivity {
    TextView tvResult;
    Button btnArrRequest, btnObjRequest, btnNext;
    ProgressDialog progressDialog;
    String urlJsonObj = "http://192.187.61.103/kimhoangson_ph21573/person_object.json";
    String urlJsonArr = "http://192.187.61.103/kimhoangson_ph21573/person_array.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);
        tvResult = findViewById(R.id.tvResult);
        btnArrRequest = findViewById(R.id.btnArrRequest);
        btnObjRequest = findViewById(R.id.btnObjRequest);
        btnNext = findViewById(R.id.btnNext);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        btnObjRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonObjectRequest();
            }
        });
        btnArrRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonArrayRequest();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bai2Activity.this, Bai3Activity.class);
                startActivity(intent);
            }
        });
    }

    private void makeJsonArrayRequest() {
        progressDialog.show();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(urlJsonArr,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d("TAG", jsonArray.toString());
                        try {
                            String jsonResponse = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject person = (JSONObject) jsonArray.get(i);
                                String name = person.getString("name");
                                String email = person.getString("email");
                                JSONObject phone = person.getJSONObject("phone");
                                String home = phone.getString("home");
                                String mobile = phone.getString("mobile");
                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Email: " + email + "\n\n";
                                jsonResponse += "Home: " + home + "\n\n";
                                jsonResponse += "Mobile: " + mobile + "\n\n";
                            }
                            tvResult.setText(jsonResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        progressDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("TAG","Error: "+ volleyError.getMessage());
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),volleyError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

    private void makeJsonObjectRequest() {
        progressDialog.show();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonObj, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    String email = response.getString("email");
                    JSONObject phone = response.getJSONObject("phone");
                    String home = phone.getString("home");
                    String mobile = phone.getString("mobile");
                    String jsonSponse = "";
                    jsonSponse += "Name: " + name + "\n\n";
                    jsonSponse += "Email: " + email + "\n\n";
                    jsonSponse += "Home: " + home + "\n\n";
                    jsonSponse += "Phone: " + mobile + "\n\n";
                    tvResult.setText(jsonSponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("123", "Error: "+error.getMessage());
                Toast.makeText(Bai2Activity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                tvResult.setText(error.getMessage());

                progressDialog.hide();
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest);
    }
}