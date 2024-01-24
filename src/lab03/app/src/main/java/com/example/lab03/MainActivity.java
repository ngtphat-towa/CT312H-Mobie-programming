package com.example.lab03;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextA;
    private EditText editTextB;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextA = findViewById(R.id.editTextA);
        editTextB = findViewById(R.id.editTextB);
        textViewResult = findViewById(R.id.textViewResult);

        // Set click listeners for buttons
        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperation(MainActivity.this::add);
            }
        });

        findViewById(R.id.buttonSubtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperation(MainActivity.this::subtract);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void handleOperation(Operation operation) {
        String aString = editTextA.getText().toString();
        String bString = editTextB.getText().toString();

        try {
            double a = Double.parseDouble(aString);
            double b = Double.parseDouble(bString);

            double result = operation.apply(a, b);
            textViewResult.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            textViewResult.setText("Please enter valid numbers in both fields.");
        }
    }

    private double add(double a, double b) {
        return a + b;
    }

    private double subtract(double a, double b) {
        return a - b;
    }

    private double multiply(double a, double b) {
        return a * b;
    }

    @SuppressLint("SetTextI18n")
    private double divide(double a, double b) {
        if (b == 0.0) {
            textViewResult.setText("Division by zero is not allowed!");
            return 0.0;
        }
        return a / b;
    }

    private double combine(double a, double b) {
        // Round up each number to its nearest whole number
        int aRounded = (int) Math.ceil(a);
        int bRounded = (int) Math.ceil(b);

        // Concatenate the integer strings and parse as a double
        String combinedString = String.valueOf(aRounded).concat(String.valueOf(bRounded));
        return Double.parseDouble(combinedString);
    }

    public void handleCombine(View view) {
        handleOperation(this::combine);
    }

    public void handleDivide(View view) {
        handleOperation(this::divide);
    }

    public void handleMultiply(View view) {
        handleOperation(this::multiply);
    }

    interface Operation {
        double apply(double a, double b);
    }
}
