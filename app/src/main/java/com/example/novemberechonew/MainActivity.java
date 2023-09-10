package com.example.novemberechonew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.novemberechonew.Main.HomeActivity;
import com.example.novemberechonew.Profile.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Display the welcome screen for a few seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUserLoggedIn()) {
                    // User is logged in, navigate to HubFragment
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    // User is not logged in, navigate to LoginActivity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                finish(); // Finish the welcome screen activity
            }
        }, DELAY_MILLIS);
    }

    private boolean isUserLoggedIn() {
        // Check whether the user is logged in (e.g., using SharedPreferences or other storage)
        // Return true if logged in, false if not
        // Replace this logic with your actual implementation
        return false; // Change to true if the user is logged in
    }
}