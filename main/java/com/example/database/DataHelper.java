package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAMES = "names";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMES + " TEXT)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Rename old column
            db.execSQL("ALTER TABLE " + TABLE_USERS +
                    " RENAME COLUMN name TO " + COLUMN_NAMES);
        }
    }

    public void insert(String names) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMES, names);

        long newRowId = db.insert(TABLE_USERS, null, values);
        db.close();
        Log.d("DataHelper", "New user inserted with id: " + newRowId);
    }

    public List<String> selectAll() {
        List<String> users = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_NAMES},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMES));
            users.add(user);
        }
        cursor.close();
        db.close();

        return users;
    }

    public int deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsDeleted = db.delete(TABLE_USERS, null, null);
        db.close();
        Log.d("DataHelper", "Deleted " + rowsDeleted + " rows");

        return rowsDeleted;
    }

    public int deleteUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsDeleted = db.delete(TABLE_USERS, COLUMN_NAMES + " = ?", new String[]{name});
        db.close();
        Log.d("DataHelper", "Deleted " + rowsDeleted + " rows");

        return rowsDeleted;
    }
}
