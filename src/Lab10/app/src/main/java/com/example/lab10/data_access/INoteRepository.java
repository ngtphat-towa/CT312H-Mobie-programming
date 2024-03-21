package com.example.lab10.data_access;

import androidx.annotation.Nullable;
import com.example.lab10.models.Note;
import java.util.ArrayList;

public interface INoteRepository {
    public void AddNote(Note note);

    public ArrayList<Note> GetAllNotes();

    public @Nullable Note GetNoteById(int id);

    public void UpdateNote(int id, @Nullable Note note);

    public void DeleteNote(int id);
}
