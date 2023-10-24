package com.example.novemberechonew.Backend;

public class VariableManager {
    static Boolean isUserLoggedIn;

    public static Boolean getUserLoggedIn() {
        return isUserLoggedIn;
    }

    public static void setUserLoggedIn(Boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
    }
}
