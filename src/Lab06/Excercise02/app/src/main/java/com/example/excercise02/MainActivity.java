package com.example.excercise02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements StudentAdapter.OnStudentCardItemClickListener {
    private List<Student> studentList;
    private ListView listView;
    private StudentAdapter studentAdapter;
    private FloatingActionButton btnAdd;

    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // UI
        listView = findViewById(R.id.listViewStudentInfo);
        btnAdd = findViewById(R.id.fabAddNewStudent);
        // Init the list and adapter
        studentList = Student.GetExampleList();
        studentAdapter = new StudentAdapter(studentList);
        // Set adapter
        listView.setAdapter(studentAdapter);
        studentAdapter.setOnStudentCardItemClickListener(this);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            Student student = (Student) intent.getSerializableExtra("student");
                            int editedPosition = intent.getIntExtra("position", -1);

                            if (editedPosition != -1) {
                                // Update the existing student in the list
                                studentList.set(editedPosition, student);
                            } else {
                                // Add a new student to the list
                                studentList.add(student);
                            }

                            studentAdapter.notifyDataSetChanged();
                        } else {
                            // Handle case where intent is null
                            Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        // Handle cancellation
                        // For now, do nothing
                    }
                }
        );
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentFormActivity.class);
            launcher.launch(intent);
        });

    }

    @Override
    public void onEditClicked(int position) {
        // Handle edit action
        Student studentToEdit = studentList.get(position);
        Intent intent = new Intent(this, StudentFormActivity.class);
        intent.putExtra("studentToEdit", studentToEdit);
        intent.putExtra("position", position);
        launcher.launch(intent);
    }

    @Override
    public void onDeleteClicked(int position) {
        // Handle delete action
        studentList.remove(position);
        studentAdapter.notifyDataSetChanged();
    }
}