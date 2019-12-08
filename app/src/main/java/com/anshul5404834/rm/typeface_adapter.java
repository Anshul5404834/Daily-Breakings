package com.anshul5404834.rm;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class typeface_adapter extends ArrayAdapter<Typeface> {
    private List<Typeface> list;
    private Context mcontext;

    public typeface_adapter(Context context, List<Typeface> objects) {
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
        Typeface pos = list.get(position);
        TextView textView = m.findViewById(R.id.adapter_textview);
        textView.setText("Choose me");
        textView.setTypeface(pos);
        return m;
    }
}
