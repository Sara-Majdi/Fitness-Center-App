package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class NavigationPage extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Account account;
    private DatabaseManager dbManager;
    private int page;

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
        page = 0;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Intent intent = getIntent();
        page = intent.getIntExtra("pagekey", 0);
        account = (Account) getIntent().getSerializableExtra("account");
        account.setProfile(dbManager);

        Bundle bundle = new Bundle();
        bundle.putSerializable("account", account);

        if (!account.isHavingProfile()){
            Toast.makeText(getApplicationContext(), "Profile is not complete!", Toast.LENGTH_LONG).show();
        }

        if (page == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.homeTrainerSideNav);
        } else if (page == 1){
            //My Profile
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            myProfileFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myProfileFragment).commit();
            navigationView.setCheckedItem(R.id.myProfileSideNav);
        } else if (page == 2){
            //Book Class
            ClassDetailsFragment classDetailsFragment = new ClassDetailsFragment();
            classDetailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, classDetailsFragment).commit();
            navigationView.setCheckedItem(R.id.classDetailsSideNav);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeTrainerSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }
                else if (item.getItemId() == R.id.classDetailsSideNav){
                    ClassDetailsFragment classDetailsFragment = new ClassDetailsFragment();
                    classDetailsFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, classDetailsFragment).commit();
                }
                else if (item.getItemId() == R.id.announcementSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnnouncementFragment()).commit();
                }
                else if (item.getItemId() == R.id.trainersSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrainersFragment()).commit();
                }
                else if (item.getItemId() == R.id.historySideNav){
                    HistoryFragment historyFragment = new HistoryFragment();
                    historyFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, historyFragment).commit();
                }
                else if (item.getItemId() == R.id.aboutUsSideNav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
                }
                else if (item.getItemId() == R.id.myProfileSideNav) { //No problem at passing account
                    MyProfileFragment myProfileFragment = new MyProfileFragment();
                    myProfileFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myProfileFragment).commit();
                }
                else if (item.getItemId() == R.id.logoutSideNav) {
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