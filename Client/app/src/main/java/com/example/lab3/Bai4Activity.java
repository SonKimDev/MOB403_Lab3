package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Bai4Activity extends AppCompatActivity {

    private ListView lvListView;
    private MyContactAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai4);
        lvListView = findViewById(R.id.lvListView);
        adapter = new MyContactAdapter(this, new ArrayList<>());
        lvListView.setAdapter(adapter);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.187.61.103/kimhoangson_ph21573/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServer2 server2 = retrofit.create(APIServer2.class);

        Call<ArrayList<Contact2>> call = server2.getMyJSON();
        call.enqueue(new Callback<ArrayList<Contact2>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact2>> call, Response<ArrayList<Contact2>> response) {
                if(response.isSuccessful()){
                    adapter.clear();
                    ArrayList<Contact2> contacts = response.body();
                    Log.d("1111", "onResponse: "+contacts);
                    adapter.addAll(contacts);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(Bai4Activity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Contact2>> call, Throwable t) {
                Toast.makeText(Bai4Activity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onFailure: " + t.getMessage());
            }
        });
    }
}