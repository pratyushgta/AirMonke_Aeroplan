package com.example.novemberechonew.Backend;

public class VariableManager {
    static Boolean account_cancel_welcome;

    public static Boolean getUserLoggedIn() {
        return account_cancel_welcome;
    }

    public static void setUserLoggedIn(Boolean userLoggedIn) {
        account_cancel_welcome = userLoggedIn;
    }
}
