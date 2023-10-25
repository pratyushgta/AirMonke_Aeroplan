package com.example.novemberechonew.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;

public class DBManager {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context ctx) {
        context = ctx;
    }

    public DBManager open() throws SQLDataException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void DB_insertUser(String id, String name, String email, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.USER_ID, id);
        contentValues.put(DBHelper.USER_NAME, name);
        contentValues.put(DBHelper.USER_EMAIL, email);
        contentValues.put(DBHelper.USER_PHONE, phone);
        database.insert(DBHelper.TABLE_1_NAME, null, contentValues);
    }

    public Cursor DB_fetchUser() {
        String[] columns = new String[]{DBHelper.USER_ID, DBHelper.USER_NAME, DBHelper.USER_EMAIL, DBHelper.USER_PHONE};
        Cursor cursor = database.query(DBHelper.TABLE_1_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor DB_fetchFlightByID(String ID) {
        String[] columns = new String[]{
                DBHelper.FLIGHT_ID,
                DBHelper.FLIGHT_ORG,
                DBHelper.FLIGHT_DEST,
                DBHelper.FLIGHT_DEPT_T,
                DBHelper.FLIGHT_ARR_T
        };

        String selection = DBHelper.FLIGHT_ID + " = ?";
        String[] selectionArgs = {ID};

        Cursor cursor = database.query(DBHelper.TABLE_3_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int DB_updateUser(String id, String name, String email, String phone) {
        ContentValues contentValues = new ContentValues();
        //contentValues.put(DBHelper.USER_ID, id);
        contentValues.put(DBHelper.USER_NAME, name);
        contentValues.put(DBHelper.USER_EMAIL, email);
        contentValues.put(DBHelper.USER_PHONE, phone);
        return database.update(DBHelper.TABLE_1_NAME, contentValues, DBHelper.USER_ID + "=" + id, null);
    }

    public void DB_deleteUser(long id) {
        database.delete(DBHelper.TABLE_1_NAME, DBHelper.USER_ID + "=" + id, null);
    }
}
