package com.ranajeet2205.sqlitedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    private final Context context;
    private List<Note> items;

    public NoteListAdapter(List<Note> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note item = items.get(position);
        holder.set(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView note_title,note_desc;

        public NoteViewHolder(View itemView) {
            super(itemView);
            note_title = itemView.findViewById(R.id.note_title);
            note_desc = itemView.findViewById(R.id.note_desc);
        }

        public void set(Note item) {
            //UI setting code
            note_title.setText(item.getTitle());
            note_desc.setText(item.getDescription());
        }
    }
}