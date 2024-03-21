package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lab10.models.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        // Retrieve the note object from the intent
        Note note = getIntent().getParcelableExtra("note");

        // Display the details of the note
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtContent = findViewById(R.id.txtContent);

        if (note != null) {
            txtTitle.setText(note.getTitle());
            txtContent.setText(note.getContent());
        }
    }
}