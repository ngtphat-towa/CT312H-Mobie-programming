package com.example.lab05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {
    // Private fields for UI elements
    private TextView txtPickedDate;
    private Button btnDatePicker;
    private TextInputEditText studentIdEditText;
    private AutoCompleteTextView studentClassDropdown;
    private TextInputEditText studentNameEditText;
    private RadioGroup genderRadioGroup;
    private CheckBox completedCoursesCheckbox;
    private CheckBox b2CertificateCheckbox;
    private CheckBox notCompletedCheckbox;
    private Button btnCheckResult;
    private Button btnCancelProcess;
    private Toast currentToast;
    private TextView txtResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements once
        initializeUiElements();

        // Set button click listeners
        btnCheckResult.setOnClickListener(v -> onCheckResultClicked());

        btnCancelProcess.setOnClickListener(v -> clearInputFields());
        btnDatePicker.setOnClickListener(v -> showBirthdatePicker());
    }

    private void initializeUiElements() {
        studentIdEditText = findViewById(R.id.txtStudentID);
        studentClassDropdown = findViewById(R.id.autoCompleteClass);
        studentNameEditText = findViewById(R.id.txtStudentName);
        genderRadioGroup = findViewById(R.id.radioGroupGender);
        completedCoursesCheckbox = findViewById(R.id.chkBoxCompleteBasicCourse);
        b2CertificateCheckbox = findViewById(R.id.chkBoxB2Certificate);
        notCompletedCheckbox = findViewById(R.id.chkBoxNotCompleted);
        btnCheckResult = findViewById(R.id.btnCheckResult);
        btnCancelProcess = findViewById(R.id.btnCancelProcess);
        txtPickedDate = findViewById(R.id.txtPickedDate);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        txtResultView = findViewById(R.id.txtResultView);

        // Create an ArrayAdapter with class names
        String[] classNames = {"Class A", "Class B", "Class C", "Class D"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, classNames);
        studentClassDropdown.setAdapter(adapter);

        setDefaultDate();
    }

    private void updatePickedDateText(int year, int month, int dayOfMonth) {
        String pickedDateString = DateFormat.getDateInstance().format(new GregorianCalendar(year, month, dayOfMonth).getTime());
        txtPickedDate.setText(pickedDateString);
    }

    private void showBirthdatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                updatePickedDateText(i, i1, i2);
            }
        },
                // Set initial date to current date
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private  void setDefaultDate(){
        // Set default date to current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        updatePickedDateText(year, month, day);
    }
    private void clearInputFields() {
        // Clear EditText fields
        studentIdEditText.setText("");
        studentNameEditText.setText("");

        // Clear AutoCompleteTextView (class dropdown)
        studentClassDropdown.setText("");

        // Reset RadioGroup (gender)
        genderRadioGroup.clearCheck();
        // Clear birthdate text
        setDefaultDate();

        // Uncheck CheckBoxes (English status)
        completedCoursesCheckbox.setChecked(false);
        b2CertificateCheckbox.setChecked(false);
        notCompletedCheckbox.setChecked(false);
    }

    private void onCheckResultClicked() {
        // Extract user input directly from private fields
        String studentId = studentIdEditText.getText().toString();
        String studentClass = studentClassDropdown.getText().toString();
        String studentName = studentNameEditText.getText().toString();
        String gender = genderRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonMale ? "Male" : "Female";
        boolean completedCourses = completedCoursesCheckbox.isChecked();
        boolean b2Certificate = b2CertificateCheckbox.isChecked();
        boolean notCompleted = notCompletedCheckbox.isChecked();

        // Build result string and display Toast
        if (currentToast != null) {
            currentToast.cancel();

        }
        txtResultView.setVisibility(View.VISIBLE);
        String resultText = formatResultString(studentId, studentClass, studentName, gender, completedCourses, b2Certificate, notCompleted);
        // Store the result in txtResultView
        txtResultView.setText(resultText);
        currentToast = Toast.makeText(this, resultText, Toast.LENGTH_LONG);
        currentToast.show();
    }

    private String formatResultString(String studentId, String studentClass, String studentName, String gender, boolean completedCourses, boolean b2Certificate, boolean notCompleted) {
        String result = "Student ID: " + studentId + "\n";
        result += "Class: " + studentClass + "\n";
        result += "Name: " + studentName + "\n";
        result += "Gender: " + gender + "\n";
        result += "English Status:\n";
        if (completedCourses) {
            result += " - Completed 3 English courses\n";
        }
        if (b2Certificate) {
            result += " - Have B2 certificate\n";
        }
        if (notCompleted) {
            result += " - Have not completed\n";
        }
        return result;
    }

}