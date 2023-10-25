package com.example.novemberechonew.Backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "airMonke.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_1_NAME = "user_records";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";

    public static final String CREATE_USER_QUERY = "CREATE TABLE " + TABLE_1_NAME + " ( "
            + USER_ID + " TEXT PRIMARY KEY, "
            + USER_NAME + " TEXT,"
            + USER_EMAIL + " TEXT,"
            + USER_PHONE + " TEXT)";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { //i= old version i1= new version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1_NAME);
        onCreate(db);
    }
}

