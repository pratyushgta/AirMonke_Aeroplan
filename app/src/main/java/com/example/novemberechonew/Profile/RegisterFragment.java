package com.example.novemberechonew.Profile;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novemberechonew.Backend.DataClass;
import com.example.novemberechonew.Main.HomeActivity;
import com.example.novemberechonew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    TextInputEditText editEmail, editPassword, editFirst, editLast, editDOB, editPhone;
    Button registerBtn;
    TextView login;
    String uid;
    FirebaseAuth mAuth;
    AlertDialog alertDialog;
    int miles = 0;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent home = new Intent(getContext(), HomeActivity.class);
            startActivity(home);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        editFirst = view.findViewById(R.id.book_first_name);
        editLast = view.findViewById(R.id.book_last_name);
        editDOB = view.findViewById(R.id.signup_dob);
        editPhone = view.findViewById(R.id.signup_phone);
        editEmail = view.findViewById(R.id.signup_email);
        editPassword = view.findViewById(R.id.signup_password);
        registerBtn = view.findViewById(R.id.signup_button);

        login = view.findViewById(R.id.signup_loginText);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (FragmentActivity) getActivity();
                assert activity != null;
                ViewPager2 viewPager2 = activity.findViewById(R.id.accounts_viewPager);
                if (viewPager2 != null) {
                    viewPager2.setCurrentItem(0);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                String fName, lastName, dob, phone, email, password;
                fName = String.valueOf(editFirst.getText());
                lastName = String.valueOf(editLast.getText());
                dob = String.valueOf(editDOB.getText());
                phone = String.valueOf(editPhone.getText());
                email = String.valueOf(editEmail.getText());
                password = String.valueOf(editPassword.getText());

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(fName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(phone)) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "looks like you're missing something...", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    dialog.dismiss();
                                    FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (fire_user != null) {
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(fName)
                                                .build();
                                        Log.e(TAG, fName);
                                        fire_user.updateProfile(profileUpdates);
                                        uid = fire_user.getUid();
                                        miles = 0;
                                    }
                                    firebase_uploadData();

                                    // Sign in success, update UI with the signed-in user's information
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getContext(), "Account created!",
                                            Toast.LENGTH_SHORT).show();

                                    Activity activity = (FragmentActivity) getActivity();
                                    assert activity != null;
                                    ViewPager2 viewPager2 = activity.findViewById(R.id.accounts_viewPager);
                                    if (viewPager2 != null) {
                                        viewPager2.setCurrentItem(1);
                                    }
                                } else {
                                    dialog.dismiss();
                                    String errorMessage = task.getException().getMessage();
                                    showFirebaseErrorDialog(errorMessage);
                                }
                            }
                        });

            }
        });
        return view;
    }

    private void showFirebaseErrorDialog(String errorMessage) {
        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Authentication Failed")
                .setMessage(errorMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }

    public void firebase_uploadData() {
        String fName, lastName, dob, phone, email, password;
        fName = String.valueOf(editFirst.getText());
        lastName = String.valueOf(editLast.getText());
        dob = String.valueOf(editDOB.getText());
        phone = String.valueOf(editPhone.getText());
        email = String.valueOf(editEmail.getText());
        password = String.valueOf(editPassword.getText());

        DataClass dataClass = new DataClass(uid, fName + " " + lastName, dob, email, phone, String.valueOf(miles));

        FirebaseDatabase.getInstance().getReference("AirMonke_UserData").child(uid)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "SAVED!");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "FAILED!");
                    }
                });
    }
}