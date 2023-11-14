package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataAdapter extends ArrayAdapter<AndroidVersion> {

    public DataAdapter(@NonNull Context context, ArrayList<AndroidVersion> androidVersions) {
        super(context, 0, androidVersions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.android_item, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvVersion = convertView.findViewById(R.id.tvVersion);
        TextView tvAPILevel = convertView.findViewById(R.id.tvAPILevel);

        AndroidVersion currentAndroidVersion = getItem(position);

        if (convertView!=null){
            tvName.setText(currentAndroidVersion.getName());
            tvVersion.setText(currentAndroidVersion.getVer());
            tvAPILevel.setText(currentAndroidVersion.getApi());
        }

        return convertView;
    }
}
