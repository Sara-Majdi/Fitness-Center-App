package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_Layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home_menu){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.classDetails_menu) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.notifications_menu) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.trainers_menu) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.aboutUs_menu) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.history_menu) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.logout_menu) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });


    }
}