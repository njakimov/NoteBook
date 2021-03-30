package com.e.notebook.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.e.notebook.model.ListNote;
import com.e.notebook.model.Note;

import static com.e.notebook.service.Common.formatDateTimeToStringDb;
import static com.e.notebook.service.Common.formatStringToDate;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_THEME = "theme";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATACREATE = "date_create";
    public static final String COLUMN_DATECHANGE = "date_change";
    public static final String COLUMN_DATEALARM = "date_alarm";
    public static final String COLUMN_FAVORITESTATE = "favorite_state";

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_THEME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_DATACREATE + " TEXT," +
                COLUMN_DATECHANGE + " TEXT," +
                COLUMN_DATEALARM + " TEXT," +
                COLUMN_FAVORITESTATE + " BOOLEAN" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Adding new note
    public Integer addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_THEME, note.getTheme());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_DATACREATE, formatDateTimeToStringDb(note.getDateCreate()));
        values.put(COLUMN_DATECHANGE, formatDateTimeToStringDb(note.getDateChange()));
        values.put(COLUMN_DATEALARM, formatDateTimeToStringDb(note.getDateAlarm()));
        values.put(COLUMN_FAVORITESTATE, note.getFavoriteState());


        // Inserting Row
        Integer rowID = (int) db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return rowID;
    }

    // Edit note
    public Integer editNote(Note note, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_THEME, note.getTheme());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_DATACREATE, formatDateTimeToStringDb(note.getDateCreate()));
        values.put(COLUMN_DATECHANGE, formatDateTimeToStringDb(note.getDateChange()));
        values.put(COLUMN_DATEALARM, formatDateTimeToStringDb(note.getDateAlarm()));
        values.put(COLUMN_FAVORITESTATE, note.getFavoriteState());

        Integer updCount = db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[] { "" + id });
        db.close();
        return updCount;
    }

    // update favorite note
    public Integer updateFavoriteNote(Integer id, Boolean state) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_FAVORITESTATE, state);

        Integer updCount = db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[] { "" + id });
        db.close();
        return updCount;
    }
    // delete favorite note
    public Integer deleteNote(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Integer updCount = db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[] { "" + id });
        db.close();
        return updCount;
    }

    // get list note
    public void getNotes() {
        ListNote notes = ListNote.getInstance();

        SQLiteDatabase db;
        try {
            db = this.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = this.getReadableDatabase();
        }
        Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                boolean favoriteState = false;
                if (c.getInt(6) == 1) {
                    favoriteState = true;
                }
                notes.addNote(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        formatStringToDate(c.getString(3)),
                        formatStringToDate(c.getString(4)),
                        formatStringToDate(c.getString(5)),
                        favoriteState
                );
            } while (c.moveToNext());
        }
    }
}
