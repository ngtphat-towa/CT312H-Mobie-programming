package com.example.excercise02;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StudentFormActivity extends AppCompatActivity {
    private AutoCompleteTextView menuClassName;
    private TextInputEditText txtEditStudentId, txtEditStudentName;
    private RadioGroup radioGroupGender;
    private CheckBox checkBoxCompleted, checkBoxB2, checkBoxNotCompleted;
    private Button btnDatePicker, btnSave, btnCancel;

    private List<String> classNames;
    private int positionToEdit;
    private Date birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        initView();
        initEvent();

        // Check if there's a student to edit
        Student studentToEdit = (Student) getIntent().getSerializableExtra("studentToEdit");
        positionToEdit = getIntent().getIntExtra("position", -1);

        if (studentToEdit != null && positionToEdit != -1) {
            // Update UI with existing student data for editing
            updateUIForEditing(studentToEdit, positionToEdit);
        }

        initData();

    }

    private void initEvent() {
        btnDatePicker.setOnClickListener(this::showDateTimePicker);
        btnSave.setOnClickListener(view -> {
            // Student information
            String className = menuClassName.getText().toString();
            String studentId = txtEditStudentId.getText().toString();
            String studentName = txtEditStudentName.getText().toString();
            String gender = getGender();
            String birthDateString = btnDatePicker.getText().toString();

            // Check if all information is filled
            if (!className.isEmpty() && !studentId.isEmpty() && !studentName.isEmpty()
                    && !gender.isEmpty() && !birthDateString.equals("Pick date")) {
                Student student = new Student(studentId, className, studentName, birthDate, gender);

                // Set English status based on checkbox values
                student.setHasCompletedBasicCourse(checkBoxCompleted.isChecked());
                student.setHasB2Certificate(checkBoxB2.isChecked());
                student.setIsNotCompleted(checkBoxNotCompleted.isChecked());

                Intent intent = new Intent();
                intent.putExtra("student", student);
                intent.putExtra("position", positionToEdit);

                setResult(RESULT_OK, intent);

                String message = (positionToEdit != -1) ? "Update success " : "Insert successfully";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                finish();

            } else {
                Toast.makeText(this, "Please fill all information", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(view -> {
            // If editing an existing student, cancel without updating
            if (positionToEdit != -1) {
                setResult(RESULT_CANCELED);
                finish();
            } else {
                // If adding a new student, reset all fields
                resetFields();
            }
        });
    }

    private void resetFields() {
        menuClassName.setText(""); // Clear the class name
        txtEditStudentId.setText(""); // Clear student ID
        txtEditStudentName.setText(""); // Clear student name
        radioGroupGender.clearCheck(); // Clear gender selection
        checkBoxCompleted.setChecked(false); // Uncheck completion status
        checkBoxB2.setChecked(false); // Uncheck B2 certificate status
        checkBoxNotCompleted.setChecked(false); // Uncheck not completed status
        // Reset date picker to default text
        btnDatePicker.setText(getString(R.string.pick_date));
        birthDate = null; // Reset birth date
    }

    private void updateUIForEditing(Student studentToEdit, int positionToEdit) {
        // Update UI elements with existing data

        String className = studentToEdit.getClassName();
        menuClassName.setText(className);

        txtEditStudentId.setText(studentToEdit.getId());
        txtEditStudentName.setText(studentToEdit.getName());


        // Set the existing date in the date picker
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(studentToEdit.getBirthday());

        // Update both birthDate and button text using DateUtils
        birthDate = calendar.getTime();
        btnDatePicker.setText(DateTimeUtils.format(birthDate));

        // Set gender based on student's gender value
        if (studentToEdit.getGender().equals("Male")) {
            radioGroupGender.check(R.id.radioButtonMale);
        } else {
            radioGroupGender.check(R.id.radioButtonFemale);
        }

        // Set English status checkboxes based on student's status
        checkBoxCompleted.setChecked(studentToEdit.hasCompletedBasicCourse());
        checkBoxB2.setChecked(studentToEdit.hasB2Certificate());
        checkBoxNotCompleted.setChecked(studentToEdit.isNotCompleted());
    }

    @NonNull
    private String getGender() {
        StringBuilder gender = new StringBuilder();
        if (findViewById(R.id.radioButtonMale).isSelected())
            gender.append("Male");
        else gender.append("Female");
        return gender.toString();
    }

    private String getEnglishStatus() {
        StringBuilder englishStatus = new StringBuilder();
        if (checkBoxNotCompleted.isChecked()) {
            englishStatus.append(checkBoxNotCompleted.getText().toString());
        } else {
            if (checkBoxCompleted.isChecked())
                englishStatus.append(checkBoxCompleted.getText().toString());
            if (checkBoxB2.isChecked())
                englishStatus.append(checkBoxB2.getText().toString());
        }
        return englishStatus.toString();
    }


    private void initData() {
        classNames = Student.GetExampleClassName();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, classNames);
        menuClassName.setAdapter(adapter);
    }

    private void initView() {
        // Student info
        menuClassName = findViewById(R.id.autoCompleteClass);
        txtEditStudentId = findViewById(R.id.txtEditStudentId);
        txtEditStudentName = findViewById(R.id.txtEditStudentName);
        // Date & gender
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        // English status
        checkBoxCompleted = findViewById(R.id.chkBoxCompleteBasicCourse);
        checkBoxB2 = findViewById(R.id.chkBoxB2Certificate);
        checkBoxNotCompleted = findViewById(R.id.chkBoxNotCompleted);
        // Action button
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

    }


    private void showDateTimePicker(View v) {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // If a birth date is already set, use that for pre-filling
        if (birthDate != null) {
            calendar.setTime(birthDate);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        }

        // Create Date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, selectedYear, selectedMonth, selectedDay) -> {
            // Update calendar and get date string
            calendar.set(selectedYear, selectedMonth, selectedDay);
            birthDate = calendar.getTime();
            btnDatePicker.setText(DateTimeUtils.format(birthDate));
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

}