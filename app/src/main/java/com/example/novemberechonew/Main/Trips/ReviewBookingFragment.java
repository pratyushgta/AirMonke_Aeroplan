package com.example.novemberechonew.Main.Trips;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novemberechonew.Backend.DBManager;
import com.example.novemberechonew.Backend.DataClass;
import com.example.novemberechonew.Backend.VariableManager;
import com.example.novemberechonew.Profile.Welcome_AccountActivity;
import com.example.novemberechonew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReviewBookingFragment extends Fragment {
    DBManager dbManager;
    TableLayout tableLayout;
    Button confirm;
    FirebaseAuth mAuth;
    Boolean isUserLoggedIn = false;
    List<DataClass> dataList;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    AlertDialog alertDialog;
    String name = "";
    String email = "";
    String dob = "";
    String phone = "";
    String miles = "";
    String pnr = "";

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
        View view = inflater.inflate(R.layout.fragment_review_booking, container, false);
        confirm = view.findViewById(R.id.confirm);


        String org = VariableManager.getDB_flight_org();
        String dest = VariableManager.getDB_flight_dest();
        String arrivalTime = VariableManager.getDB_flight_arr_t();
        String departureTime = VariableManager.getDB_flight_dept_t();
        String flight_num = VariableManager.getDB_flight_num();


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

        // Find the TextView elements by their IDs
        TextView fromTextView = view.findViewById(R.id.from);
        TextView toTextView = view.findViewById(R.id.to);
        TextView deptTextView = view.findViewById(R.id.dept);
        TextView arrTextView = view.findViewById(R.id.arr);
        TextView nameTextView = view.findViewById(R.id.name);
        TextView dobTextView = view.findViewById(R.id.dob);
        TextView emailTextView = view.findViewById(R.id.email);
        TextView phoneTextView = view.findViewById(R.id.phone);
        TextView pnrTextView = view.findViewById(R.id.PNR);
        TextView milesTextView = view.findViewById(R.id.miles);

        // Set the values for these TextView elements
        fromTextView.setText(org); // Set 'org' as the value for the 'from' TextView
        toTextView.setText(dest);  // Set 'dest' as the value for the 'to' TextView
        deptTextView.setText(departureTime); // Set 'departureTime' as the value for the 'dept' TextView
        arrTextView.setText(arrivalTime);  // Set 'arrivalTime' as the value for the 'arr' TextView
        nameTextView.setText(name);  // Set 'name' as the value for the 'name' TextView
        dobTextView.setText(dob);  // Set 'dob' as the value for the 'dob' TextView
        emailTextView.setText(email);  // Set 'email' as the value for the 'email' TextView
        phoneTextView.setText(phone);  // Set 'phone' as the value for the 'phone' TextView

        Random random = new Random();
        int lowerBound = 5000;
        int upperBound = 8000;
        int pnr = 0;
        for (int i = 0; i < 10; i++) {
            pnr = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        }
        pnrTextView.setText(String.valueOf(pnr));  // Set 'flight_num' as the value for the 'PNR' TextView
        miles = miles + 100;
        milesTextView.setText(String.valueOf(miles));  // Set a placeholder value for the 'miles' TextView


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


        dbManager = new DBManager(getContext());
        try {
            dbManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int finalPnr = pnr;
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                dbManager.DB_insertUserBooking(currentUser.getUid(), String.valueOf(finalPnr), name, email, dob, phone);
                firebase_updateData();
                alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Flight booked successfully!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        return view;
    }

    public void firebase_updateData() {

        // Assuming you have a valid `uid` to identify the user
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Create a DataClass object with updated data
        DataClass updatedData = new DataClass(uid, name + " " + name, dob, email, phone, String.valueOf(miles));

        // Get a reference to the Firebase database
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("AirMonke_UserData").child(uid);

        // Update the user's data
        userRef.setValue(updatedData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Log.e(TAG, "Data Updated Successfully!");
                } else {
                    //Log.e(TAG, "Data Update Failed: " + task.getException().getMessage());
                }
            }
        });
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
                    name = currentUser.getDisplayName();
                    email = (userData.getDataEmail());
                    phone = (userData.getDataPhone());
                    miles = (userData.getDataMiles());
                    dob = (userData.getDataDOB());
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
