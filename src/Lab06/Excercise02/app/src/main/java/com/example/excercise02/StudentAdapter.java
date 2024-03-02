package com.example.excercise02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends BaseAdapter {

    private List<Student> list = new ArrayList<>();
    private OnStudentCardItemClickListener mListener;
    public StudentAdapter(List<Student> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = list.get(position);
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.student_card_item_layout, parent, false);
        } else view = convertView;

        ((TextView)view.findViewById(R.id.txtViewStudentId)).setText(student.getId());
        ((TextView)view.findViewById(R.id.txtViewStudentClassName)).setText(student.getClassName());
        ((TextView)view.findViewById(R.id.txtViewStudentName)).setText(student.getName());
        ((TextView)view.findViewById(R.id.txtViewStudentGender)).setText(student.getGender());
        ((TextView)view.findViewById(R.id.txtViewStudentBirthDate)).setText(DateTimeUtils.format(student.getBirthday()));
        ((TextView)view.findViewById(R.id.txtViewStudentEnglishStatus)).setText(student.getEnglishStatus());

        // Edit Button
        ImageButton btnEdit = view.findViewById(R.id.btnEditStudentInfo);
        btnEdit.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onEditClicked(position);
            }
        });

        // Delete Button
        ImageButton btnDelete = view.findViewById(R.id.btnDeleteStudentInfo);
        btnDelete.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onDeleteClicked(position);
            }
        });
        return view;
    }
    public void setOnStudentCardItemClickListener(OnStudentCardItemClickListener listener) {
        mListener = listener;
    }
    public interface OnStudentCardItemClickListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }

}
