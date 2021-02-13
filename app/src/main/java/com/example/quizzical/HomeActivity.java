package com.example.quizzical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quizzical.fragment.AddQuestionFragment;
import com.example.quizzical.fragment.AddSubjectFragment;
import com.example.quizzical.fragment.GiveTestFragment;
import com.example.quizzical.fragment.HomeFragment;
import com.example.quizzical.fragment.UserHistoryFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends BaseActivity {
//    Button btnLogout;


    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerlayout);

        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            addFragment(homeFragment);
            navigationView.setCheckedItem(R.id.home);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        replaceFragment(homeFragment);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        toolbar.setTitle("Home");
                        break;

                    case R.id.give_test:
                        GiveTestFragment giveTestFragment = new GiveTestFragment();
                        replaceFragment(giveTestFragment);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GiveTestFragment()).commit();
                        toolbar.setTitle("Give test");
                        break;

                    case R.id.history:
                        UserHistoryFragment userHistoryFragment = new UserHistoryFragment();
                        replaceFragment(userHistoryFragment);
                        toolbar.setTitle("User History");
                        break;

                    case R.id.add_subject:
                        AddSubjectFragment addSubjectFragment = new AddSubjectFragment();
                        replaceFragment(addSubjectFragment);
                        toolbar.setTitle("Subjects");
                        break;

                    case R.id.add_question:
                        AddQuestionFragment addQuestionFragment = new AddQuestionFragment();
                        replaceFragment(addQuestionFragment);
                        toolbar.setTitle("Question");
                        break;

                    case R.id.logout:
                        logoutUser();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logoutUser();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void init() {
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void logoutUser() {
//        editor.clear();
//        editor.commit();
//


        baseActivityPreferenceHelper.clearPreferences();
        // After logout redirect user to Loing Activity
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        // Starting Login Activity
        startActivity(i);
        finish();
    }
//
//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }


//    public void checkUserLogIn() {
//        if (!isLoggedIn()) {
//            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
//            startActivity(i);
//        }
//    }

//    public boolean isLoggedIn() {
//
//        return baseActivityPreferenceHelper.getBoolean(Constants.PREF_LOGIN, false);
//    }
}