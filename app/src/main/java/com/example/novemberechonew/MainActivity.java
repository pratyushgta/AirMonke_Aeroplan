package com.example.novemberechonew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.novemberechonew.Main.HomeActivity;
import com.example.novemberechonew.Profile.Welcome_AccountActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 5000; // 5 seconds

    FirebaseAuth mAuth;
    Boolean isUserLoggedIn = false;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //Intent home = new Intent(getContext(), HomeActivity.class);
           //startActivity(home);
            isUserLoggedIn = true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        // Display the welcome screen for a few seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUserLoggedIn) {
                    // User is logged in, navigate to HubFragment
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    // User is not logged in, navigate to LoginActivity
                    startActivity(new Intent(MainActivity.this, Welcome_AccountActivity.class));
                }
                finish(); // Finish the welcome screen activity
            }
        }, DELAY_MILLIS);
    }

   /* private boolean isUserLoggedIn() {
        // Check whether the user is logged in (e.g., using SharedPreferences or other storage)
        // Return true if logged in, false if not
        // Replace this logic with your actual implementation
        return false; // Change to true if the user is logged in
    }*/
}