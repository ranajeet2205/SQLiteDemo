package com.ranajeet2205.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    static DatabaseHelper db;
    List<Note> noteList;
    NoteListAdapter noteListAdapter;
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
        try {
            noteList.addAll(new BackgroundTask1().get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (noteList.size()!=0){
            noteListAdapter = new NoteListAdapter(noteList,this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(noteListAdapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            noItemTextView.setVisibility(View.VISIBLE);
        }


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });

    }


    public static class BackgroundTask1 extends AsyncTask<Void,Void, List<Note>> {

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
        }


    }
}
