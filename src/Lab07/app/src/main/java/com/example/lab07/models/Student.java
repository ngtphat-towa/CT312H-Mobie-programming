package com.example.lab07.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student implements Serializable {
    private String studentId;
    private String studentName;
    private String className;
    private Date birthDate;
    private String gender;
    private boolean completedBasicCourse;
    private boolean b2Certificate;
    private boolean notCompleted;

    public Student(String studentId,
                   String studentName,
                   String className,
                   Date birthDate,
                   String gender,
                   boolean isCompletedBasicCourse,
                   boolean hasB2Certificate,
                   boolean isNotCompleted) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.className = className;
        this.birthDate = birthDate;
        this.gender = gender;
        this.completedBasicCourse = isCompletedBasicCourse;
        this.b2Certificate = hasB2Certificate;
        this.notCompleted = isNotCompleted;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getClassName() {
        return className;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public boolean isCompletedBasicCourse() {
        return completedBasicCourse;
    }

    public boolean isB2Certificate() {
        return b2Certificate;
    }

    public boolean isNotCompleted() {
        return notCompleted;
    }

    public String getEnglishStatus() {
        StringBuilder englishStatus = new StringBuilder();
        if (notCompleted) {
            englishStatus.append("Have not completed \n");
        } else {
            if (b2Certificate)
                englishStatus.append("Have B2 certificate \n");
            if (completedBasicCourse)
                englishStatus.append("Completed 3 English courses");
        }
        return englishStatus.toString();
    }

    public static List<String> getExampleClassNames() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            result.add(String.format("M0%d", i));
        }
        return result;
    }

    public void setHasCompletedBasicCourse(boolean checked) {
        this.completedBasicCourse = checked;
    }

    public void setHasB2Certificate(boolean checked) {
        this.b2Certificate = checked;
    }

    public void setIsNotCompleted(boolean isNotCompleted) {
        this.notCompleted = isNotCompleted;
        if (isNotCompleted) {
            this.completedBasicCourse = false;
            this.b2Certificate = false;
        }
    }
}
