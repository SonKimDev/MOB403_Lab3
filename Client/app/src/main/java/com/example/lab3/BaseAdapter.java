package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BaseAdapter extends ArrayAdapter<Contact> {

    public BaseAdapter(@NonNull Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);

        Contact currentContact = getItem(position);

        if (convertView!=null){
            tvName.setText(currentContact.getName());
            tvEmail.setText(currentContact.getEmail());
            tvPhone.setText(currentContact.getMobile());
        }

        return convertView;
    }
}
