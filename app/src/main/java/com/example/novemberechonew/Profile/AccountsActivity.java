package com.example.novemberechonew.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.novemberechonew.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AccountsActivity extends AppCompatActivity {

    Button back;
    int opened_frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        ViewPager2 viewPager = findViewById(R.id.accounts_viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        back = findViewById(R.id.accounts_closeButton);

        opened_frag = getIntent().getExtras().getInt("frgToLoad");

        Accounts_PagerAdapter pagerAdapter = new Accounts_PagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // Set tab titles here
                    switch (position) {
                        case 0:
                            tab.setText("Login");
                            break;
                        case 1:
                            tab.setText("Register");
                            break;
                    }
                }).attach();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(AccountsActivity.this, Welcome_AccountActivity.class);
                startActivity(home);
            }
        });
    }

    // Create an inner PagerAdapter to manage the fragments for each section
    static class Accounts_PagerAdapter extends FragmentStateAdapter {

        public Accounts_PagerAdapter(AccountsActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new LoginFragment();
                case 1:
                    return new RegisterFragment();
                case 2:
                default:
                    return new LoginFragment(); // Return a default fragment or handle the case appropriately.
            }
        }

        @Override
        public int getItemCount() {
            return 2; // Number of tabs
        }
    }
}
