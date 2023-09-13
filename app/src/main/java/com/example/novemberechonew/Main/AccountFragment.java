package com.example.novemberechonew.Main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.novemberechonew.R;

import java.util.ArrayList;

public class AccountFragment extends Fragment {
    ImageSlider imageSlider;
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        imageSlider = view.findViewById(R.id.login_slideshow);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.login_banner1e, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.login_banner2e, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.login_banner3e, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
        return view;
    }
}