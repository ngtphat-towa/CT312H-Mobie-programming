package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab10.data_access.SQLiteDataAccess;
import com.example.lab10.models.Note;

public class EditNoteActivity extends AppCompatActivity {

    EditText txtTitle, txtContent;
    Button btnSave, btnCancel;
    SQLiteDataAccess dataAccess;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        initializeToolbar();
        initializeViews();
        initializeData();
        wireUpEventListener();
    }

    private void initializeData() {
        // Retrieve note ID from intent extras
        noteId = getIntent().getIntExtra("noteId", -1);
        if (noteId == -1) {
            // Invalid note ID, finish activity
            finish();
            return;
        }

        // Initialize data access
        dataAccess = new SQLiteDataAccess(this);

        // Retrieve note details from database and populate UI
        Note note = dataAccess.GetNoteById(noteId);
        if (note != null) {
            txtTitle.setText(note.getTitle());
            txtContent.setText(note.getContent());
        }
    }

    private void wireUpEventListener() {
        btnCancel.setOnClickListener(view -> {
            // Finish activity without saving changes
            finish();
        });

        btnSave.setOnClickListener(view -> {
            try {
                // Get updated data from text fields
                String title = txtTitle.getText().toString();
                String content = txtContent.getText().toString();

                // Validate the data
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(EditNoteActivity.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the note in the database
                Note updatedNote = new Note();
                updatedNote.setId(noteId);
                updatedNote.setTitle(title);
                updatedNote.setContent(content);

                dataAccess.UpdateNote(noteId,updatedNote);

                // Set result to indicate success and finish the activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("NoteSaved", true);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
            catch (Exception exception){
                Log.d("DEBUG", "btnSave.setOnClickListener: "+ exception);
            }
        });
    }

    private void initializeViews() {
        txtTitle = findViewById(R.id.txtEditNote_Title);
        txtContent = findViewById(R.id.txtEditNote_Content);
        btnSave = findViewById(R.id.btnSaveEdit);
        btnCancel = findViewById(R.id.btnCancelEdit);
    }

    private void initializeToolbar() {
        // toolbar
        Toolbar toolbar = findViewById(R.id.editToolBar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataAccess != null) {
            dataAccess.close();
        }
    }
}