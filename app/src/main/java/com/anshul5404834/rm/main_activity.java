package com.anshul5404834.rm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class main_activity extends AppCompatActivity {
    String username = "username";

    BottomNavigationView navView;
    private int REQUEST_CODE = 120;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_abcd:
                    final Intent intent = new Intent(main_activity.this, all_stuff.class);
                    try {
                        username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                        intent.putExtra("current_user_name", username);
                        startActivity(intent);
                    } catch (Exception e) {
                        startActivity(new Intent(main_activity.this, Authorization.class));
                    }

                    return true;
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent1 = new Intent(main_activity.this, Design_own.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_notifications:
                    final Intent intent3 = new Intent(main_activity.this, profile.class);
                    startActivity(intent3);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        navView = findViewById(R.id.nav_view);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        ListView listView = findViewById(R.id.content_main_list);
        List<FriendlyMessage> mymessage = new ArrayList<>();
        final message_display_adapter madapter = new message_display_adapter(main_activity.this, mymessage, "");
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(main_activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(main_activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("most_favourite");
        listView.setAdapter(madapter);

        databaseReference.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FriendlyMessage friendlyMessageSnapshot = dataSnapshot.getValue(FriendlyMessage.class);
                madapter.add(friendlyMessageSnapshot);
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
        super.onResume();
        navView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
        }
        if (item.getItemId() == R.id.login) {
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.how_to_use) {

            Intent intent = new Intent(main_activity.this, How_to_use.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.suggestions) {

            Intent intent = new Intent(main_activity.this, suggestions.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] != RESULT_OK) {
            Toast.makeText(getApplicationContext(), "PERMISSIONS NOT GRANTED ", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Intent intent = getIntent();
            if (intent != null) {
                Toast.makeText(getApplicationContext(), "not working", Toast.LENGTH_SHORT).show();
            }
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String username1 = bundle.getString("anshul");
                Toast.makeText(getApplicationContext(), username1, Toast.LENGTH_SHORT).show();
            }
            if (username == null) {
                Toast.makeText(getApplicationContext(), "username is null", Toast.LENGTH_SHORT).show();
            }

            if (requestCode == 1 && resultCode == RESULT_OK) {

            } else {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(main_activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }

            }
            if (requestCode == 2 && resultCode == RESULT_OK) {
            } else {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(main_activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }
}
