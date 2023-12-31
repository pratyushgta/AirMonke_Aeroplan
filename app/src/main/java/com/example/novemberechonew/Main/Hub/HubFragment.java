package com.example.novemberechonew.Main.Hub;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.novemberechonew.Main.MapsFragment;
import com.example.novemberechonew.Main.Trips.BookFragment;
import com.example.novemberechonew.Main.Trips.MyBookings_N_Fragment;
import com.example.novemberechonew.Main.Trips.MyBookings_Y_Fragment;
import com.example.novemberechonew.Profile.Home_N_AccountFragment;
import com.example.novemberechonew.Profile.Home_Y_AccountFragment;
import com.example.novemberechonew.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class HubFragment extends Fragment {
    FirebaseAuth mAuth;
    Boolean isUserLoggedIn = false;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            isUserLoggedIn = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hub, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
        if (fire_user != null) {
            isUserLoggedIn = true;
        }

        // Randomize greetings
        String[] greetings = {"Buna ziua", "hallo", "Përshëndetje", "ሰላም", "Բարեւ Ձեզ", "Salam", "হ্যালো", "kaixo", "добры дзень", "zdravo", "Здравейте", "မင်္ဂလာပါ", "Hola", "kumusta", "你好", "你好", "Bonghjornu", "zdravo", "Hej", "Hallo", "Hello", "Henlo", "Hello there"};
        TextView hub_greeting = view.findViewById(R.id.hub_greeting);
        Random rand = new Random();
        int random1 = rand.nextInt(greetings.length);
        hub_greeting.setText(greetings[random1]);

        //update user info
        TextView user = view.findViewById(R.id.hub_user);
        if (isUserLoggedIn && fire_user != null) {
            user.setText(fire_user.getDisplayName());
            //Log.e(TAG,fire_user.getDisplayName()+" "+fire_user.getEmail()+" "+isUserLoggedIn);
        } else {
            //Log.e(TAG,fire_user.getDisplayName()+" "+fire_user.getEmail()+" "+isUserLoggedIn);
            user.setText("Login/ Sign Up");
            user.setMovementMethod(LinkMovementMethod.getInstance());
            user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Home_N_AccountFragment accountFragment = new Home_N_AccountFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.accounts_framelayout, accountFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                            bottomNavigationView.setSelectedItemId(R.id.account);
                        }
                    });
                }
            });
        }

        //randomize background destinations
        int[] bgCityID = {R.drawable.vegas_generic, R.drawable.bahrain_generic, R.drawable.sentinelisland_generic, R.drawable.javaisland_generic, R.drawable.paris_generic, R.drawable.athens_generic};
        String[] bgCityName = {"Las Vegas LAS", "Bahrain BAH", "North Sentinel NS", "Java Islands JOG", "Paris CDG", "Athens ATH"};
        ImageView hub_photo = view.findViewById(R.id.hub_photo);
        TextView cityName = view.findViewById(R.id.hub_cityName);
        TextView bookNow = view.findViewById(R.id.hub_BookNow);

        int random2 = rand.nextInt(bgCityID.length);
        hub_photo.setImageResource(bgCityID[random2]);
        cityName.setText(bgCityName[random2]);
        ImageView gradientOverlayImageView = view.findViewById(R.id.gradient_overlay);
        // Set the visibility to VISIBLE to show the gradient overlay
        gradientOverlayImageView.setVisibility(View.VISIBLE);

        bookNow.setMovementMethod(LinkMovementMethod.getInstance());
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookNow.setOnClickListener(new View.OnClickListener() {
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
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.hub_recyclerView);
        // Create a list of CardItems
        List<CardItem> cardItems = new ArrayList<>();
        cardItems.add(new CardItem(R.drawable.hub_searchicon_d, "Book it, don't overlook it!", "Book Now"));
        cardItems.add(new CardItem(R.drawable.hub_planeicon_d, "Flying in the next 24 hours?", "Check in"));
        cardItems.add(new CardItem(R.drawable.hub_exploreicon_d, "Explore the unexplored!", "Explore"));
        cardItems.add(new CardItem(R.drawable.monkemiles_logo_d, "Let us go the extra miles for you", "Sign in"));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(requireContext(), cardItems, getActivity());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.setClipToPadding(false);
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }
}

class CardItem {
    private final int imageResId;
    private final String text;
    private final String buttonText;

    public CardItem(int imageResId, String text, String buttonText) {
        this.imageResId = imageResId;
        this.text = text;
        this.buttonText = buttonText;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }

    public String getButtonText() {
        return buttonText;
    }
}

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyRecyclerHolder> {

    private final LayoutInflater inflater;
    private final List<CardItem> list;
    private final FragmentActivity activity; // Add this field

    RecyclerAdapter(Context context, List<CardItem> list, Activity activity) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.activity = (FragmentActivity) activity;
    }


    @NonNull
    @Override
    public MyRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRecyclerHolder(inflater.inflate(R.layout.fragment_hub_carditem, parent, false));
    }

    @Override
    public void onBindViewHolder(MyRecyclerHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImageResId());
        holder.textView.setText(list.get(position).getText());
        holder.button.setText(list.get(position).getButtonText());

        switch (position) {
            case 0: //book now button
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BookFragment bookFragment = new BookFragment();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.accounts_framelayout, bookFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
                        bottomNavigationView.setSelectedItemId(R.id.trips);
                    }
                });
                break;
            case 1: //check in button
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
                        if (fire_user != null) {
                            MyBookings_Y_Fragment myBookings_y_fragment = new MyBookings_Y_Fragment();
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.accounts_framelayout, myBookings_y_fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
                            bottomNavigationView.setSelectedItemId(R.id.trips);
                        } else {
                            MyBookings_N_Fragment myBookings_y_fragment = new MyBookings_N_Fragment();
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.accounts_framelayout, myBookings_y_fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
                            bottomNavigationView.setSelectedItemId(R.id.trips);
                        }
                    }
                });
                break;
            case 2: //bexplore button
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MapsFragment mapsFragment = new MapsFragment();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.accounts_framelayout, mapsFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
                        bottomNavigationView.setSelectedItemId(R.id.maps);
                    }
                });
                break;
            case 3: //accounts button
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();
                        if (fire_user != null) {
                            Home_Y_AccountFragment accountFragment = new Home_Y_AccountFragment();
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.accounts_framelayout, accountFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
                            bottomNavigationView.setSelectedItemId(R.id.account);
                        } else {
                            Home_N_AccountFragment accountFragment = new Home_N_AccountFragment();
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.accounts_framelayout, accountFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
                            bottomNavigationView.setSelectedItemId(R.id.account);
                        }

                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyRecyclerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        MaterialButton button;

        public MyRecyclerHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hub_card_image);
            textView = itemView.findViewById(R.id.hub_card_text);
            button = itemView.findViewById(R.id.hub_button);
        }
    }
}


