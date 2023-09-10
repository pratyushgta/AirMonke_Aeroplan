package com.example.novemberechonew.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.novemberechonew.Main.HomeActivity;
import com.example.novemberechonew.R;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    TextView guest;
    ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageSlider = findViewById(R.id.login_slideshow);
        guest = findViewById(R.id.login_guestbtn);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.miles_generic, ScaleTypes.FIT));
        slideModels.add(new SlideModel(com.denzcoskun.imageslider.R.drawable.placeholder, ScaleTypes.FIT));
        slideModels.add(new SlideModel(com.denzcoskun.imageslider.R.drawable.placeholder, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.miles_generic, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        guest.setText(R.string.guest_continue);
        guest.setMovementMethod(LinkMovementMethod.getInstance());
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(home);
            }
        });


    }

}