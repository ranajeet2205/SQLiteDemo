package com.ranajeet2205.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    FloatingActionButton floatingActionButton;
    private  RecyclerView recyclerView;
    static DatabaseHelper db;
    List<Note> noteList;
    private  NoteListAdapter noteListAdapter;
    TextView noItemTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recycler_view);
        noItemTextView = findViewById(R.id.text_view);

        db = new DatabaseHelper(this);
        noteList = new ArrayList<>();

        new AddRecyclerViewAsyncTask().execute();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

    }


    /***
     * That async task is used to get all the notes from database
     * and add it into list and inflate it into recyclerView
     */
    public class AddRecyclerViewAsyncTask extends AsyncTask<Void, Void, List<Note>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            return db.getAllNotes();
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);

            if (notes.size() != 0) {
                noteListAdapter = new NoteListAdapter(notes, MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(noteListAdapter);
            } else {
                recyclerView.setVisibility(View.GONE);
                noItemTextView.setVisibility(View.VISIBLE);
            }
        }


    }
}
