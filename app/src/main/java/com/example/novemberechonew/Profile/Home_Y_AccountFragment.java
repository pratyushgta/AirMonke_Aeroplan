package com.example.novemberechonew.Profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novemberechonew.Backend.DataClass;
import com.example.novemberechonew.Main.Trips.BookFragment;
import com.example.novemberechonew.Main.Trips.FlightStatusFragment;
import com.example.novemberechonew.Main.Trips.MyBookings_N_Fragment;
import com.example.novemberechonew.Main.Trips.MyBookings_Y_Fragment;
import com.example.novemberechonew.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home_Y_AccountFragment extends Fragment {
    Button logout;
    TextView name, phone, email, card2text, card3text, miles, dob;
    ImageView card2Image, card3Image;
    Button card2Button, card3Button;
    FirebaseAuth mAuth;
    Boolean isUserLoggedIn = false;
    List<DataClass> dataList;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    AlertDialog alertDialog;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            isUserLoggedIn = true;
            fetchAndDisplayUserData(currentUser.getUid());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home__y__account, container, false);

        logout = view.findViewById(R.id.yAccount_logout);
        card2text = view.findViewById(R.id.yAccount_card_text);
        card3text = view.findViewById(R.id.yAccount_card2_text);
        card2Button = view.findViewById(R.id.yAccount_button);
        card3Button = view.findViewById(R.id.yAccount2_button);
        card2Image = view.findViewById(R.id.yAccount_card_image);
        card3Image = view.findViewById(R.id.yAccount_card2_image);

        name = view.findViewById(R.id.yAccount_name);
        phone = view.findViewById(R.id.yAccount_phone);
        email = view.findViewById(R.id.yAccount_email);
        dob = view.findViewById(R.id.yAccount_dob);
        miles = view.findViewById(R.id.yAccount_miles_Description1);


        card2Image.setImageResource(R.drawable.hub_planeicon_d);
        card2text.setText("Manage Booking");
        card2Button.setText("Check In >");
        card3Image.setImageResource(R.drawable.hub_exploreicon_d);
        card3text.setText("Check flight status");
        card3Button.setText("Track >");

        card2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
                if (fire_user != null) {
                    MyBookings_Y_Fragment myBookings_y_fragment = new MyBookings_Y_Fragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.accounts_framelayout, myBookings_y_fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setSelectedItemId(R.id.trips);
                } else {
                    MyBookings_N_Fragment myBookings_y_fragment = new MyBookings_N_Fragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.accounts_framelayout, myBookings_y_fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setSelectedItemId(R.id.trips);
                }
            }
        });

        card3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlightStatusFragment flightStatusFragment = new FlightStatusFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.accounts_framelayout, flightStatusFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.trips);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
        if (fire_user == null) {
            Intent login = new Intent(getContext(), Welcome_AccountActivity.class);
            startActivity(login);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();


        dataList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("AirMonke_UserData");
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

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

    private void fetchAndDisplayUserData(String userId) {
        DatabaseReference userRef = databaseReference.child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataClass userData = snapshot.getValue(DataClass.class);

                    // Set the values in the TextView elements
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    name.setText(currentUser.getDisplayName());
                    email.setText(userData.getDataEmail());
                    phone.setText(userData.getDataPhone());
                    miles.setText(userData.getDataMiles());
                    dob.setText(userData.getDataDOB());
                    // Dismiss the progress dialog
                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                Toast.makeText(getContext(), "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}