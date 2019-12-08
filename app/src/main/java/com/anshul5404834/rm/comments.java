package com.anshul5404834.rm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class comments extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);
        ListView listView = findViewById(R.id.listview_comments);
        final EditText editText = findViewById(R.id.enter_comments);
        Button button = findViewById(R.id.send_comment);
        final List<comments_pojo> comments_pojos = new ArrayList<>();
        final String ref = getIntent().getStringExtra("ref");
        final comment_adapter comment_adapter = new comment_adapter(comments.this, comments_pojos);
        listView.setAdapter(comment_adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child("comments").child(ref);
        try {
            if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName() == null) {
                FirebaseAuth.getInstance().getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(FirebaseAuth.getInstance().getCurrentUser().getUid()).build());
            }
        } catch (Exception e) {

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length() != 0) {
                    comments_pojo comments_pojo = new comments_pojo(editText.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    databaseReference.push().setValue(comments_pojo);
                    editText.setText("");
                }
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                comments_pojo comments_pojo = dataSnapshot.getValue(comments_pojo.class);
                comment_adapter.add(comments_pojo);
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

    @Override
    protected void onResume() {
        try {
            FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please Log in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, main_activity.class);
            startActivity(intent);
        }
        super.onResume();
    }
}
