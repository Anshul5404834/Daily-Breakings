package com.anshul5404834.rm;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class profile extends Activity {
    ImageView profile_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        } catch (Exception e) {
            Intent intent = new Intent(profile.this, Authorization.class);
            startActivity(intent);
        }
        setContentView(R.layout.profile);

        profile_photo = findViewById(R.id.profile_photo);
        final TextView username = findViewById(R.id.username_change);

        try {
            username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            Uri url = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
            Glide.with(getApplicationContext()).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.man)).load(url).apply(new RequestOptions().circleCrop()).into(profile_photo);
        } catch (Exception e) {
        }
        if (username.getText().length() == 0) {
            username.setText("update_username");
        }

        ListView listView = findViewById(R.id.mylist);
        if (profile_photo.getDrawable() == null) {
            profile_photo.setImageResource(R.drawable.man);
        }
        profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click again to update profile", Toast.LENGTH_SHORT).show();
                profile_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/jpeg");
                        intent.setType("image/png");
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        startActivityForResult(Intent.createChooser(intent, "Complete action using "), 2112);
                    }
                });

            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText editText = new EditText(getApplicationContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(profile.this);
                builder.setView(editText);
                builder.setTitle("Update Username");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.getText().toString().trim().isEmpty()) {
                            username.setText("update_username");
                        }
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(
                                new UserProfileChangeRequest.Builder().
                                        setDisplayName(editText.getText().toString()).build()
                        );
                        username.setText(editText.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create();
                builder.show();
            }
        });
        List<FriendlyMessage> mymessage = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("messages");
        try {
            Query query = databaseReference.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).limitToLast(100);

            final message_display_adapter madapter = new message_display_adapter(profile.this, mymessage, "");
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
        } catch (NullPointerException e) {
            startActivity(new Intent(this, Authorization.class));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2112) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                FirebaseAuth.getInstance().getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(uri).build());
                Glide.with(getApplicationContext()).load(uri).apply(new RequestOptions().circleCrop()).into(profile_photo);
            }
        }

    }

    @Override
    protected void onResume() {
        try {
            FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please log in to continue", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, main_activity.class));
            onBackPressed();
        }

        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDestroy();

    }
}
