package com.example.lab10.data_access;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

import com.example.lab10.models.Note;

import java.util.ArrayList;
import java.util.Date;


public class SQLiteDataAccess extends SQLiteOpenHelper implements INoteRepository {
    private static final String DB_NAME = "NoteDB";
    private static final int DB_VERSION = 2;
    private static final String TABLE_NAME = "notes";
    private static final String ID_COL = "id";
    private static final String TITLE_COL = "title";
    private static final String CONTENT_COL = "content";
    private static final String CREATED_DATE_COL = "created_date";

    public SQLiteDataAccess(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COL + " TEXT, "
                + CONTENT_COL + " TEXT, "
                + CREATED_DATE_COL + " INTEGER)";
        sqLiteDatabase.execSQL(query);
        Log.d("DATABASE", "DATABASE-CREATED");
        // **Sample data insertion (after table creation):**
        addSampleNotes(sqLiteDatabase);
    }
    private void addSampleNotes(SQLiteDatabase sqLiteDatabase) {
        // Create some sample Note objects
        Note note1 = new Note(1,"Grocery List", "Milk, Bread, Eggs", new Date());
        Note note2 = new Note(2,"Important Meeting", "Discuss project deadlines with team.",new Date());

        // Insert each sample note into the database
        for (Note note : new Note[]{note1, note2}) {
            ContentValues values = new ContentValues();
            values.put(TITLE_COL, note.getTitle());
            values.put(CONTENT_COL, note.getContent());
            sqLiteDatabase.insert(TABLE_NAME, null, values);
            Log.d("DATABASE", "Sample note inserted: " + note.getTitle());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void AddNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COL, note.getTitle());
        values.put(CONTENT_COL, note.getContent());
        values.put(CREATED_DATE_COL, new Date().getTime());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public ArrayList<Note> GetAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Note note = new Note();
                // TODO: Should be replaced with getColumnIndex for better mapping
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setCreatedDate(new Date(cursor.getLong(3)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    @Nullable
    @Override
    public Note GetNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID_COL, TITLE_COL, CONTENT_COL, CREATED_DATE_COL}, ID_COL + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Note note = new Note();
            // TODO: Should be replaced with getColumnIndex for better mapping
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            note.setCreatedDate(new Date(cursor.getLong(3)));
            cursor.close();
            return note;
        }
        db.close();
        return null;
    }

    @Override
    public void UpdateNote(int id, @Nullable Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_COL, note.getTitle());
        values.put(CONTENT_COL, note.getContent());
        db.update(TABLE_NAME, values, ID_COL + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public void DeleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COL + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

}
