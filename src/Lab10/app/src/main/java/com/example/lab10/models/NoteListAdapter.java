package com.example.lab10.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab10.NoteDetailsActivity;
import com.example.lab10.R;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
     private ArrayList<Note> notes;
    private final Context context;
    private int position;

    public NoteListAdapter(Context context,ArrayList<Note> notes) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Binding the data with position
        Note note = notes.get(position);
        // Set data holder
        holder.txtTitle.setText(note.getTitle());
        holder.txtContent.setText(note.getContent());
        holder.txtCreatedDate.setText(DateUtils.formatDate(note.getCreatedDate()));
        holder.itemView.setOnLongClickListener(view -> {
            setPosition(position);
            return false;
        });
        // Set OnClickListener to open NoteDetailsActivity when item clicked
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("note", note);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public int getPosition() {
        return position;
    }
    public  void  setNotes(ArrayList<Note> notes){
        this.notes = notes;
        this.notifyDataSetChanged();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<Note> getNotes() {
        return this.notes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle, txtContent, txtCreatedDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.note_item_title);
            txtContent = itemView.findViewById(R.id.note_item_content);
            txtCreatedDate = itemView.findViewById(R.id.note_item_created_date);
        }

    }
}
