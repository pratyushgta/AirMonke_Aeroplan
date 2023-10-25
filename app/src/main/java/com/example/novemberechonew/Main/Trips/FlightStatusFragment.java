package com.example.novemberechonew.Main.Trips;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novemberechonew.Backend.DBHelper;
import com.example.novemberechonew.Backend.DBManager;
import com.example.novemberechonew.Backend.VariableManager;
import com.example.novemberechonew.Main.HomeActivity;
import com.example.novemberechonew.MainActivity;
import com.example.novemberechonew.Profile.Home_Y_AccountFragment;
import com.example.novemberechonew.Profile.Welcome_AccountActivity;
import com.example.novemberechonew.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FlightStatusFragment extends Fragment {
    EditText flight_num;
    Button search;
    DBManager dbManager;
    AlertDialog alertDialog;
    private static final int DELAY_MILLIS = 2000; // 5 seconds

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flight_status, container, false);
        flight_num = view.findViewById(R.id.track_num);
        search = view.findViewById(R.id.track_button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                String f_id;
                f_id = String.valueOf(flight_num.getText());

                dbManager = new DBManager(getContext());
                try {
                    dbManager.open();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Cursor cursor = dbManager.DB_fetchFlightByID(f_id);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (cursor != null && cursor.moveToFirst()) {
                            dialog.dismiss();
                            VariableManager.setDB_flight_num(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_ID)));
                            VariableManager.setDB_flight_org(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_ORG)));
                            VariableManager.setDB_flight_dest(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_DEST)));
                            VariableManager.setDB_flight_arr_t(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_ARR_T)));
                            VariableManager.setDB_flight_dept_t(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FLIGHT_DEPT_T)));

                            ShowFlightStatusFragment showFlightStatusFragment = new ShowFlightStatusFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.accounts_framelayout, showFlightStatusFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        } else {
                            dialog.dismiss();
                            //Toast.makeText(getContext(), "Flight not found!", Toast.LENGTH_SHORT).show();
                            alertDialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Unable to find the flight!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    })
                                    .create();
                            alertDialog.show();
                            return;
                        }
                    }
                }, DELAY_MILLIS);
            }
        });

        return view;
    }
}