package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyContactAdapter extends ArrayAdapter<Contact2> {

    public MyContactAdapter(@NonNull Context context, ArrayList<Contact2> contact2s) {
        super(context, 0, contact2s);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_row_view, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);
        ImageView ivImage = convertView.findViewById(R.id.ivImage);

        Contact2 contact2 = getItem(position);

        if(convertView!=null){
            tvName.setText(contact2.getName());
            tvEmail.setText(contact2.getEmail());
            Picasso.get().load(contact2.getProfilePic()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(ivImage);
        }

        return convertView;
    }
}
