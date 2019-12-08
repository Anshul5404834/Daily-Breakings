package com.anshul5404834.rm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class message_display_adapter extends ArrayAdapter<FriendlyMessage> {
    private Context context;
    private String username_thrusold;
    private List<FriendlyMessage> friendlyMessageList;

    public message_display_adapter(Context context, List<FriendlyMessage> objects, String s) {
        super(context, 0, objects);
        this.context = context;
        username_thrusold = s;
        this.friendlyMessageList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final FriendlyMessage message1 = friendlyMessageList.get(position);
        View m;
        m = LayoutInflater.from(context).inflate(R.layout.list_content, parent, false);
        TextView message = m.findViewById(R.id.message);
        final TextView username = m.findViewById(R.id.username);
        final ImageView image_msg = m.findViewById(R.id.image_msg);
        username.setText(message1.getName());
        message.setText(message1.getText());
        final TextView comment = m.findViewById(R.id.comment);

        //if(message1.getPhotoUrl()!=null) {
        //   Glide.with(image_msg.getContext())
        //           .load(message1.getPhotoUrl())
        //           .into(image_msg);
        //   message.setVisibility(View.GONE);
        //}else {image_msg.setVisibility(View.INVISIBLE);}
        //       return m;
        //}

        Glide
                .with(image_msg).setDefaultRequestOptions(RequestOptions.overrideOf(500))
                .load(message1.getPhotoUrl())
                .apply(new RequestOptions().placeholder(R.drawable.download))
                .into(image_msg);


        ImageButton imageButton = m.findViewById(R.id.imageButton);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_images save_image = new Save_images();
                save_image.save_image(image_msg, context, message1.getText());
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    Intent intent = new Intent(context, comments.class);
                    intent.putExtra("ref", message1.getTime());
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Please log in to continue", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, Authorization.class));
                }

            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    Intent intent = new Intent(context, others_profile.class);
                    intent.putExtra("id", message1.getUid());
                    intent.putExtra("name", message1.getName());
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Please log in to continue", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, Authorization.class));
                }


            }
        });
        return m;
    }
}
