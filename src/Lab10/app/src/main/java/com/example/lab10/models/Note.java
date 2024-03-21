package com.example.lab10.models;

import android.os.Parcel;
import android.os.Parcelable;



public class Note  implements Parcelable {
    private int id;
    private String title;
    private String content;

    public Note() {
        // Default constructor
    }

    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
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
}
