package com.example.lab10;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lab10.data_access.SQLiteDataAccess;
import com.example.lab10.models.Note;

public class AddNewNoteActivity extends AppCompatActivity {

    EditText txtTitle, txtContent;
    Button btnSave, btnCancel;
    SQLiteDataAccess dataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
       initializeToolbar();
       initializeViews();
       wireUpEventListener();
    }

    private void wireUpEventListener() {
        btnCancel.setOnClickListener(view -> {
            // Clear text fields and return to MainActivity without adding a new note
            setResult(RESULT_CANCELED);
            finish();
        });

        btnSave.setOnClickListener(view -> {
            // Get data from text fields
            String title = txtTitle.getText().toString();
            String content = txtContent.getText().toString();

            // Validate the data
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(AddNewNoteActivity.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add the new note to the database
            Note newNote = new Note();
            newNote.setTitle(title);
            newNote.setContent(content);

            dataAccess.AddNote(newNote);

            // Set result to indicate success and finish the activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("NoteSaved", true);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void initializeViews() {
        txtTitle = findViewById(R.id.txtCreateNote_Title);
        txtContent = findViewById(R.id.txtCreateNote_Content);
        btnSave = findViewById(R.id.btnSaveCreate);
        btnCancel = findViewById(R.id.btnCancelCreate);
        // Data access
        dataAccess = new SQLiteDataAccess(this);
    }

    private void initializeToolbar(){

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.addNewToolBar);
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
        dataAccess.close();
    }
}