package com.example.lab07.adapters;

import androidx.annotation.Nullable;

import com.example.lab07.models.Student;

public interface StudentListViewAction {
    void AddStudent(Student student);

    void EditStudent(int position, @Nullable Student updatedStudent);

    void DeleteStudent(int position);
}
