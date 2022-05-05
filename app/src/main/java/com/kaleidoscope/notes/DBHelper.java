package com.kaleidoscope.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "notes.db";
    private static final int VERSION = 2;

    public static String getDbName() {
        return DB_NAME;
    }

    public static int getVERSION() {
        return VERSION;
    }

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesContract.NotesEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NotesContract.NotesEntry.DROP_COMMAND);
        onCreate(db);
    }
}
