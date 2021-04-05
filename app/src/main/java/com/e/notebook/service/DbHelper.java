package com.e.notebook.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_NAME = "id";
    public static final String COLUMN_THEME = "theme";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATACREATE = "date_create";
    public static final String COLUMN_DATECHANGE = "date_change";
    public static final String COLUMN_DATEALARM = "date_alarm";
    public static final String COLUMN_FAVORITESTATE = "favorite_state";

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + "TEXT, " +
                COLUMN_THEME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_DATACREATE + " DATETIME," +
                COLUMN_DATECHANGE + " DATETIME," +
                COLUMN_DATEALARM + " DATETIME," +
                COLUMN_FAVORITESTATE + " BOOLEAN" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
