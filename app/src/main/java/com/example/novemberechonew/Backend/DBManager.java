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

    public Cursor DB_fetchBookingByID(String userId) {
        String[] projection = {
                DBHelper.PAX_ID,
                DBHelper.PAX_PNR,
                DBHelper.PAX_NAME,
                DBHelper.PAX_EMAIL,
                DBHelper.PAX_DOB,
                DBHelper.PAX_PHONE
        };

        String selection = DBHelper.USER_ID + " = ?";
        String[] selectionArgs = {userId};

        return database.query(DBHelper.TABLE_4_NAME, projection, selection, selectionArgs, null, null, null);
    }

    public long DB_insertUserBooking(String paxId, String paxPnr, String paxName, String paxEmail, String paxDob, String paxPhone) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.PAX_ID, paxId);
        values.put(DBHelper.PAX_PNR, paxPnr);
        values.put(DBHelper.PAX_NAME, paxName);
        values.put(DBHelper.PAX_EMAIL, paxEmail);
        values.put(DBHelper.PAX_DOB, paxDob);
        values.put(DBHelper.PAX_PHONE, paxPhone);

        long newRowId = database.insert(DBHelper.TABLE_4_NAME, null, values);
        database.close();

        return newRowId;
    }

    public Cursor DB_fetchFlightByDestinationOrigin(String dest, String org) {
        String[] columns = new String[]{
                DBHelper.FLIGHT_ID,
                DBHelper.FLIGHT_ORG,
                DBHelper.FLIGHT_DEST,
                DBHelper.FLIGHT_DEPT_T,
                DBHelper.FLIGHT_ARR_T
        };

        // Define a selection string with placeholders for the origin and destination
        String selection = DBHelper.FLIGHT_ORG + " = ? AND " + DBHelper.FLIGHT_DEST + " = ?";
        String[] selectionArgs = {org, dest};

        // Query the database with the specified selection and selectionArgs
        Cursor cursor = database.query(DBHelper.TABLE_3_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor DB_fetchAirport() {
        String[] columns = new String[]{DBHelper.AIRPORT_ID, DBHelper.AIRPORT_NAME, DBHelper.AIRPORT_ICAO, DBHelper.AIRPORT_CITY, DBHelper.AIRPORT_COUNTRY};
        Cursor cursor = database.query(DBHelper.TABLE_2_NAME, columns, null, null, null, null, null);
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
