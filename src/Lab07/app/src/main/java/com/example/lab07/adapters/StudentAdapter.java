package com.example.lab07.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lab07.DateTimeUtils;
import com.example.lab07.R;
import com.example.lab07.models.Student;

import java.util.List;

public class StudentAdapter extends BaseAdapter {

    private List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.txtStudentName);
            viewHolder.studentIdTextView = convertView.findViewById(R.id.txtStudent);
            viewHolder.studentClassnameTextView = convertView.findViewById(R.id.txtStudentClassname);
            viewHolder.birthDateTextView = convertView.findViewById(R.id.txtStudentBirthDate);
            viewHolder.genderTextView = convertView.findViewById(R.id.txtStudentGender);
            viewHolder.englishStatusTextView = convertView.findViewById(R.id.txtStudentEnglishStatus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Student student = students.get(position);
        viewHolder.nameTextView.setText(student.getStudentName());
        viewHolder.studentIdTextView.setText(student.getStudentId());
        viewHolder.studentClassnameTextView.setText(student.getClassName());
        viewHolder.birthDateTextView.setText(DateTimeUtils.format(student.getBirthDate()));
        viewHolder.genderTextView.setText(student.getGender());
        viewHolder.englishStatusTextView.setText(student.getEnglishStatus());

        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView studentIdTextView;
        TextView studentClassnameTextView;
        TextView birthDateTextView;
        TextView genderTextView;
        TextView englishStatusTextView;
    }

    public void AddStudent(Student student) {
        students.add(student);
        notifyDataSetChanged();
    }

    public void EditStudent(int position, Student updatedStudent) {
        students.set(position, updatedStudent);
        notifyDataSetChanged();
    }

    public void DeleteStudent(int position) {
        students.remove(position);
        notifyDataSetChanged();
    }
}
