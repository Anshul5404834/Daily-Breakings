package com.anshul5404834.rm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class comment_adapter extends ArrayAdapter {

    List<comments_pojo> objects;
    Context context;

    public comment_adapter(Context context, List<comments_pojo> objects) {
        super(context, 0, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View m;
        m = LayoutInflater.from(context).inflate(R.layout.comment_pojo_display, parent, false);
        TextView textView = m.findViewById(R.id.username_comments);
        TextView textView1 = m.findViewById(R.id.comment_comment);
        comments_pojo comments_pojo = objects.get(position);
        textView.setText(comments_pojo.getUsername() + " :");
        textView1.setText(comments_pojo.getComments());
        return m;
    }
}
