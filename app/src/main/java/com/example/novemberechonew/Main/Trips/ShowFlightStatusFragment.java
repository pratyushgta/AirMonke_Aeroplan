package com.example.novemberechonew.Main.Trips;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.novemberechonew.Backend.VariableManager;
import com.example.novemberechonew.R;

public class ShowFlightStatusFragment extends Fragment {
    TextView flight_number, flight_from, flight_to, flight_dept, flight_arr;
    Button back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_flight_status, container, false);
        flight_number = view.findViewById(R.id.status_flight_num);
        flight_from = view.findViewById(R.id.status_from);
        flight_to = view.findViewById(R.id.status_to);
        flight_dept = view.findViewById(R.id.status_dept_t);
        flight_arr = view.findViewById(R.id.status_arr_t);
        back = view.findViewById(R.id.status_back);

        flight_number.setText("AirMonke "+VariableManager.getDB_flight_num());
        flight_from.setText(VariableManager.getDB_flight_org());
        flight_to.setText(VariableManager.getDB_flight_dest());
        flight_dept.setText(VariableManager.getDB_flight_dept_t());
        flight_arr.setText(VariableManager.getDB_flight_arr_t());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlightStatusFragment flightStatusFragment = new FlightStatusFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.accounts_framelayout, flightStatusFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}