package com.example.lab3;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonRequestExample {
    String url = "http://192.187.61.103/kimhoangson_ph21573/index.php"; // Thay thế bằng URL thực sự của bạn
    RequestQueue requestQueue;

    public JsonRequestExample(Context context) {
        // Tạo RequestQueue với context
        requestQueue = Volley.newRequestQueue(context);

        // Gọi hàm thực hiện yêu cầu JSON
        makeJsonObjectRequest();
    }

    private void makeJsonObjectRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Xử lý dữ liệu JSON ở đây
                        try {
                            String jsonData = response.getString("contacts"); // Thay "your_key" bằng key thích hợp trong JSON

                            // Hiển thị dữ liệu lên TextView
                            Context context = null;
                            TextView tvResult = new TextView(context);
                            tvResult.setText(jsonData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi ở đây (nếu có)
                        error.printStackTrace();
                    }
                });

        // Thêm yêu cầu vào hàng đợi
        requestQueue.add(jsonObjectRequest);
    }
}
