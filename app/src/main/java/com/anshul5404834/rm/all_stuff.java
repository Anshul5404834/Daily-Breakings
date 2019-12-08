package com.anshul5404834.rm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class all_stuff extends AppCompatActivity {

    int count = 0;
    int code_from_intent = 0;
    String message;
    SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseDatabase mdatabase;
    private DatabaseReference message_refrence;
    private ChildEventListener childEventListener;
    private String username;
    private String uri_string;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private message_display_adapter madapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception e) {
        }

        try {
            FirebaseAuth.getInstance().getCurrentUser().getDisplayName().length();
        } catch (Exception e) {
            Intent intent = new Intent(this, Authorization.class);
            startActivity(intent);
        }
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                madapter.notifyDataSetChanged();
            }
        });
        //    editText = (EditText) findViewById(R.id.enter_your_message);
        //    send_button = (ImageView) findViewById(R.id.send_button);
        //    photobutton = (ImageView) findViewById(R.id.phone_button);
        ListView list_msgs = findViewById(R.id.adapter);
        //   list_msgs.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mdatabase = FirebaseDatabase.getInstance();
        message_refrence = mdatabase.getReference().child("messages");
        message_refrence.keepSynced(true);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("chat_photos");
        code_from_intent = getIntent().getIntExtra("a", 0);
        message = "";
        if (code_from_intent == 12) {

            message = getIntent().getStringExtra("mess");
            // File f = new File("/storage/emulated/0/cache");
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache");
            if (f.exists()) {
                Toast.makeText(getApplicationContext(), "toast", Toast.LENGTH_SHORT);
            }
            Uri yourUri = Uri.fromFile(f);
            //    if(yourUri!=null){ Toast.makeText(getApplicationContext(),"vvuy",Toast.LENGTH_SHORT).show();}else{
            //        Toast.makeText(getApplicationContext(),"meri ",Toast.LENGTH_SHORT).show();}
            String message2 = "\" " + message + "\" ";

            if (message.length() == 0) {
                message2 = message;
            }
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            final StorageReference photorefrence = storageReference.child(timeStamp);
            // adding to data base


            final String finalMessage = message2;
            photorefrence.putFile(yourUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    photorefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri uri1 = uri;
                            uri_string = uri1.toString();
                            DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
                            String string_time = dateFormat2.format(Calendar.getInstance().getTime());

                            DatabaseReference ref = message_refrence.push();

                            String time = ref.getKey();
                            FriendlyMessage friendlyMessage =
                                    new FriendlyMessage(finalMessage, username, uri_string, time
                                            , FirebaseAuth.getInstance().getCurrentUser().getUid());
                            ref.setValue(friendlyMessage);
                        }


                    });
                }
            });


        }


        //           List decleration and attatched to adapter

        final List<FriendlyMessage> friendlyMessageArrayList;
        friendlyMessageArrayList = new ArrayList<>();
        //its not working this intent and all
        username = getIntent().getStringExtra("current_user_name");

        if (username == null) {
            username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        }
        Log.d("save me", "onCreate:abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz " + username);
        signedin_(username);
        madapter = new message_display_adapter(list_msgs.getContext(), friendlyMessageArrayList, username);
        list_msgs.setAdapter(madapter);


        //checking if user is log in or not then implementing accrodingly

        //photobutton on click event is to open a folder or something where tthere are lots of images so from there we can select any one
        //   photobutton.setOnClickListener(new View.OnClickListener() {
        //       @Override
        //       public void onClick(View v) {
        //           Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //           intent.setType("image/jpeg");
        //           intent.setType("image/png");
        //           intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        //           try {
        //               username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        //           } catch (Exception e) {
        //               username = "username_is_null";
        //           }
        //           if (!username.equals("username_is_null")) {
        //           startActivityForResult(Intent.createChooser(intent, "Complete action using "), 2112);
        //       }
        //          else {Toast.makeText(getApplicationContext(),"Login_first",Toast.LENGTH_SHORT).show();
        //           Intent intent123= new Intent(all_stuff.this,Authorization.class);
        //           startActivity(intent123);}
        //       }
        //   });

// so as to make sure that your messages are not empty . no wastage of firebase space
        //   editText.addTextChangedListener(new TextWatcher() {
        //       @Override
        //       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //       }
        //       @Override
        //       public void onTextChanged(CharSequence s, int start, int before, int count) {
        //           if (s.toString().trim().length()>0){send_button.setEnabled(true);}
        //           else {
        //               send_button.setEnabled(false);}
        //       }
        //       @Override
        //       public void afterTextChanged(Editable s) {
        //           if(s==null){Toast.makeText(getApplicationContext(),"BODY IS EMPTY",Toast.LENGTH_SHORT).show();}
        //       }
        //   });


// if(condition is always true)
        //  send_button.setOnClickListener(new View.OnClickListener() {
        //      @Override
        //      public void onClick(View v) {
        //          convert_milli_to_usual convert_milli_to_usual=new convert_milli_to_usual(System.currentTimeMillis());
        //          try{username=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();}catch (Exception e){
        //              Toast.makeText(getApplicationContext(),"LOGIN FIRST",Toast.LENGTH_SHORT).show();
        //          }
        //          if(!editText.getText().toString().equals("")){
        //          FriendlyMessage friendlyMessage = new FriendlyMessage(editText.getText().toString(),username,null,convert_milli_to_usual.convert_milli_to_usuals());
        //          if(friendlyMessage.getText()!=null){
        //              try{username=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();}catch (Exception e){username=null;}
        //              if(!(username==null)){
        //                //  message_refrence.push().setValue(friendlyMessage);
        //              }else {
        //                  Intent intent= new Intent(all_stuff.this,Authorization.class);
        //                  startActivity(intent);
        //              }
        //          }
        //          editText.setText(null);
        //      }}
        //  });
    }


    // above two activity gives request code , when these request code get activated then this method work acccordingly
    // @Override
    // protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //     if(requestCode==2112){ if(resultCode==RESULT_OK){
    //         Uri uri = data.getData();
    //         //last path segment because images location detail comes in format like /fies/android/obb/anshul.png where anshul is the name of photo
    //         final StorageReference photorefrence = storageReference.child(uri.getLastPathSegment());
    //         //compressing image
    //         Bitmap bmp = null;
    //         try {
    //             bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //         ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //
    //         //here you can choose quality factor in third parameter(ex. i choosen 25)
    //         bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
    //         byte[] fileInBytes = baos.toByteArray();
    //         // adding to data base
    //         photorefrence.putBytes(fileInBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    //             @Override
    //             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
    //                 photorefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
    //                     @Override
    //                     public void onSuccess(Uri uri) {
    //                         Uri uri1=uri;
    //                         uri_string=uri1.toString();
    //                         convert_milli_to_usual convert_milli_to_usual=new convert_milli_to_usual(System.currentTimeMillis());
    //                         FriendlyMessage friendlyMessage = new FriendlyMessage(null,username, uri_string,convert_milli_to_usual.convert_milli_to_usuals());
    //                         message_refrence.push().setValue(friendlyMessage);
    //                     }
    //
    //
    //                 });
    //             }
    //         });
    //     }}
    //     super.onActivityResult(requestCode, resultCode, data);
    //          //RESULY CODE 2112 ID FOR PHOTO PICKER
    //     // 121 IS FOR authentication
    // }

    @Override
    protected void onResume() {

        Log.e("on resume", "onResume:rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        try {
            message_refrence.removeEventListener(childEventListener);
        } catch (Exception e) {
        }
        //   this.status= "online";
        try {
            username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please Log in to continue", Toast.LENGTH_SHORT).show();
            //  if(count==0){startActivity(new Intent(this,Authorization.class));count++;}
            Intent intent = new Intent(this, main_activity.class);
            startActivity(intent);
        }
        signedin_(username);
        madapter.clear();
        super.onResume();
        madapter.clear();
    }

    @Override
    protected void onStop() {
        Log.e("on stop", "onStop:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        //   this.status="offline";
        madapter.clear();
        super.onStop();
        madapter.clear();
    }

    @Override
    protected void onPause() {
        Log.e("on pause", "onPause: pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
        madapter.clear();
        super.onPause();

        madapter.clear();
        // if we add child event listener two timmes then our msg will gonna appear on list for two times , so we have to remove this method whenevr we add ,this
        //dont automatically get cut off through node
    }

    public void signedin_(final String username) {
        this.username = username;
        childEventListener = new ChildEventListener() {
            // on child added means whenever any new msg arrived in firebase
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("allstuff", dataSnapshot.toString());
                FriendlyMessage friendlyMessageSnapshot = dataSnapshot.getValue(FriendlyMessage.class);

                //    Log.e("ERRORRRRRRRRRR", "onChildAdded: aabsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" );

                madapter.insert(friendlyMessageSnapshot, 0);
                swipeRefreshLayout.setRefreshing(false);


                //        Log.e(status, "online or offline:abbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb " );
                //        com.anshul5404834.rm.Notification notification = new com.anshul5404834.rm.Notification(getApplicationContext());
                //        notification.Notifications();

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
        };
        message_refrence.addChildEventListener(childEventListener);
    }

    public void signedout() {
        madapter.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, main_activity.class);
        startActivity(intent);

    }
}

////ca-app-pub-8775257823411929~8119804698