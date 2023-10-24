package com.example.novemberechonew.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novemberechonew.Main.Trips.BookFragment;
import com.example.novemberechonew.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home_Y_AccountFragment extends Fragment {
    Button logout;
    TextView name, phone, email;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home__y__account, container, false);

        logout = view.findViewById(R.id.yAccount_logout);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
        if (fire_user == null) {
            Intent login = new Intent(getContext(), Welcome_AccountActivity.class);
            startActivity(login);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logged out!",
                        Toast.LENGTH_SHORT).show();
                Intent login = new Intent(getContext(), Welcome_AccountActivity.class);
                startActivity(login);
            }
        });


        return view;
    }
}