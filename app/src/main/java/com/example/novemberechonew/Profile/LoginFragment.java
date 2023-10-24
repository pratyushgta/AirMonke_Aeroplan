package com.example.novemberechonew.Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novemberechonew.Main.HomeActivity;
import com.example.novemberechonew.Main.MapsFragment;
import com.example.novemberechonew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginFragment extends Fragment {
    EditText editEmail, editPassword;
    TextView register;
    Button loginBtn;
    //ProgressBar progressBar;
    FirebaseAuth mAuth;
    AlertDialog alertDialog;

    @Override
    public void onStart() {
        super.onStart();
        // Check if the user is signed in (non-null) and update UI accordingly.
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
        mAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        editEmail = view.findViewById(R.id.login_email);
        editPassword = view.findViewById(R.id.login_password);
        loginBtn = view.findViewById(R.id.login_loginButton);
        register = view.findViewById(R.id.login_signupText);
        //progressBar = view.findViewById(R.id.login_progressBar);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (FragmentActivity) getActivity();
                assert activity != null;
                ViewPager2 viewPager2 = activity.findViewById(R.id.accounts_viewPager);
                if (viewPager2 != null) {
                    viewPager2.setCurrentItem(1);
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                String email, password;
                email = String.valueOf(editEmail.getText());
                password = String.valueOf(editPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    //progressBar.setVisibility(View.GONE);
                    dialog.dismiss();
                    Toast.makeText(getContext(), "ig you missed the email...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    //progressBar.setVisibility(View.GONE);
                    dialog.dismiss();
                    Toast.makeText(getContext(), "ig you missed the password...", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    dialog.dismiss();
                                    // Sign-in success, update UI with the signed-in user's information
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getContext(), "Logged in!", Toast.LENGTH_SHORT).show();

                                    Intent home = new Intent(getContext(), HomeActivity.class);
                                    startActivity(home);
                                } else {
                                    dialog.dismiss();
                                    // Authentication failed, display the error message
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
}
