package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddNewNoteActivity extends AppCompatActivity {

    EditText txtTitle, txtContent;
    Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       initializeToolbar();
       initializeViews();
       wireUpEventListener();
    }

    private void wireUpEventListener() {

        btnCancel.setOnClickListener(view -> {
            txtTitle.setText("");
            txtContent.setText("");
        });
        btnSave.setOnClickListener(view -> {
            // Get data from controls
            String title = txtTitle.getText().toString();
            String content = txtContent.getText().toString();
            // Validate the data filled
            if (title.isEmpty() & content.isEmpty()){
                Toast.makeText(AddNewNoteActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                return;
            }
            // Init & call the database to call
            // Create toast to display message
            // Return back to main view
        });
    }

    private void initializeViews() {
        txtTitle = findViewById(R.id.txtCreateNote_Title);
        txtContent = findViewById(R.id.txtCreateNote_Content);
        btnSave = findViewById(R.id.btnSaveCreate);
        btnCancel = findViewById(R.id.btnCancelCreate);
    }

    private void initializeToolbar(){
        setContentView(R.layout.activity_add_new_note);
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.addNewToolBar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}