package com.example.quizzical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizzical.util.Constants;
import com.example.quizzical.util.PreferenceHelper;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends BaseActivity {
    Button btnLogout;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        preferences = getSharedPreferences("QUIZ_PREFERENCE", MODE_PRIVATE);
        editor = preferences.edit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerlayout);

        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        toolbar.setTitle("Home");
                        break;

                    case R.id.give_test:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GiveTestFragment()).commit();
                        toolbar.setTitle("Give test");
                        break;

                    case R.id.history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserHistoryFragment()).commit();
                        toolbar.setTitle("User History");
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

    public void logoutUser() {
        editor.clear();
        editor.commit();

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