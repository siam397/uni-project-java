package com.example.artsell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class LandingPageActivity extends AppCompatActivity {

    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        //Appbar Font Color
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));

        bottomNav = findViewById(R.id.bottomNavbar);

        if (savedInstanceState == null) {
            bottomNav.setItemSelected(R.id.chat, true);
            fragmentManager = getSupportFragmentManager();
            ChatFragment chatFragment = new ChatFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, chatFragment)
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;

                switch (id) {
                    case R.id.chat:
                        fragment = new ChatFragment();
                        break;
                    case R.id.friends:
                        fragment = new FriendsFragment();
                        break;
                    case R.id.discover:
                        fragment = new DiscoverFragment();
                        break;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();
                }
            }
        });
    }
}