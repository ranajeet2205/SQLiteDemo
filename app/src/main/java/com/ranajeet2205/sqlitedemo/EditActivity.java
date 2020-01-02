package com.ranajeet2205.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class EditActivity extends AppCompatActivity {
    EditText title_edit_text,desc_edit_text;
    Button save_btn;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        databaseHelper = new DatabaseHelper(this);

        title_edit_text = findViewById(R.id.title_edit_text);
        desc_edit_text = findViewById(R.id.desc_edit_text);
        save_btn = findViewById(R.id.save_btn);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note_title = title_edit_text.getText().toString().trim();
                String note_Desc = desc_edit_text.getText().toString().trim();

                Note note = new Note(note_title,note_Desc);
                databaseHelper.insertNote(note);

                startActivity(new Intent(EditActivity.this,MainActivity.class));
                finish();

            }
        });
    }



}
