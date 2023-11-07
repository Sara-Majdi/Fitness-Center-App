package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class NavigationPage extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Account account;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_page);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (Exception e){
            e.printStackTrace();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        account = (Account) getIntent().getSerializableExtra("account");
        account.setProfile(dbManager);

        if (!account.isHavingProfile()){
            Toast.makeText(getApplicationContext(), "Profile is not complete!", Toast.LENGTH_LONG).show();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                } else if (item.getItemId() == R.id.classDetailsSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ClassDetailsFragment()).commit();
                } else if (item.getItemId() == R.id.announcementSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnnoucementFragment()).commit();
                } else if (item.getItemId() == R.id.trainersSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrainersFragment()).commit();
                } else if (item.getItemId() == R.id.historySideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).commit();
                } else if (item.getItemId() == R.id.aboutUsSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
                } else if (item.getItemId() == R.id.myProfileSideNav) {
                    MyProfileFragment myProfileFragment = new MyProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("account", account);
                    myProfileFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myProfileFragment).commit();
                } else if (item.getItemId() == R.id.logoutSideNav) {
                    Intent i = new Intent(NavigationPage.this, Login.class);
                    startActivity(i);
                }
                dbManager.close();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.homeSideNav);
        }
    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}