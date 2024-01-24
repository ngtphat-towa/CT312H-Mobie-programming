package com.example.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextBio;
    private RadioGroup radioGroupGender;
    private CheckBox[] checkBoxes;
    private Toast currentToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cache views for efficient access
        editTextBio = findViewById(R.id.editTextBio);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        checkBoxes = new CheckBox[]{
                findViewById(R.id.checkBoxCoding),
                findViewById(R.id.checkBoxGaming),
                findViewById(R.id.checkBoxMemes),
                findViewById(R.id.checkBoxProcrastinating)
        };
    }

    public void onHelloButtonClicked(View view) {
        String name = editTextBio.getText().toString();
        String gender = getSelectedRadioButtonText(radioGroupGender);
        String hobbies = getCheckedHobbiesString(checkBoxes);

        if (currentToast != null) {
            currentToast.cancel();
        }
        String message = String.format("Hi! My name is %s. I am %s and I enjoy %s.", name, gender, hobbies);
        currentToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        currentToast.show();
    }

    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(selectedId);
        return selectedButton != null ? selectedButton.getText().toString() : "";
    }

    private String getCheckedHobbiesString(CheckBox[] checkBoxes) {
        StringBuilder hobbies = new StringBuilder();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                hobbies.append(checkBox.getText()).append(", ");
            }
        }
        if (hobbies.length() > 0) {
            hobbies.delete(hobbies.length() - 2, hobbies.length());
        }
        return hobbies.toString();
    }

}