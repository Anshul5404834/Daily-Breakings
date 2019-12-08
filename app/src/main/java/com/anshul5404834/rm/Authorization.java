package com.anshul5404834.rm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class Authorization extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    String username = "abcdefgh";
    FirebaseAuth.AuthStateListener mauthstatelistener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instanciating firebase_auth

        firebaseAuth = FirebaseAuth.getInstance();

        // defining Auth state listener

        mauthstatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //  Toast.makeText(getApplicationContext(),"Already Signed in",Toast.LENGTH_SHORT).show();
                    //passing information to main activity
                    try {
                        firebaseAuth.removeAuthStateListener(mauthstatelistener);
                        firebaseAuth.removeAuthStateListener(mauthstatelistener);

                    } catch (Exception e) {
                        onDestroy();
                    }
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            121);


                }
            }
        };

        //adding auth state listener to firebase authorization
        try {
            firebaseAuth.addAuthStateListener(mauthstatelistener);
        } catch (Exception e) {
            Log.e("try block", "onCreate: io_exception_caught");
            firebaseAuth.addAuthStateListener(mauthstatelistener);
        }
        //firebaseAuth.removeAuthStateListener(mauthstatelistener);
    }

    @Override
    protected void onResume() {
        firebaseAuth.removeAuthStateListener(mauthstatelistener);
        super.onResume();
        //

        // try{firebaseAuth.getCurrentUser();}catch (Exception e){onDestroy();}
        finish();

    }

    //starting activity for result of auth state listener
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 121) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "LOGIN UNSUCCESSFUL", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, main_activity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Welcome to the world of creativity", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, main_activity.class);
        startActivity(intent);
    }
}
