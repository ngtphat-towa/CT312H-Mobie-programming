package com.example.lab07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lab07.models.Student;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StudentFormActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private AutoCompleteTextView menuClassName;
    private TextInputEditText txtEditStudentId, txtEditStudentName;
    private RadioGroup radioGroupGender;
    private CheckBox checkBoxCompleted, checkBoxB2, checkBoxNotCompleted;
    private Button btnDatePicker, btnSave, btnCancel;

    private List<String> classNames;
    private ArrayAdapter<String> classNamesAdapter;
    private int positionToEdit;
    private Date birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        initializeViews();
        initEvent();
        loadStudentToEdit();
        initClassnameMenu();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.studentFormToolbar);
        menuClassName = findViewById(R.id.autoCompleteClass);
        txtEditStudentId = findViewById(R.id.txtEditStudentId);
        txtEditStudentName = findViewById(R.id.txtEditStudentName);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        checkBoxCompleted = findViewById(R.id.chkBoxCompleteBasicCourse);
        checkBoxB2 = findViewById(R.id.chkBoxB2Certificate);
        checkBoxNotCompleted = findViewById(R.id.chkBoxNotCompleted);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadStudentToEdit() {
        Student studentToEdit = (Student) getIntent().getSerializableExtra("EditStudentData");
        positionToEdit = getIntent().getIntExtra("position", -1);
        if (studentToEdit != null && positionToEdit != -1) {
            updateUIForEditing(studentToEdit);
        }
    }

    private void updateUIForEditing(Student student) {
        menuClassName.setText(student.getClassName());
        txtEditStudentId.setText(student.getStudentId());
        txtEditStudentName.setText(student.getStudentName());
        btnDatePicker.setText(DateTimeUtils.format(student.getBirthDate()));
        radioGroupGender.check(student.getGender().equals("Male") ? R.id.radioButtonMale : R.id.radioButtonFemale);
        checkBoxCompleted.setChecked(student.isCompletedBasicCourse());
        checkBoxB2.setChecked(student.isB2Certificate());
        checkBoxNotCompleted.setChecked(student.isNotCompleted());
        birthDate = student.getBirthDate();
    }

    private void initClassnameMenu() {
        classNames = Student.getExampleClassNames();
        classNamesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classNames);
        menuClassName.setAdapter(classNamesAdapter);
    }

    private void initEvent() {
        btnDatePicker.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDatePicker:
                showDateTimePicker();
                break;
            case R.id.btnSave:
                saveStudent();
                break;
            case R.id.btnCancel:
                cancelOperation();
                break;
        }
    }

    private void saveStudent() {
        String className = menuClassName.getText().toString();
        String studentId = txtEditStudentId.getText().toString();
        String studentName = txtEditStudentName.getText().toString();
        String gender = radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale ? "Male" : "Female";
        String birthDateString = btnDatePicker.getText().toString();

        if (!className.isEmpty() && !studentId.isEmpty() && !studentName.isEmpty()
                && !gender.isEmpty() && !birthDateString.equals(getString(R.string.pick_date))) {
            Student student = new Student(studentId,  studentName, className, birthDate, gender, false, false, true);
            student.setHasCompletedBasicCourse(checkBoxCompleted.isChecked());
            student.setHasB2Certificate(checkBoxB2.isChecked());
            student.setIsNotCompleted(checkBoxNotCompleted.isChecked());

            Intent intent = new Intent();
            intent.putExtra("student", student);
            intent.putExtra("position", positionToEdit);

            setResult(RESULT_OK, intent);

            String message = (positionToEdit != -1) ? "Update success" : "Insert successfully";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Please fill all information", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelOperation() {
        if (positionToEdit != -1) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            clearFields();
        }
    }

    private void clearFields() {
        menuClassName.setText("");
        txtEditStudentId.setText("");
        txtEditStudentName.setText("");
        radioGroupGender.clearCheck();
        checkBoxCompleted.setChecked(false);
        checkBoxB2.setChecked(false);
        checkBoxNotCompleted.setChecked(false);
        btnDatePicker.setText(getString(R.string.pick_date));
        birthDate = null;
    }

    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();
        if (birthDate != null) {
            calendar.setTime(birthDate);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, selectedYear, selectedMonth, selectedDay) -> {
            calendar.set(selectedYear, selectedMonth, selectedDay);
            birthDate = calendar.getTime();
            btnDatePicker.setText(DateTimeUtils.format(birthDate));
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}
