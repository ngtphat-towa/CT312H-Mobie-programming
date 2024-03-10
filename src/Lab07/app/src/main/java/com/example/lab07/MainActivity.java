package com.example.lab07;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.MenuItem;

import com.example.lab07.adapters.StudentAdapter;
import com.example.lab07.adapters.StudentListViewActionHandler;
import com.example.lab07.models.Student;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements StudentListViewActionHandler {
    private static final int REQUEST_CODE_ADD_TASK = 1;
    private static final int REQUEST_CODE_EDIT_TASK = 2;

    private ArrayList<Student> students;
    private StudentAdapter studentAdapter;
    private ListView listView;
    private Button addNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setListeners();
        setupListView();
    }

    private void initializeViews() {
        listView = findViewById(R.id.studentListView);
        addNew = findViewById(R.id.btnAddNew);
    }

    private void setListeners() {
        addNew.setOnClickListener(view -> AddStudentHandler());
        registerForContextMenu(listView);
    }

    private void setupListView() {
        students = new ArrayList<>();
        students.add(new Student("1", "Name 1", "M01", new Date(),
                "Male", false, false, true));
        students.add(new Student("2", "Name 2", "M02", new Date(),
                "Male", false, false, true));
        students.add(new Student("3", "Name 3", "M03", new Date(),
                "Male", false, false, true));

        studentAdapter = new StudentAdapter(students);
        listView.setAdapter(studentAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.setHeaderTitle("Select The Action");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {
            case R.id.action_delete:
                DeleteStudentHandler(position);
                return true;
            case R.id.action_edit:
                EditStudentHandler(position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Student student = (Student) data.getSerializableExtra("student");
            int position = data.getIntExtra("position", -1);
            if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK) {
                if (student != null) {
                    studentAdapter.AddStudent(student);
                }
            } else if (requestCode == REQUEST_CODE_EDIT_TASK && resultCode == RESULT_OK && position != -1 && student != null) {
                studentAdapter.EditStudent(position, student);
            }
        }
    }


    @Override
    public void AddStudentHandler() {
        Intent intent = new Intent(MainActivity.this, StudentFormActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_TASK);
    }

    @Override
    public void EditStudentHandler(int position) {
        Intent intent = new Intent(MainActivity.this, StudentFormActivity.class);
        intent.putExtra("EditStudentData", students.get(position));
        intent.putExtra("position", position);
        startActivityForResult(intent, REQUEST_CODE_EDIT_TASK);
    }

    @Override
    public void DeleteStudentHandler(int position) {
        studentAdapter.DeleteStudent(position);
        Toast.makeText(this, "Student Deleted", Toast.LENGTH_SHORT).show();
    }
}
