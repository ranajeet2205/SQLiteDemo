package com.ranajeet2205.sqlitedemo;

public class NoteEntry {

    /***
     * Note Table static column names which are used on DatabaseHelper class
     */
    public static final String NOTES_TABLE = "notes";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NOTE_TTITLE = "Note_Title";
    public static final String COLUMN_NOTE_DESC = "Note_Desc";

    /****
     * USER table static column details used in DatabaseHelper class
     */
    public static final String USER_TABLE ="user";
    public static final String USER_COLUMN_ID ="Id";
    public static final String USER_COLUMN_NAME = "user_name";
    public static final String USER_COLUMN_PASSWORD = "user_password";
    public static final String USER_COLUMN_EMAIL = "user_email";
    public static final String USER_COLUMN_MOBILE = "user_mobile";


}
