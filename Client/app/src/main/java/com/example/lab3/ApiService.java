package com.example.lab3;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("jsonandroid.json") // Đường dẫn tương đối của endpoint API
    Call<ArrayList<AndroidVersion>> getAndroidVer();
}
