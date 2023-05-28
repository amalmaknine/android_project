package com.example.caluclatriceapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> data;
    private LayoutInflater inflater;

    public HistoryAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.history_item, parent, false);

        TextView tvOperation = convertView.findViewById(R.id.tv_operation);
        TextView tvResult = convertView.findViewById(R.id.tv_result);
        tvOperation.setText(data.get(position).get("operation"));
        tvResult.setText(data.get(position).get("result"));

        return convertView;
    }

     void addItem(ArrayList<HashMap<String, String>> history){
        data.clear();
        data.addAll(history);

        notifyDataSetChanged();
    }
}
