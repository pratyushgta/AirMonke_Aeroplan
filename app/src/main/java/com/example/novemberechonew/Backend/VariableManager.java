package com.example.novemberechonew.Backend;

public class VariableManager {
    private static String DB_flight_num, DB_flight_org, DB_flight_dest, DB_flight_dept_t, DB_flight_arr_t;

    public static String getDB_flight_num() {
        return DB_flight_num;
    }

    public static void setDB_flight_num(String DB_flight_num) {
        VariableManager.DB_flight_num = DB_flight_num;
    }

    public static String getDB_flight_org() {
        return DB_flight_org;
    }

    public static void setDB_flight_org(String DB_flight_org) {
        VariableManager.DB_flight_org = DB_flight_org;
    }

    public static String getDB_flight_dest() {
        return DB_flight_dest;
    }

    public static void setDB_flight_dest(String DB_flight_dest) {
        VariableManager.DB_flight_dest = DB_flight_dest;
    }

    public static String getDB_flight_dept_t() {
        return DB_flight_dept_t;
    }

    public static void setDB_flight_dept_t(String DB_flight_dept_t) {
        VariableManager.DB_flight_dept_t = DB_flight_dept_t;
    }

    public static String getDB_flight_arr_t() {
        return DB_flight_arr_t;
    }

    public static void setDB_flight_arr_t(String DB_flight_arr_t) {
        VariableManager.DB_flight_arr_t = DB_flight_arr_t;
    }
}
