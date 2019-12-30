package com.ranajeet2205.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.ranajeet2205.sqlitedemo.NoteEntry.COLUMN_ID;
import static com.ranajeet2205.sqlitedemo.NoteEntry.COLUMN_NOTE_DESC;
import static com.ranajeet2205.sqlitedemo.NoteEntry.COLUMN_NOTE_TTITLE;
import static com.ranajeet2205.sqlitedemo.NoteEntry.NOTES_TABLE;
import static com.ranajeet2205.sqlitedemo.NoteEntry.USER_COLUMN_EMAIL;
import static com.ranajeet2205.sqlitedemo.NoteEntry.USER_COLUMN_ID;
import static com.ranajeet2205.sqlitedemo.NoteEntry.USER_COLUMN_MOBILE;
import static com.ranajeet2205.sqlitedemo.NoteEntry.USER_COLUMN_NAME;
import static com.ranajeet2205.sqlitedemo.NoteEntry.USER_COLUMN_PASSWORD;
import static com.ranajeet2205.sqlitedemo.NoteEntry.USER_TABLE;

public class DatabaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "note.db";

    // Create table SQL query for note table
    private static final String CREATE_NOTE_TABLE =

            "CREATE TABLE " + NOTES_TABLE + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE_TTITLE + " TEXT,"
                    + COLUMN_NOTE_DESC + " TEXT"
                    + ")";

    //create table sql query for user table
    private static final String CREATE_USER_TABLE =

            "CREATE TABLE " + USER_TABLE + "("
                    + USER_COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + USER_COLUMN_NAME + "TEXT,"
                    + USER_COLUMN_PASSWORD + "TEXT,"
                    + USER_COLUMN_EMAIL + "TEXT,"
                    + USER_COLUMN_MOBILE + "TEXT"
                    + ")";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_NOTE_TABLE);
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        // Create tables again
        onCreate(db);
    }


    public long insertNote(Note note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_NOTE_TTITLE, note.getTitle());
        values.put(COLUMN_NOTE_DESC, note.getDescription());

        // insert row
        long id = db.insert(NOTES_TABLE, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + NOTES_TABLE + " ORDER BY " +
                COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TTITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_DESC)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }


    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TTITLE, note.getTitle());
        values.put(COLUMN_NOTE_DESC, note.getDescription());


        // updating row
        return db.update(NOTES_TABLE, values, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
    }


    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTES_TABLE, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
        db.close();
    }


    public Note getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NOTES_TABLE,
                new String[]{COLUMN_ID, COLUMN_NOTE_TTITLE, COLUMN_NOTE_DESC},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TTITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_DESC)));

        // close the db connection
        cursor.close();

        return note;
    }

    /***
     * This method helps to insert the user  into user table
     * @param userName
     * @param password
     * @param email
     * @param mobile
     * @return
     */
    public boolean insertUser(String userName, String password, String email, String mobile) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COLUMN_NAME, userName);
        contentValues.put(USER_COLUMN_PASSWORD, password);
        contentValues.put(USER_COLUMN_EMAIL, email);
        contentValues.put(USER_COLUMN_MOBILE, mobile);

        if (!isUserExist(userName, password)){
            database.insert(USER_TABLE, null, contentValues);
            database.close();
            return true;
        }else{
            return  false;
        }

    }

    public boolean isUserExist(String userName, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USER_TABLE, new String[]{USER_COLUMN_NAME, USER_COLUMN_PASSWORD},
                USER_COLUMN_NAME + "=?" + "AND" + USER_COLUMN_PASSWORD + "=?",
                new String[]{userName, password}, null, null, null, null
        );

        return cursor != null && cursor.moveToFirst();

    }

}
