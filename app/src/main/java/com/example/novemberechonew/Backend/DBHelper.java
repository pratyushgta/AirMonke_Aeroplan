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
    public static final String USER_ID = "u_id";
    public static final String USER_NAME = "u_name";
    public static final String USER_EMAIL = "u_email";
    public static final String USER_PHONE = "u_phone";

    public static final String TABLE_2_NAME = "airport_records";
    public static final String AIRPORT_ID = "a_id";
    public static final String AIRPORT_NAME = "a_name";
    public static final String AIRPORT_ICAO = "a_icao";
    public static final String AIRPORT_CITY = "a_city";
    public static final String AIRPORT_COUNTRY = "a_country";

    public static final String TABLE_3_NAME = "flight_records";
    public static final String FLIGHT_ID = "f_id";
    public static final String FLIGHT_ORG = "a_org";
    public static final String FLIGHT_DEST = "a_dest";
    public static final String FLIGHT_DEPT_T = "a_dept_t";
    public static final String FLIGHT_ARR_T = "a_arr_t";

    public static final String TABLE_4_NAME = "pax_records";
    public static final String PAX_ID = "p_id";
    public static final String PAX_PNR = "p_pnr";
    public static final String PAX_NAME = "p_name";
    public static final String PAX_EMAIL = "p_email";
    public static final String PAX_DOB = "p_dob";
    public static final String PAX_PHONE = "p_phone";

    public static final String CREATE_USER_QUERY = "CREATE TABLE " + TABLE_1_NAME + " ( "
            + USER_ID + " TEXT PRIMARY KEY, "
            + USER_NAME + " TEXT,"
            + USER_EMAIL + " TEXT,"
            + USER_PHONE + " TEXT)";


    public static final String CREATE_AIRPORT_QUERY = "CREATE TABLE " + TABLE_2_NAME + " ( "
            + AIRPORT_ID + " TEXT PRIMARY KEY, "
            + AIRPORT_NAME + " TEXT,"
            + AIRPORT_ICAO + " TEXT,"
            + AIRPORT_CITY + " TEXT,"
            + AIRPORT_COUNTRY + " TEXT)";

    public static final String CREATE_FLIGHT_QUERY = "CREATE TABLE " + TABLE_3_NAME + " ( "
            + FLIGHT_ID + " TEXT PRIMARY KEY, "
            + FLIGHT_ORG + " TEXT,"
            + FLIGHT_DEST + " TEXT,"
            + FLIGHT_DEPT_T + " TEXT,"
            + FLIGHT_ARR_T + " TEXT)";

    public static final String CREATE_PAX_QUERY = "CREATE TABLE " + TABLE_4_NAME + " ( "
            + PAX_ID + " TEXT PRIMARY KEY, "
            + PAX_PNR + " TEXT,"
            + PAX_NAME + " TEXT,"
            + PAX_EMAIL + " TEXT,"
            + PAX_DOB + " TEXT,"
            + PAX_PHONE + " TEXT)";

    // Additional SQL statements to insert initial data
    public static final String INSERT_FLIGHT_DATA =
            "INSERT INTO " + TABLE_3_NAME + " (" + FLIGHT_ID + ", " + FLIGHT_ORG + ", " + FLIGHT_DEST + ", " + FLIGHT_DEPT_T + ", " + FLIGHT_ARR_T + ") VALUES " +
                    "('AM52','Hong Kong', 'Singapore', '23:59', '04:15'), " +
                    "('AM46','Hong Kong', 'Singapore', '08:00', '13:00'), " +
                    "('AM51','Singapore', 'Hong Kong', '14:30', '19:00'), " +
                    "('AM39','Singapore', 'Hong Kong', '05:30', '10:00'), " +
                    "('AM63','Hong Kong', 'Tokyo', '13:15', '18:00');";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_QUERY);
        db.execSQL(CREATE_AIRPORT_QUERY);
        db.execSQL(CREATE_FLIGHT_QUERY);
        db.execSQL(CREATE_PAX_QUERY);

        db.execSQL(INSERT_FLIGHT_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { //i= old version i1= new version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_3_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_4_NAME);

        onCreate(db);
    }
}

