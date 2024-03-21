package com.example.lab10;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.lab10.data_access.SQLiteDataAccess;
import com.example.lab10.models.Note;
import com.example.lab10.models.NoteListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView noteListRecyclerView;
    private FloatingActionButton fabAddNewNote;
    private NoteListAdapter noteListAdapter;
    private SQLiteDataAccess dataAccess;
    ActivityResultLauncher<Intent> noteItemActivityResultLauncher;

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
                editNote();
               break;

            case R.id.note_item_menu_delete:
                deleteNote();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void initializeEventListener() {
        noteItemActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onNoteItemResultHandler
        );

        fabAddNewNote.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
            noteItemActivityResultLauncher.launch(intent);
        });
    }

    private void initializeDataView() {
        // init database
        dataAccess = new SQLiteDataAccess(this);

        // Create adapter and assign to list view
        noteListAdapter = new NoteListAdapter(MainActivity.this, dataAccess.GetAllNotes());
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

    private void onNoteItemResultHandler(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            // Handle the result, update the note list if necessary
            Intent data = result.getData();
            if (data != null) {
                boolean noteAdded = data.getBooleanExtra("NoteSaved", false);
                if (noteAdded) {
                    // Refresh the note list
                    noteListAdapter.setNotes(dataAccess.GetAllNotes());
                }
            }
        }
    }
    private void editNote() {
        // Retrieve the selected note's ID
        int selectedNotePosition = noteListAdapter.getPosition();
        Note selectedNote = noteListAdapter.getNotes().get(selectedNotePosition);

        // Launch EditNoteActivity with the note's ID as an extra
        Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
        intent.putExtra("noteId", selectedNote.getId());
        noteItemActivityResultLauncher.launch(intent);
    }
    private void deleteNote() {
        // Retrieve the selected note's ID
        int selectedNotePosition = noteListAdapter.getPosition();
        Note selectedNote = noteListAdapter.getNotes().get(selectedNotePosition);

        // Delete the note from the database
        dataAccess.DeleteNote(selectedNote.getId());

        // Update the note list in the adapter
        noteListAdapter.setNotes(dataAccess.GetAllNotes());
    }
}