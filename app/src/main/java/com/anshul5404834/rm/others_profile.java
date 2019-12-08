package com.anshul5404834.rm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class others_profile extends Activity {
    ImageView profile_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        profile_photo = findViewById(R.id.profile_photo);
        TextView username = findViewById(R.id.username_change);
        username.setText(getIntent().getStringExtra("name"));
        profile_photo.setImageResource(R.drawable.man);
        ListView listView = findViewById(R.id.mylist);
        String id = getIntent().getStringExtra("id");
        List<FriendlyMessage> mymessage = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("messages");
        Query query = databaseReference.orderByChild("uid").equalTo(id).limitToLast(100);
        final message_display_adapter madapter = new message_display_adapter(others_profile.this, mymessage, "");
        listView.setAdapter(madapter);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FriendlyMessage friendlyMessageSnapshot = dataSnapshot.getValue(FriendlyMessage.class);
                madapter.insert(friendlyMessageSnapshot, 0);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
