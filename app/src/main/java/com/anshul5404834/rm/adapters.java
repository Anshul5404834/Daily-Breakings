package com.anshul5404834.rm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class adapters extends ArrayAdapter<Integer> {
    private List<Integer> list;
    private Context mcontext;

    public adapters(Context context, List<Integer> objects) {
        super(context, 0, objects);
        this.list = objects;
        this.mcontext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View m = convertView;
        if (m == null) {
            m = LayoutInflater.from(mcontext).inflate(R.layout.integer, parent, false);
        }
        int pos = list.get(position);
        TextView textView = m.findViewById(R.id.adapter_textview);
        textView.setText(String.valueOf(position));

        try {
            textView.setBackgroundColor(pos);
        } catch (Exception e) {
            textView.setTextSize(pos);
        }
        return m;
    }
}
