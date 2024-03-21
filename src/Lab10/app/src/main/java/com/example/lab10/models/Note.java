package com.example.lab10.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.Date;


public class Note  implements Parcelable {
    private int id;
    private String title;
    private String content;
    private Date createdDate;
    public Note() {
        // Default constructor
    }

    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = new Date();
    }

    public Note(int id, String title, String content,Date createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }


    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        createdDate = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeLong(createdDate != null ? createdDate.getTime() : -1L);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
