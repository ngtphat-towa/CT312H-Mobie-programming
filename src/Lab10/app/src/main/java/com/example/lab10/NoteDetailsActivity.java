package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lab10.models.DateUtils;
import com.example.lab10.models.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        initializeToolbar();

        // Retrieve the note object from the intent
        Note note = getIntent().getParcelableExtra("note");

        // Display the details of the note
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtContent = findViewById(R.id.txtContent);
        TextView txtCreateDate = findViewById(R.id.txtCreatedDate);

        if (note != null) {
            txtTitle.setText(note.getTitle());
            txtContent.setText(note.getContent());
            txtCreateDate.setText(DateUtils.formatDate(note.getCreatedDate()));
        }
    }
    private void initializeToolbar(){
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_note_details);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}