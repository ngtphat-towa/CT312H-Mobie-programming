package com.example.lab10;

import static androidx.constraintlayout.widget.StateSet.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.lab10.data_access.SQLiteDataAccess;
import com.example.lab10.models.Note;
import com.example.lab10.models.NoteListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView noteListRecyclerView;
    private FloatingActionButton fabAddNewNote;
    private ArrayList<Note> noteArrayList;
    private NoteListAdapter noteListAdapter;
    private SQLiteDataAccess dataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeDataView();
        initializeEventListener();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.note_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note_item_menu_edit:
                noteListAdapter.editNote();
               break;

            case R.id.note_item_menu_delete:
                noteListAdapter.deleteNote();
                break;

        }
        return super.onContextItemSelected(item);
    }
    private void initializeEventListener() {
        fabAddNewNote.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
            startActivity(intent);
        });
    }

    private void initializeDataView() {
        // init database
        dataAccess = new SQLiteDataAccess(this);
        // Set temporary note
        noteArrayList = dataAccess.GetAllNotes();

        // Create adapter and assign to list view
        noteListAdapter = new NoteListAdapter(MainActivity.this, noteArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        noteListRecyclerView.setLayoutManager(linearLayoutManager);

        // Set adapter to recycler view.
        noteListRecyclerView.setAdapter(noteListAdapter);

    }

    private void initializeViews() {
        noteListRecyclerView = findViewById(R.id.noteListRecyclerView);
        fabAddNewNote = findViewById(R.id.fabCreateNewNote);
        // Set context menu for recycler view
        registerForContextMenu(noteListRecyclerView);
    }

}