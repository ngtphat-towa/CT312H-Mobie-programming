package com.example.excercise02;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student implements Serializable {
    private String id;
    private String className;
    private String name;
    private Date birthday;
    private String gender;
    private boolean hasCompletedBasicCourse;
    private boolean hasB2Certificate;
    private boolean isNotCompleted;


    public Student(String id, String className, String name, Date birthday, String gender) {
        this.id = id;
        this.className = className;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.hasCompletedBasicCourse = false;
        this.hasB2Certificate = false;
        this.isNotCompleted = true;
    }

    public String getId() {
        return id;
    }


    public String getClassName() {
        return className;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public static List<Student> GetExampleList() {
        List<Student> results = new ArrayList<Student>();
        List<String> classNames = GetExampleClassName();

        for (String className : classNames) {
            // Create birthday in UTC+07:00 directly
            Date birthday = new Date(); // Adjust ZoneId as needed

            results.add((new Student("ID_" + className, className, "Student " + className, new Date(), "Male")));
            results.add(new Student("ID_1" + className, className, "Student " + className, new Date(), "Female"));
        }
        return results;
    }

    public static List<String> GetExampleClassName() {
        List result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            result.add(String.format("M0%d", i));
        }
        return result;
    }

    public String getEnglishStatus() {
        StringBuilder englishStatus = new StringBuilder();
        if (isNotCompleted) {
            englishStatus.append("Have not completed \n");
        } else {
            if (hasB2Certificate)
                englishStatus.append("Have B2 certificate \n");
            if (hasCompletedBasicCourse)
                englishStatus.append("Completed 3 English courses");
        }
        return englishStatus.toString();
    }

    public void setHasB2Certificate(boolean checked) {
        this.hasB2Certificate = checked;
    }

    public void setHasCompletedBasicCourse(boolean checked) {
        this.hasCompletedBasicCourse = checked;

    }

    public void setIsNotCompleted(boolean checked) {
        this.isNotCompleted = checked;
        if (checked == true) {
            this.hasCompletedBasicCourse = false;
            this.hasB2Certificate = false;
        }
    }

    public boolean hasB2Certificate() {
        return hasB2Certificate;
    }

    public boolean hasCompletedBasicCourse() {
        return hasCompletedBasicCourse;
    }

    public boolean isNotCompleted() {
        return isNotCompleted;
    }
}
