package com.example.lab3;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIServer2 {
    @GET("json_data.json")
    Call<ArrayList<Contact2>> getMyJSON();
}
