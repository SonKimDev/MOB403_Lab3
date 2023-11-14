package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvListView;
    private Button btnNext;
    private ArrayList<Contact> contacts;
    private BaseAdapter adapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvListView = findViewById(R.id.lvListView);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Bai2Activity.class);
                startActivity(intent);
            }
        });
        contacts = new ArrayList<>();
        adapter = new BaseAdapter(this, contacts);
        lvListView.setAdapter(adapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
        new DownloadContactsTask().execute("http://192.187.61.103/kimhoangson_ph21573/index.php");
    }
    private class DownloadContactsTask extends AsyncTask<String, Void, ArrayList<Contact>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("AsyncTask", "onPreExecute");
            progressDialog.show();
        }

        @Override
        protected ArrayList<Contact> doInBackground(String... urls) {
            Log.d("AsyncTask", "doInBackground");
            String url = urls[0];
            ArrayList<Contact> result = new ArrayList<>();

            try {
                // Tải dữ liệu JSON từ URL
                String jsonString = downloadJsonString(url);

                // Parse JSON thành danh sách contacts
                result = parseJson(jsonString);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Contact> result) {
            // Cập nhật danh sách contacts trong adapter và hiển thị lên ListView
            Log.d("AsyncTask", "onPostExecute");
            progressDialog.dismiss();
            contacts.clear();
            contacts.addAll(result);
            adapter.notifyDataSetChanged();
        }

        private String downloadJsonString(String url) throws IOException {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL jsonUrl = new URL(url);
                connection = (HttpURLConnection) jsonUrl.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                return buffer.toString();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private ArrayList<Contact> parseJson(String jsonString) throws JSONException {
            Log.d("AsyncTask", "parseJson");
            ArrayList<Contact> result = new ArrayList<>();

            try {
                // Tạo một đối tượng JSONObject từ chuỗi JSON
                JSONObject jsonObject = new JSONObject(jsonString);

                // Trích xuất mảng "contacts" từ đối tượng JSONObject
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");

                // Lặp qua mỗi đối tượng trong mảng "contacts"
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Trích xuất đối tượng Contact từ mảng "contacts"
                    JSONObject contactObject = jsonArray.getJSONObject(i);

                    // Trích xuất các trường từ đối tượng Contact
                    String id = contactObject.getString("id");
                    String name = contactObject.getString("name");
                    String email = contactObject.getString("email");

                    // Đối tượng phone là một đối tượng con của Contact
                    JSONObject phoneObject = contactObject.getJSONObject("phone");
                    String mobile = phoneObject.getString("mobile");

                    // Tạo đối tượng Contact và thêm vào danh sách kết quả
                    Contact contact = new Contact();
                    contact.setId(id);
                    contact.setName(name);
                    contact.setEmail(email);
                    contact.setMobile(mobile);
                    result.add(contact);

                    // Log thông tin của đối tượng Contact
                    Log.d("ContactParsing", "ID: " + id + ", Name: " + name + ", Email: " + email + ", Mobile: " + mobile);
                }
            } catch (JSONException e) {
                Log.e("AsyncTask", "Error parsing JSON: " + e.getMessage());
                e.printStackTrace();
            }
            return result;
        }
    }
}