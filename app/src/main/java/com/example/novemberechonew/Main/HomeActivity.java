package com.example.novemberechonew.Main;
import android.os.Bundle;

import com.example.novemberechonew.Main.Hub.HubFragment;
import com.example.novemberechonew.Main.Trips.TripsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.novemberechonew.Profile.Home_N_AccountFragment;
import com.example.novemberechonew.databinding.ActivityHomeBinding;

import com.example.novemberechonew.R;

public class HomeActivity extends AppCompatActivity {

    @NonNull ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                selectedFragment = new Home_N_AccountFragment();
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.accounts_framelayout, selectedFragment).commit();
            }
            return true;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.accounts_framelayout, new HubFragment()).commit();
    }
}