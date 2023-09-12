package com.example.novemberechonew.Main.Trips;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.novemberechonew.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TripsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        ViewPager2 viewPager = view.findViewById(R.id.trips_viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // Set tab titles here
                    switch (position) {
                        case 0:
                            tab.setText("Book Flight");
                            break;
                        case 1:
                            tab.setText("Manage Booking");
                            break;
                        case 2:
                            tab.setText("Flight Status");
                            break;
                    }
                }).attach();

        return view;
    }

    // Create an inner PagerAdapter to manage the fragments for each section
    static class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new BookFragment();
                case 1:
                    return new MyBookingsFragment();
                case 2:
                    return new FlightStatusFragment();
                default:
                    return new FlightStatusFragment(); // Return a default fragment or handle the case appropriately.
            }
        }

        @Override
        public int getItemCount() {
            return 3; // Number of tabs
        }
    }
}
