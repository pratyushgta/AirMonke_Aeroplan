package com.example.novemberechonew.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.novemberechonew.R;

import java.util.ArrayList;

public class Home_N_AccountFragment extends Fragment {
    ImageSlider imageSlider;
    Button signup, login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home__n__account, container, false);

        imageSlider = view.findViewById(R.id.welcome_account_slideshow);
        signup = view.findViewById(R.id.home_n_account_signupbtn);
        login = view.findViewById(R.id.home_n_account_loginbtn);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.login_banner1e, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.login_banner2e, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.login_banner3e, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getContext(), Welcome_AccountActivity.class);
                startActivity(login);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getContext(), Welcome_AccountActivity.class);
                startActivity(login);
            }
        });
        return view;
    }
}