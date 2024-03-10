package com.example.lab07.adapters;

import androidx.annotation.Nullable;

import com.example.lab07.models.Student;

public interface StudentListViewActionHandler {
    void AddStudentHandler();

    void EditStudentHandler(int position);

    void DeleteStudentHandler(int position);
}
