package com.example.novemberechonew.Main;

import android.os.Bundle;

import com.example.novemberechonew.Main.Hub.HubFragment;
import com.example.novemberechonew.Main.Trips.TripsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.novemberechonew.Profile.Home_N_AccountFragment;
import com.example.novemberechonew.Profile.Home_Y_AccountFragment;
import com.example.novemberechonew.databinding.ActivityHomeBinding;

import com.example.novemberechonew.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {


    @NonNull
    ActivityHomeBinding binding;
    FirebaseAuth mAuth;
    Boolean isUserLoggedIn = false;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            isUserLoggedIn = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
        if (fire_user != null) {
            isUserLoggedIn = true;
        }
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.hub) {
                selectedFragment = new HubFragment();
            } else if (item.getItemId() == R.id.trips) {
                selectedFragment = new TripsFragment();
            } else if (item.getItemId() == R.id.maps) {
                selectedFragment = new MapsFragment();
            } else if (item.getItemId() == R.id.account) {
                if (isUserLoggedIn && fire_user != null) {
                    selectedFragment = new Home_Y_AccountFragment();
                } else {
                    selectedFragment = new Home_N_AccountFragment();
                }
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.accounts_framelayout, selectedFragment).commit();
            }
            return true;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.accounts_framelayout, new HubFragment()).commit();
    }
}