package com.example.lab10.models;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab10.AddNewNoteActivity;
import com.example.lab10.EditNoteActivity;
import com.example.lab10.R;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {
     private ArrayList<Note> notes;
    private Context context;
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
        holder.itemView.setOnLongClickListener(view -> {
            setPosition(position);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.note_item_title);
            txtContent = itemView.findViewById(R.id.note_item_content);
        }

    }

    public void editNote() {
        Note note = notes.get(position);
        Intent i = new Intent(context, EditNoteActivity.class);
        i.putExtra("id", note.getId());
        i.putExtra("title", note.getTitle());
        i.putExtra("content", note.getContent());
        context.startActivity(i);
    }

    public void deleteNote() {
        Note note  = notes.get(position);
        notes.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(context, "Deleted" + note.getId(), Toast.LENGTH_SHORT).show();
    }
}
