package com.example.novemberechonew.Main.Trips;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novemberechonew.Backend.DBHelper;
import com.example.novemberechonew.Backend.DBManager;
import com.example.novemberechonew.Backend.VariableManager;
import com.example.novemberechonew.Profile.Welcome_AccountActivity;
import com.example.novemberechonew.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

import retrofit2.http.GET;

public class SearchFragment extends Fragment {
    TextView search_from, search_to, extraInfo;
    String origin, destination, adults, children, date_from, date_to;
    Button modify, book;
    DBManager dbManager;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        search_from = view.findViewById(R.id.search_from);
        search_to = view.findViewById(R.id.search_to);
        extraInfo = view.findViewById(R.id.search_extraInfo);
        modify = view.findViewById(R.id.search_modify);

        origin = VariableManager.getDB_cityFrom();
        destination = VariableManager.getDB_cityTo();
        adults = VariableManager.getDB_adults();
        children = VariableManager.getDB_child();
        date_from = VariableManager.getDB_dateFrom();
        date_to = VariableManager.getDB_dateTo();


        search_from.setText(VariableManager.getDB_cityFrom());
        search_to.setText(VariableManager.getDB_cityTo());
        if (VariableManager.getDB_dateTo() == null) {
            extraInfo.setText("on " + VariableManager.getDB_dateFrom() + " for " + VariableManager.getDB_adults() + "A & " + VariableManager.getDB_child() + "C");

        } else {
            extraInfo.setText("from " + VariableManager.getDB_dateFrom() + " to " + VariableManager.getDB_dateTo() + " for " + VariableManager.getDB_adults() + "A & " + VariableManager.getDB_child() + "C");

        }

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookFragment bookFragment = new BookFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.accounts_framelayout, bookFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.trips);
            }
        });

        dbManager = new DBManager(getContext());
        try {
            dbManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = dbManager.DB_fetchFlightByDestinationOrigin(origin, destination);


        if (cursor.moveToFirst()) {
            do {
                String flight_num = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_ID));
                String flight_origin = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_ORG));
                String flight_destination = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_DEST));
                String flight_dept_t = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_DEPT_T));
                String flight_arr_t = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_ARR_T));

                // Inflate the flight card layout
                View flightCardView = getLayoutInflater().inflate(R.layout.flight_card, null);

                // Find views within the flight card layout
                TextView flightNumber = flightCardView.findViewById(R.id.flight_card_flight_num);
                TextView departureTime = flightCardView.findViewById(R.id.flight_card_depart);
                TextView arrivalTime = flightCardView.findViewById(R.id.flight_card_arrive);
                TextView extraInfo = flightCardView.findViewById(R.id.flight_card_price);
                Button bookButton = flightCardView.findViewById(R.id.flight_card_book);

                Random random = new Random();
                int lowerBound = 1000;
                int upperBound = 2000;
                int price = 0;
                for (int i = 0; i < 10; i++) {
                    price = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
                }

                // Set flight information to the views
                flightNumber.setText(flight_num);
                departureTime.setText(flight_dept_t);
                arrivalTime.setText(flight_arr_t);
                extraInfo.setText("$" + price);

                // Add a click listener to the book button if needed
                bookButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
                        if (fire_user == null) {
                            Toast.makeText(getContext(), "You need to be logged in to continue booking!", Toast.LENGTH_SHORT).show();
                        } else {
                            VariableManager.setDB_cityFrom(flight_origin);
                            VariableManager.setDB_cityTo(flight_destination);
                            VariableManager.setDB_flight_arr_t(String.valueOf(arrivalTime.getText()));
                            VariableManager.setDB_flight_dept_t(String.valueOf(departureTime.getText()));
                            VariableManager.setDB_flight_num(flight_num);

                            ReviewBookingFragment reviewBookingFragment = new ReviewBookingFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.accounts_framelayout, reviewBookingFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }
                });

                // Add the flight card to the layout
                LinearLayout flightCardsLayout = view.findViewById(R.id.search_flight_cards_layout);
                flightCardsLayout.addView(flightCardView);

            } while (cursor.moveToNext());

        }
        return view;
    }
}