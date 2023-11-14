package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Bai3Activity extends AppCompatActivity {
    private ListView lvListView;
    private DataAdapter adapter;
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);
        initView();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bai3Activity.this, Bai4Activity.class);
                startActivity(intent);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.187.61.103/kimhoangson_ph21573/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<ArrayList<AndroidVersion>> call = service.getAndroidVer();
        call.enqueue(new Callback<ArrayList<AndroidVersion>>() {
            @Override
            public void onResponse(Call<ArrayList<AndroidVersion>> call, Response<ArrayList<AndroidVersion>> response) {
                if(response.isSuccessful()){
                    adapter.clear();
                    adapter.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(Bai3Activity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AndroidVersion>> call, Throwable t) {
                Toast.makeText(Bai3Activity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onFailure: " + t.getMessage());
            }
        });
    }

    private void initView() {
        lvListView = findViewById(R.id.lvListView);
        btnNext = findViewById(R.id.btnNext);
        adapter = new DataAdapter(this, new ArrayList<>());
        lvListView.setAdapter(adapter);
    }
}