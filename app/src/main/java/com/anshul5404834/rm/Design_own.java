package com.anshul5404834.rm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Design_own extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetching_quote);
        final String[] body_string = new String[1];
        final String[] head_string = new String[1];
        //defining views
        final EditText body_edit = findViewById(R.id.body_edit_text);
        final EditText message_edit_text = findViewById(R.id.message_edit_text);
        message_edit_text.setHint("Enter Caption ");
        final EditText heading_edit = findViewById(R.id.heading_edit_text);
        Button button = findViewById(R.id.next_to_appear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body_string[0] = body_edit.getText().toString();
                head_string[0] = heading_edit.getText().toString();
                Intent intent = new Intent(Design_own.this, Appearance.class);
                intent.putExtra("BODY", body_string[0]);
                intent.putExtra("HEAD", head_string[0]);
                intent.putExtra("m", message_edit_text.getText().toString());
                if (body_string[0].trim().length() != 0 && head_string[0].trim().length() != 0) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "All Fields are necessary", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
